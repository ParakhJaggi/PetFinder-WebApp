package petfinder.site.common.user;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alloy.util.AlloyAuthentication;
import alloy.util.Wait;
import alloy.util._Lists;
import alloy.util._Maps;
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
		private String city, state, address;

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
		userAuthentication = new UserAuthenticationDto(
				new UserDto(request.getPrincipal(),
						_Lists.list("ROLE_USER"),
						UserType.OWNER, request.getAttributes(),
						request.getAddress(), request.getCity(),
						request.getState()),
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
	//For testing
    public Optional<UserAuthenticationDto> findUsersTest(String principle){
        return userDao.findUserByPrincipal(principle);
    }
}