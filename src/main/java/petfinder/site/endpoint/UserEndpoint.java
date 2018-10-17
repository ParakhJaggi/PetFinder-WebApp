package petfinder.site.endpoint;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import petfinder.site.common.Exceptions.UserException;
import petfinder.site.common.user.*;
import petfinder.site.common.user.UserService.RegistrationRequest;

/**
 * Created by jlutteringer on 8/23/17.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserEndpoint {
	@Autowired
	private UserService userService;

	@GetMapping(value = "", produces = "application/json")
	public Optional<UserDto> getUserDetails() {
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.findUserByPrincipal(principal);
	}

	@PostMapping(value = "/register")
	public UserDto register(@RequestBody RegistrationRequest request) {
		return userService.register(request);
	}

	@PostMapping(value = "/updatePassword")
	public UserDto updatePassword(@RequestBody UserService.PasswordChangeRequest req){
		return userService.changePassword(req);
	}

	@DeleteMapping(value = "/deleteThisUser")
	public boolean deleteCurrentUser(){
		return userService.deleteUser(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@GetMapping(value = "/getSits")
	public List<BookingDTO> getMySits(){
		//this is for sitters
		return userService.getMySits(true);
	}

	@GetMapping(value = "/getRequestedSits")
	public List<BookingDTO> getRequestedSits(){
		//this is for sitters
		return userService.getMySits(false);
	}

	@GetMapping(value = "/getavailablesitters")
	public UserCollectionDTO getSitters() throws UserException {
		return userService.getAvailableSitters(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@PostMapping(value = "/setdays")
	public void setTimes(@RequestBody UserTimesDTO utd){
		//this is for all users
		userService.setUserTimes(utd);
	}

	@GetMapping(value = "/getDays")
	public UserTimesDTO getDays(){
		//this is for all users
		return userService.getTimes();
	}

	@PostMapping(value = "/requestBooking/{userName}")
	public boolean requestBooking(@PathVariable("userName") String principal, @RequestBody UserTimesDTO utd){
		return userService.requestBooking(principal, utd);
	}

	@PostMapping(value = "/confirmBooking")
	public boolean confirmBooking(@RequestBody BookingDTO bd){
		return userService.confirmBooking(bd);
	}

}