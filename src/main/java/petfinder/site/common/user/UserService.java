package petfinder.site.common.user;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import com.mailjet.client.resource.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static java.net.URLEncoder.encode;

import alloy.util.AlloyAuthentication;
import alloy.util.Wait;
import alloy.util._Lists;
import alloy.util._Maps;
import petfinder.site.common.CustomGeoPoint;
import petfinder.site.common.Exceptions.UserException;
import petfinder.site.common.RestRequests.AnimalTypeRequestBuilder;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDao;
import petfinder.site.common.user.UserDto.UserType;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;


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
	private PetDao petDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<UserDto> findUser(String id) {
		return userDao.findUser(id).map(UserAuthenticationDto::getUser);
	}

	public Optional<UserDto> findUserByPrincipal(String principal) {
		Optional<UserDto> u = userDao.findUserByPrincipal(principal).map(UserAuthenticationDto::getUser);
		return u;
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
		RestTemplate rs = new RestTemplate();
		StringBuilder strB = new StringBuilder();
		Double latitude = 0.0, longitude = 0.0;
		strB.append(request.getAddress()).append(',').append(request.getCity()).append(',').append(request.getState());
		UserAuthenticationDto userAuthentication = null;
		//see if the user already exists
		if(findUserByPrincipal(request.getPrincipal()).isPresent()){
			return null;
		}
		Map<?, ?> o = null;
		try {
			o = rs.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address={address}&key=AIzaSyCXPmp-yjzKl9jIN9fwXbRLgCUOfwaYZfQ", Map.class, encode(strB.toString(), "UTF-8"));
		}
		catch(UnsupportedEncodingException e){
			return null;
		}
		if(((String)(((Map<?,?>)o).get("status"))).equals("OK") ){
			Map<?, ?> location = ((Map<?, ?>) ((Map<?, ?>) ((Map<?, ?>) ((List<?>) o.get("results")).get(0)).get("geometry")).get("location"));
			latitude = (Double)location.get("lat");
			longitude = (Double)location.get("lng");
		}
		UserDto toSet = new UserDto(request.getPrincipal(),
				_Lists.list("ROLE_USER"),
				UserType.OWNER, request.getAttributes(),
				request.getAddress(), request.getCity().toLowerCase(),
				request.getState().toLowerCase(), request.getZip());
		if(request.getType().equalsIgnoreCase("SITTER")){
			toSet.setType(UserType.SITTER);
		}
		else{
			toSet.setType(UserType.OWNER);
		}
		//toSet.setGeographicPoint(new CustomGeoPoint(latitude, longitude));
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
		if(user.getZip() == null){
			throw new UserException("current user has no zip");
		}
		/*
		List<Object> zipMatch = new ArrayList<>(), cityMatch = new ArrayList<>();
		zipMatch.add(user.getZip());
		cityMatch.add(user.getCity());
		//first get all users within the same zip
		UserCollectionDTO users = userDao.findByFieldMatch("user.zip", zipMatch );
		UserCollectionDTO userCity = userDao.findByFieldMatch("user.city", cityMatch );

		Set<UserDto> allObjects = new HashSet<>(users.getUsers());
		allObjects.addAll(userCity.getUsers());
		*/
		//UserCollectionDTO users = userDao.findSitters(user.getGeographicPoint());
		UserCollectionDTO users = userDao.findSitters(user.getCity(),user.getState(),user.getZip());

		//now remove the current user
		List<UserDto> filtered = users.getUsers().stream()
				.distinct()
				.filter(x-> /*!x.getPrincipal().equals(user.getPrincipal())
						&& */ x.getType() == UserType.SITTER)
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
	public boolean requestBooking(String s, UserTimesDTO utd) throws MailjetSocketTimeoutException, MailjetException {
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
		List<String> test = sitter.get().getUser().getNotification();
		List<String> test2 = owner.get().getUser().getNotification();
		test.add("Booking requested " + owner.get().getUser().getPrincipal());
		test2.add("Booking requested " + sitter.get().getUser().getPrincipal());
		List<Object> toMatch = new LinkedList<>();
		toMatch.add(owner.get().getMomento());
		sitter.get().getUser().getSitPets().put(owner.get().getMomento(), petDao.findByOwner(toMatch));
		sitter.get().getUser().setNotification(test);
		owner.get().getUser().setNotification(test2);
		userDao.save(sitter.get());
		userDao.save(owner.get());

        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("141f6e47ca4cc452b41aaa540312bc7a", "d8acde824e69d34ac0c55def4a1fbf12");
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "parakh_jaggi@baylor.edu")
                .property(Email.FROMNAME, "Group 4 admin")
                .property(Email.SUBJECT, "Booking requested!")
                .property(Email.TEXTPART, "Dear User, You have a booking! Please check our site to accept/decline.")
                .property(Email.RECIPIENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("Email",sitter.get().getUser().getPrincipal())
                                .put("Email",owner.get().getUser().getPrincipal())));


        response = client.post(request);
        System.out.println(response.getData());

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
	public boolean confirmBooking (BookingDTO bd) throws MailjetSocketTimeoutException, MailjetException {
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
		List<String> test = sitter.get().getUser().getNotification();
		test.add("Booking confirmed");
		sitter.get().getUser().setNotification(test);
		//add this owner to the people allowed to review the sitter
		sitter.get().getUser().getUsedSitters().add(bd.getPrincipal());
		userDao.save(sitter.get());

		//also add this to the owner that requested the booking
		Optional<UserAuthenticationDto> owner = userDao.findUserByPrincipal(bd.getPrincipal());
		List<String> test2 = owner.get().getUser().getNotification();
		test2.add("Booking confirmed with " + principal);
		owner.get().getUser().setNotification(test2);
		owner.get().getUser().getBookings().add(new BookingDTO(principal, bd.getDays()));
		userDao.save(owner.get());


        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("141f6e47ca4cc452b41aaa540312bc7a", "d8acde824e69d34ac0c55def4a1fbf12");
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "parakh_jaggi@baylor.edu")
                .property(Email.FROMNAME, "Group 4 admin")
                .property(Email.SUBJECT, "Booking confirmed!")
                .property(Email.TEXTPART, "Dear User, Your booking has been confirmed!")
                .property(Email.RECIPIENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("Email",sitter.get().getUser().getPrincipal())
                                .put("Email",owner.get().getUser().getPrincipal())));


        response = client.post(request);
        System.out.println(response.getData());
		return true;
	}
	public void ClearNotifications(String principle)  {
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> sitter = userDao.findUserByPrincipal(principal);

		sitter.get().getUser().setNotification(new ArrayList<>());

		userDao.save(sitter.get());

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
		sitter.getUser().getSitPets().remove(owner.getMomento());
		if(sitter.getUser().getRequestedBookings().contains(bd))
			sitter.getUser().getRequestedBookings().remove(bd);
		else {
			//see if it is the owner cancelling a confirmed booking
			if (principal.equals(owner.getUser().getPrincipal())) {
				owner.getUser().getBookings().remove(bd);
				sitter.getUser().getBookings().remove(new BookingDTO(owner.getUser().getPrincipal(), bd.getDays()));

			}
			else {
				sitter.getUser().getBookings().remove(bd);
				owner.getUser().getBookings().remove(new BookingDTO(owner.getUser().getPrincipal(), bd.getDays()));
			}
		}
		sitter.getUser().setReviewSum(sitter.getUser().getReviewSum()-1);
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
	public void addReview (ReviewDTO rd) throws MailjetSocketTimeoutException, MailjetException {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
	    UserDto owner = userDao.findUserByPrincipal(principal).get().getUser();

        if(owner.getType() != UserType.OWNER){
            return;
        }
	    Optional<UserAuthenticationDto> op = userDao.findUserByPrincipal(rd.getUser());
	    if(op.isPresent()){
	        UserDto sitter = op.get().getUser();
	        //make sure that the owner is reviewing a sitter and that the owner is
			// allowed to review the sitter
	        if(sitter.getType()!= UserType.SITTER || !sitter.getUsedSitters().contains(principal)){
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
            sitter.setReviewCount(sitter.getReviewCount() + 1);
            sitter.setReviewSum(sitter.getReviewSum() + rd.getAssignedScore());
            List<String> l = sitter.getNotification();
            l.add("Review added! Current Score = " + sitter.getReviewSum());
            op.get().getUser().setNotification(l);
            userDao.save(op.get());


            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient("141f6e47ca4cc452b41aaa540312bc7a", "d8acde824e69d34ac0c55def4a1fbf12");
            request = new MailjetRequest(Email.resource)
                    .property(Email.FROMEMAIL, "parakh_jaggi@baylor.edu")
                    .property(Email.FROMNAME, "Group 4 admin")
                    .property(Email.SUBJECT, "Rating Added!")
                    .property(Email.TEXTPART, "Dear User, A user gave you a review! Your new score is " + sitter.getReviewSum() +".")
                    .property(Email.RECIPIENTS, new JSONArray()
                            .put(new JSONObject()
                                    .put("Email",sitter.getPrincipal())));


            response = client.post(request);
            System.out.println(response.getData());
	    }
    }

	//For testing
    public Optional<UserAuthenticationDto> findUsersTest(String principle){
        return userDao.findUserByPrincipal(principle);
    }

}