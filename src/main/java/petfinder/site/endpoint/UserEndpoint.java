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
		Optional<UserDto> debug = userService.findUserByPrincipal(principal);
		return debug;
	}

	@PostMapping(value = "/register")
	public UserDto register(@RequestBody RegistrationRequest request) {
		return userService.register(request);
	}

	/**
	 * @author Laird
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/updatePassword")
	public UserDto updatePassword(@RequestBody UserService.PasswordChangeRequest req){
		return userService.changePassword(req);
	}

	/**
	 * @author Laird
	 * @return
	 */
	@DeleteMapping(value = "/deleteThisUser")
	public boolean deleteCurrentUser(){
		return userService.deleteUser(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/getSits")
	public List<BookingDTO> getMySits(){
		//this is for sitters
		return userService.getMySits(true);
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/getRequestedSits")
	public List<BookingDTO> getRequestedSits(){
		//this is for sitters
		return userService.getMySits(false);
	}

	/**
	 * @author Laird
	 * @return
	 * @throws UserException
	 */
	@GetMapping(value = "/getavailablesitters")
	public UserCollectionDTO getSitters() throws UserException {
		//return userService.getAvailableSitters("sitter1@sitter.com");
		return userService.getAvailableSitters(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@GetMapping(value = "/clearnotifications")
	public void clearNotifications() throws UserException {
		userService.ClearNotifications(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	/**
	 * @author Laird
	 * @param utd
	 */
	@PostMapping(value = "/setdays")
	public void setTimes(@RequestBody UserTimesDTO utd){
		//this is for all users
		userService.setUserTimes(utd);
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/getDays")
	public UserTimesDTO getDays(){
		//this is for all users
		return userService.getTimes();
	}

	/**
	 * @author Laird
	 * @param utd
	 * @return
	 */
	@PostMapping(value = "/requestBooking")
	public boolean requestBooking(@RequestBody makebookingDTO utd){
		return userService.requestBooking(utd.getPrincipal(), new UserTimesDTO(utd.getDays()));
	}

	/**
	 * @author Laird
	 * @param bd
	 * @return
	 */
	@PostMapping(value = "/confirmBooking")
	public boolean confirmBooking(@RequestBody BookingDTO bd){
		return userService.confirmBooking(bd);
	}

	/**
	 * @author Laird
	 * @param bd
	 * @return
	 */
	@PostMapping(value = "/deleteBooking")
	public boolean deleteBooking(@RequestBody BookingDTO bd){
		return userService.cancelBooking(bd);
	}

	/**
	 * @author Laird
	 * @param rd
	 */
	@PostMapping(value = "/addReviewScore")
	public void addReview(@RequestBody ReviewDTO rd){
		userService.addReview(rd);

	}

}