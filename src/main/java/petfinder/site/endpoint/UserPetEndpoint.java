package petfinder.site.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.RestRequests.AnimalTypeRequestBuilder;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;
import petfinder.site.common.pet.PetService;
import petfinder.site.common.user.BookingDTO;
import petfinder.site.common.user.UserDto;
import petfinder.site.common.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userPets")
public class UserPetEndpoint {
    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    /**
     * @author Laird
     * @return
     */
    @GetMapping("/getPets")
    public PetCollectionDTO getUsersPets(){
        List<Object> userName = new ArrayList<>();
        userName.add(SecurityContextHolder.getContext().getAuthentication().getName());
        return petService.findByOwner(userName);
    }
    @GetMapping("/ugly")
    public PetCollectionDTO getPetsWeirdWay(){
        return getUsersPets();
        /*
        PetCollectionDTO ret =  petService.findByType(AnimalTypeRequestBuilder.getInstance().generate());
        List<PetDto> pets = ret.getPets().stream().filter(s->s.getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())).collect(Collectors.toList());
        ret.setPets(pets);
        return ret;
        */
    }
    @PostMapping("/update")
    public void updatePet(@RequestBody PetDto p){
        petService.update(p);
    }

    /**
     * @author Laird
     * @param pet
     * @return
     */
    @PostMapping(value = "/savePet",produces = "application/json")
    public PetDto savePet(@RequestBody PetDto pet) {
        //if(pet.getId() == null) {
        long petID = new Random().nextLong();
        if (petID < 0)
            petID *= -1;
        pet.setId(petID);
        pet.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        //}
        //make sure that the owner does not double list an animal
        PetCollectionDTO pdo = getUsersPets();
        if(pdo.getPets().stream().noneMatch(x  ->
                x.getName().equals(pet.getName()) &&
                x.getType().equals(pet.getType()) &&
                x.getSubtype().equals(pet.getSubtype()))){
            petService.save(pet);
        }
        return pet;
    }

    @GetMapping(value = "getBookedPets", produces = "application/json")
    public PetCollectionDTO getSitPets(){
        Set<PetDto> setOfPets = new HashSet<>();
        PetCollectionDTO toReturn = new PetCollectionDTO();
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserDto> retrieved = userService.findUserByPrincipal(principal);
        if(!retrieved.isPresent() || retrieved.get().getType() == UserDto.UserType.OWNER){
            return null;
        }
        UserDto user = retrieved.get();
        for(BookingDTO b : user.getBookings()){
            List<Object> toMatch = new ArrayList<>();
            toMatch.add(b.getPrincipal());
            setOfPets.addAll((petService.findByOwner(toMatch)).getPets());
        }
        toReturn.setPets(setOfPets.stream().collect(Collectors.toList()));
        return toReturn;
    }
}