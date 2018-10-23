package petfinder.site.common.user;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
				.collect(Collectors.toList());
		users.setUsers(filtered);
		return users;
	}
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
	public UserTimesDTO getTimes(){
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAuthenticationDto> a = userDao.findUserByPrincipal(principal);
		if(a.isPresent()){
			return new UserTimesDTO(a.get().getUser().getDays());
		}
		return null;
	}
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
		return true;
	}
	//For testing
    public Optional<UserAuthenticationDto> findUsersTest(String principle){
        return userDao.findUserByPrincipal(principle);
    }

}