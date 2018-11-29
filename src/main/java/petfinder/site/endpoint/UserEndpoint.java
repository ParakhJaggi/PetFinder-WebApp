package petfinder.site.endpoint;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
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
	 * @return true indicates that the user has been successfully deleted.
	 *         false indicates that the current user has not been deleted.
	 * This is is the endpoint for deleting a logged in user.
	 */
	@DeleteMapping(value = "/deleteThisUser")
	public boolean deleteCurrentUser(){
		return userService.deleteUser(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	/**
	 * @author Laird
	 * @return all bookings of the current sitter
	 * Note that this endpoint is for getting all sits of a currently logged in sitter.
	 */
	@GetMapping(value = "/getSits")
	public List<BookingDTO> getMySits(){
		//this is for sitters
		return userService.getMySits(true);
	}

	/**
	 * Way for sitters to get all requested bookings from various owners.
	 * @author Laird
	 * @return all of the sits that the current sitter has from owners.
	 * Note: this method is designed for sitters.
	 */
	@GetMapping(value = "/getRequestedSits")
	public List<BookingDTO> getRequestedSits(){
		//this is for sitters
		return userService.getMySits(false);
	}

	/**
	 * Is the sitter suggestion method that uses city and zip to give priority to sitters.
	 * @author Laird
	 * @return all sitters currently available for the current owner
	 * @throws UserException
	 */
	@GetMapping(value = "/getavailablesitters")
	public UserCollectionDTO getSitters() throws UserException {
		//return userService.getAvailableSitters("sitter1@sitter.com");
		return userService.getAvailableSitters(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	/**
	 * clears all of the notifications of the current user
	 * @author Laird
	 */
	@GetMapping(value = "/clearnotifications")
	public void clearNotifications(){
		userService.ClearNotifications(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	/**
	 * This is a way for a user to set or reset their available times
	 * @author Laird
	 * @param utd the times of the user
	 */
	@PostMapping(value = "/setdays")
	public void setTimes(@RequestBody UserTimesDTO utd){
		//this is for all users
		userService.setUserTimes(utd);
	}

	/**
	 * Way for an authenticated user to get their saved availability
	 * @author Laird
	 * @return the times saved of the current user
	 */
	@GetMapping(value = "/getDays")
	public UserTimesDTO getDays(){
		//this is for all users
		return userService.getTimes();
	}

	/**
	 * @author Laird
	 * @param utd the booking that the current owner is requesting
	 * @return true indicates that the booking has been successfully requested.
	 *         false indicates that the booking ahs not been successfully requested.
	 * @see makebookingDTO
	 */
	@PostMapping(value = "/requestBooking")
	public boolean requestBooking(@RequestBody makebookingDTO utd) throws MailjetSocketTimeoutException, MailjetException {
		return userService.requestBooking(utd.getPrincipal(), new UserTimesDTO(utd.getDays()));
	}

	/**
	 * Way for a sitter to confirm a booking.
	 * @author Laird
	 * @param bd the booking that the sitter would like to confirm
	 * @return true indicates that the booking has been successfully confirmed.
	 *         false indicates that the booking has not been successfully confirmed.
	 * Note: the booking indicates must already exist as a requested booking for the currently
	 * logged in sitter or else false will be returned.
	 */
	@PostMapping(value = "/confirmBooking")
	public boolean confirmBooking(@RequestBody BookingDTO bd) throws MailjetSocketTimeoutException, MailjetException {
		return userService.confirmBooking(bd);
	}

	/**
	 * Way for a user to cancel a booking.
	 * @author Laird
	 * @param bd the booking to be cancelled.
	 * @return true indicates success
	 */
	@PostMapping(value = "/deleteBooking")
	public boolean deleteBooking(@RequestBody BookingDTO bd){
		return userService.cancelBooking(bd);
	}

	/**
	 * Way for an owner to review a sitter
	 * @author Laird
	 * @param rd the review that is to associated with the sitter
	 * @see ReviewDTO
	 */
	@PostMapping(value = "/addReviewScore")
	public void addReview(@RequestBody ReviewDTO rd) throws MailjetSocketTimeoutException, MailjetException {
		userService.addReview(rd);
	}

}