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
    private String notification = "";
    private int reviewCount, reviewSum;
    private List<ReviewDTO> reviews;

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getReviewSum() {
		return reviewSum;
	}

	public void setReviewSum(int reviewSum) {
		this.reviewSum = reviewSum;
	}

	public List<ReviewDTO> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDTO> reviews) {
		this.reviews = reviews;
	}

	public String getNotification() {
        return this.notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }


	public UserDto() {
		this.reviewCount = 1;
		this.reviewSum = 10;
		this.reviews = new LinkedList<>();
	}

	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes, String notification) {
		this();
		this.principal = principal;
		this.roles = roles;
		this.attributes = attributes;
		this.type = type;
		this.bookings = new ArrayList<>();
		this.requestedBookings = new ArrayList<>();
		this.days=new boolean[7];
		this.notification = notification;
	}
	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes, String address, String city, String state, String zip, boolean [] b) {
		this();
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
		this.days=b;
	}
	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes, String address, String city, String state, String zip) {
		this();
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

    public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes) {
        this();
		this.principal = principal;
        this.roles = roles;
        this.attributes = attributes;
        this.type = type;
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

	public void setType(UserType type) {
		this.type = type;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}