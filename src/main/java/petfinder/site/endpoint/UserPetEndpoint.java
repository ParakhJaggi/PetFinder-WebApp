package petfinder.site.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;
import petfinder.site.common.pet.PetService;
import petfinder.site.common.user.UserService;

import java.util.*;
@RestController
@RequestMapping("/api/userPets")
public class UserPetEndpoint {
    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    /**
     * @author Laird
     * @return all pets that are owned by the current user
     * @see PetCollectionDTO
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

    /**
     * @author Laird
     * @param p the pet that is to be updated in the system
     * @see PetDto
     */
    @PostMapping("/update")
    public void updatePet(@RequestBody PetDto p){
        petService.update(p);
    }

    /**
     * @author Laird
     * @param pet The pet that is to be saved into the system.
     * @return the saved Pet
     * Note that a pet will not be successfully saved if a duplicate pet of the same owner is found in the system.
     */
    @PostMapping(value = "/savePet",produces = "application/json")
    public PetDto savePet(@RequestBody PetDto pet) {
        petService.save(pet);
        return pet;
    }

    /**
     * @author Laird
     * @return all pets of the current sitter
     * Note that the endpoint will fail if the current user is an owner instead of a sitter
     */
    @GetMapping(value = "getBookedPets", produces = "application/json")
    public PetCollectionDTO getSitPets(){
        return petService.getSitPets(userService);
    }
}