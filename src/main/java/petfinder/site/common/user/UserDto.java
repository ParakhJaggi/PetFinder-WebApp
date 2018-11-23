package petfinder.site.common.user;

import java.util.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import alloy.util.Identifiable;
import alloy.util.Momento;
import petfinder.site.common.CustomGeoPoint;
import petfinder.site.common.pet.PetCollectionDTO;

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
    private List<String> notification;
    private int reviewCount, reviewSum;
    private List<ReviewDTO> reviews;
    private Set<String> usedSitters;
    //private CustomGeoPoint geographicPoint;
    Map<String, PetCollectionDTO> sitPets;

	public Map<String, PetCollectionDTO> getSitPets() {
		return sitPets;
	}

	public void setSitPets(Map<String, PetCollectionDTO> sitPets) {
		this.sitPets = sitPets;
	}

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

	public List<String> getNotification() {
        return this.notification;
    }

    public void setNotification(List<String> notification) {
        this.notification = notification;
    }

//	public CustomGeoPoint getGeographicPoint() {
//		return geographicPoint;
//	}
//
//	public void setGeographicPoint(CustomGeoPoint geographicPoint) {
//		this.geographicPoint = geographicPoint;
//	}

	public UserDto() {
		this.reviewCount = 1;
		this.reviewSum = 100;
		this.reviews = new LinkedList<>();
		this.notification = new ArrayList<>();
		this.usedSitters = new HashSet<>();
		//this.geographicPoint = new CustomGeoPoint(0.0,0.0);
		this.sitPets = new HashMap<>();
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

	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes, String address, String city, String state, String zip, boolean[] days, List<BookingDTO> bookings, List<BookingDTO> requestedBookings, List<String> notification, int reviewCount, int reviewSum, List<ReviewDTO> reviews, Set<String> usedSitters, CustomGeoPoint geographicPoint, Map<String, PetCollectionDTO> sitPets) {
		this.principal = principal;
		this.roles = roles;
		this.type = type;
		this.attributes = attributes;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.days = days;
		this.bookings = bookings;
		this.requestedBookings = requestedBookings;
		this.notification = notification;
		this.reviewCount = reviewCount;
		this.reviewSum = reviewSum;
		this.reviews = reviews;
		this.usedSitters = usedSitters;
		//this.geographicPoint = geographicPoint;
		this.sitPets = sitPets;
	}

	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes, String address, String city, String state, String zip, boolean[] days, List<BookingDTO> bookings, List<BookingDTO> requestedBookings, List<String> notification, int reviewCount, int reviewSum, List<ReviewDTO> reviews, Set<String> usedSitters, CustomGeoPoint geographicPoint) {
		this.principal = principal;
		this.roles = roles;
		this.type = type;
		this.attributes = attributes;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.days = days;
		this.bookings = bookings;
		this.requestedBookings = requestedBookings;
		this.notification = notification;
		this.reviewCount = reviewCount;
		this.reviewSum = reviewSum;
		this.reviews = reviews;
		this.usedSitters = usedSitters;
		//this.geographicPoint = new CustomGeoPoint(geographicPoint);
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

	public Set<String> getUsedSitters() {
		return usedSitters;
	}

	public void setUsedSitters(Set<String> usedSitters) {
		this.usedSitters = usedSitters;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDto userDto = (UserDto) o;
		return reviewCount == userDto.reviewCount &&
				reviewSum == userDto.reviewSum &&
				Objects.equals(principal, userDto.principal) &&
				Objects.equals(roles, userDto.roles) &&
				type == userDto.type &&
				Objects.equals(attributes, userDto.attributes) &&
				Objects.equals(address, userDto.address) &&
				Objects.equals(city, userDto.city) &&
				Objects.equals(state, userDto.state) &&
				Objects.equals(zip, userDto.zip) &&
				Arrays.equals(days, userDto.days) &&
				Objects.equals(bookings, userDto.bookings) &&
				Objects.equals(requestedBookings, userDto.requestedBookings) &&
				Objects.equals(notification, userDto.notification) &&
				Objects.equals(reviews, userDto.reviews) &&
				Objects.equals(usedSitters, userDto.usedSitters);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(principal, roles, type, attributes, address, city, state, zip, bookings, requestedBookings, notification, reviewCount, reviewSum, reviews, usedSitters);
		result = 31 * result + Arrays.hashCode(days);
		return result;
	}
}