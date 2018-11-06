package petfinder.site.common.user;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alloy.util.AlloyAuthentication;
import alloy.util.Wait;
import alloy.util._Lists;
import alloy.util._Maps;
import petfinder.site.common.Exceptions.UserException;
import petfinder.site.common.RestRequests.AnimalTypeRequestBuilder;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDao;
import petfinder.site.common.user.UserDto.UserType;

/**
 * Created by jlutteringer on 8/23/17.
 *
 * laird added many features
 */
@Service
public class UserService {
    //ONLY FOR UNIT TESTING
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<UserDto> findUser(String id) {
		return userDao.findUser(id).map(UserAuthenticationDto::getUser);
	}

	public Optional<UserDto> findUserByPrincipal(String principal) {
		return userDao.findUserByPrincipal(principal).map(UserAuthenticationDto::getUser);
	}

	public Optional<UserAuthenticationDto> findUserAuthenticationByPrincipal(String principal) {
		return userDao.findUserByPrincipal(principal);
	}

	public static class RegistrationRequest {
		private String principal;
		private String password;
		private Map<String, Object> attributes;
		private String city, state, address, zip, type;

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPrincipal() {
			return principal;
		}

		public void setPrincipal(String principal) {
			this.principal = principal;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	public static class PasswordChangeRequest {
		private String password;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public UserDto register(RegistrationRequest request) {
		UserAuthenticationDto userAuthentication = null;
		//see if the user already exists
		if(findUserByPrincipal(request.getPrincipal()).isPresent()){
			return null;
		}
		UserDto toSet = new UserDto(request.getPrincipal(),
				_Lists.list("ROLE_USER"),
				UserType.OWNER, request.getAttributes(),
				request.getAddress(), request.getCity(),
				request.getState(), request.getZip());
		if(request.getType().equals("SITTER")){
			toSet.setType(UserType.SITTER);
		}
		else{
			toSet.setType(UserType.OWNER);
		}
		userAuthentication = new UserAuthenticationDto(toSet,
				passwordEncoder.encode(request.getPassword()));
		userDao.save(userAuthentication);
		return userAuthentication.getUser();
	}
	public UserDto changePassword(PasswordChangeRequest req){
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserDto> user = findUserByPrincipal(principal);
		if(user.isPresent()){
			UserDto current = user.get();
			UserAuthenticationDto userAuthentication = new UserAuthenticationDto(
					current, passwordEncoder.encode(req.getPassword()));
			userDao.save(userAuthentication);
			return userAuthentication.getUser();
		}
		return null;
	}
	public boolean deleteUser(String principal){
		return userDao.delete(principal);
	}

    /**
     * Returns all possible sitters for the given user. Gives 20 users within the same zip code.
     * Users within the same city are given higher priority than those who are not.
     * @author Laird
     * @param principal the owner
     * @return all possible sitters for the owner
     * @throws UserException if the user does not have an associated zip code
     */
	public UserCollectionDTO getAvailableSitters(String principal) throws UserException{
		Optional<UserDto> userp = findUserByPrincipal(principal);
		if(!userp.isPresent()){
			return null;
		}
		UserDto user = userp.get();
		//make sure that the user is a owner
		if(!(user.getType() == UserType.OWNER)){
			return null;
		}
		List<Object> zipMatch = new ArrayList<>();
		if(user.getZip() == null){
			throw new UserException("current user has no zip");
		}
		zipMatch.add(user.getZip());
		//first get all users within the same zip
		UserCollectionDTO users = userDao.findByFieldMatch("user.zip", zipMatch );

		//now remove the current user
		List<UserDto> filtered = users.getUsers().stream()
				.distinct()
				.filter(x-> !x.getPrincipal().equals(user.getPrincipal())
						&& x.getType() == UserType.SITTER)
				//see if the owner and sitter have at least one day in common
				.filter(x->{
					boolean flag = false;
					for(int i = 0; i < x.getDays().length; i++){
						if(x.getDays()[i] && userp.get().getDays()[i]){
							flag = true;
						}
					}
					return flag;
				})
				.sorted((x,y) -> x.getCity().compareTo(y.getCity()))
                .limit((long)20)
				.collect(Collectors.toList());
		users.setUsers(filtered);
		return users;
	}

    /**
     * @author Laird
     * @param utd day availability
     * @return the current user
     * @see UserTimesDTO
     * @see UserDto
     */
	public UserDto setUserTimes(UserTimesDTO utd){
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> a = userDao.findUserByPrincipal(principal);
		if(a.isPresent()){
			UserDto current = a.get().user;
			current.setDays(utd.getBools());
			userDao.save(a.get());
			return a.get().user;
		}
		return null;
	}

    /**
     * Returns the days that the current user is available.
     * @author Laird
     */
	public UserTimesDTO getTimes(){
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> a = userDao.findUserByPrincipal(principal);
		if(a.isPresent()){
			return new UserTimesDTO(a.get().getUser().getDays());
		}
		return null;
	}

    /**
     * @author laird
     * @param s the principal of the sitter that the owner is requesting a booking of
     * @param utd boolean array representing each day of the week
     * @return true indicates that the booking has been successfully requested
     */
	public boolean requestBooking(String s, UserTimesDTO utd){
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> owner = userDao.findUserByPrincipal(principal);
		//see if the current user is an owner
		if(!owner.isPresent() || owner.get().user.getType() == UserType.SITTER){
			return false;
		}
		Optional<UserAuthenticationDto> sitter = userDao.findUserByPrincipal(s);
		//See if the desired user exists and is a sitter
		if(!sitter.isPresent() || sitter.get().user.getType() != UserType.SITTER){
			return false;
		}
		//see if the sitter is available for those days
		for(int i = 0; i < utd.getBools().length; i++){
			if(utd.getBools()[i] && !sitter.get().getUser().getDays()[i]){
				return false;
			}
		}
		//now add the booking request to the sitter
		sitter.get().user.getRequestedBookings().add(new BookingDTO(principal, utd.getBools()));
		sitter.get().getUser().setNotification("You have a requested booking");
		userDao.save(sitter.get());
		return true;
	}

    /**
     * @author Laird
     * @param isConfirmed true means that confimed bookings should be returned. False means that requested bookings should be returned.
     * @return list of bookings
     * @see BookingDTO
     */
	public List<BookingDTO> getMySits(boolean isConfirmed){
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> sitter = userDao.findUserByPrincipal(principal);
		if(!sitter.isPresent() || sitter.get().user.getType() == UserType.OWNER){
			return null;
		}
		if(isConfirmed) {
			return sitter.get().user.getBookings();
		}
		return sitter.get().user.getRequestedBookings();
	}

    /**
     * This method is used for a sitter to confirm a requestedbooking from an owner.
     * @author Laird
     * @param bd the bookig that the user will request
     * @return true indicates that the booking was successfully confimed. False means the confirmation failed.
     * @see BookingDTO
     */
	public boolean confirmBooking (BookingDTO bd){
		//first see if the current user is a sitter and exists
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> sitter = userDao.findUserByPrincipal(principal);

		if(!sitter.isPresent() || sitter.get().user.getType() == UserType.OWNER){
			return false;
		}
		//now see if that was an actually existant request
		if(!sitter.get().getUser().getRequestedBookings().contains(bd))
			return false;
		sitter.get().getUser().getRequestedBookings().remove(bd);
		sitter.get().getUser().getBookings().add(bd);
		sitter.get().getUser().setNotification("booking confirmed");
		userDao.save(sitter.get());

		//also add this to the owner that requested the booking
		Optional<UserAuthenticationDto> owner = userDao.findUserByPrincipal(bd.getPrincipal());
		owner.get().getUser().getBookings().add(new BookingDTO(principal, bd.getDays()));
		userDao.save(owner.get());
		return true;
	}

    /**
     * Method to cancel a booking. can be called by an owner or a sitter to cancel a requested or confirmed booking.
     * @author Laird
     * @param bd booking to cancel
     * @return true indicates success in cancelling the @Link{bd}
     * @see BookingDTO
     */
	public boolean cancelBooking (BookingDTO bd){
		//first get the current user
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> user = userDao.findUserByPrincipal(principal);
		Optional<UserAuthenticationDto> other = userDao.findUserByPrincipal(bd.getPrincipal());

		if(!user.isPresent() ){
			return false;
		}

		//figure out which one is the sitter
		UserAuthenticationDto sitter = (user.get().user.getType() == UserType.SITTER ? user.get() : other.get());
		UserAuthenticationDto owner =  (user.get().user.getType() == UserType.OWNER ? user.get() : other.get());

		if(sitter.getUser().getRequestedBookings().contains(bd)) {
			sitter.getUser().getRequestedBookings().remove(bd);
		}
		else {
			sitter.getUser().getBookings().remove(bd);
			owner.getUser().getBookings().remove(new BookingDTO(owner.getUser().getPrincipal(), bd.getDays()));
		}

		userDao.save(sitter);
		userDao.save(owner);
		return true;
	}

    /**
     * Method for an owner to add a review to a sitter.
     * @author Laird
     * @param rd review that the current owner is adding.
     * @see ReviewDTO
     */
	public void addReview (ReviewDTO rd){
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
	    UserDto owner = userDao.findUserByPrincipal(principal).get().getUser();
        if(owner.getType() != UserType.OWNER){
            return;
        }
	    Optional<UserAuthenticationDto> op = userDao.findUserByPrincipal(rd.getUser());
	    if(op.isPresent()){
	        UserDto sitter = op.get().getUser();
	        if(sitter.getType()!= UserType.SITTER){
	            return;
            }
            //see if the owner has already left a review
            ReviewDTO review = sitter.getReviews().stream().filter(x -> x.getUser().equals(principal)).findFirst().orElse(null);
            if(review != null) {
                //already exists
                sitter.getReviews().remove(review);
                sitter.setReviewSum(sitter.getReviewSum() - review.getAssignedScore());
                sitter.setReviewCount(sitter.getReviewCount() - 1);
            }
            //now add the review
            rd.setUser(principal);
            sitter.getReviews().add(rd);
            userDao.save(op.get());
	    }
    }

	//For testing
    public Optional<UserAuthenticationDto> findUsersTest(String principle){
        return userDao.findUserByPrincipal(principle);
    }

}