package petfinder.site.common.user;

import java.util.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import alloy.util.Identifiable;
import alloy.util.Momento;

/**
 * Created by jlutteringer on 8/23/17.
 */
public class UserDto implements Momento<String> {
	private String principal;
	private List<String> roles;
	private UserType type;
	private Map<String, Object> attributes;
	private String address, city, state, zip;
	private boolean [] days;
	private List<BookingDTO> bookings;
	private List<BookingDTO> requestedBookings;

	public UserDto() {

	}

	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes) {
		this.principal = principal;
		this.roles = roles;
		this.attributes = attributes;
		this.type = type;
		this.bookings = new ArrayList<>();
		this.requestedBookings = new ArrayList<>();
		this.days=new boolean[7];
	}
	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes, String address, String city, String state, String zip) {
		this.principal = principal;
		this.roles = roles;
		this.attributes = attributes;
		this.type = type;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.bookings = new ArrayList<>();
		this.requestedBookings = new ArrayList<>();
		this.days=new boolean[7];
	}

	public String getPrincipal() {
		return principal;
	}

	public List<String> getRoles() {
		return roles;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public UserType getType() {
		return type;
	}

	@JsonIgnore
	@Override
	public String getMomento() {
		return principal;
	}

	public enum UserType {
		OWNER, SITTER
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public boolean[] getDays() {
		return days;
	}

	public void setDays(boolean[] days) {
		this.days = days;
	}

	public List<BookingDTO> getBookings() {
		return bookings;
	}

	public void setBookings(List<BookingDTO> bookings) {
		this.bookings = bookings;
	}

	public List<BookingDTO> getRequestedBookings() {
		return requestedBookings;
	}

	public void setRequestedBookings(List<BookingDTO> requestedBookings) {
		this.requestedBookings = requestedBookings;
	}

}