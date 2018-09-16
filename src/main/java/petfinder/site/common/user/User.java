package petfinder.site.common.user;

import petfinder.site.common.Rating;
import petfinder.site.common.pet.Pet;

import java.util.List;

public class User {
    private Long user_id;
    private String name;
    private String city;
    private String state;
    private Boolean is_sitter;
    private Boolean is_owner;
    private Rating rating;
    private List<Pet> pet;


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getIs_sitter() {
        return is_sitter;
    }

    public void setIs_sitter(Boolean is_sitter) {
        this.is_sitter = is_sitter;
    }

    public Boolean getIs_owner() {
        return is_owner;
    }

    public void setIs_owner(Boolean is_owner) {
        this.is_owner = is_owner;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }
}
