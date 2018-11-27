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
        petService.save(pet);
        return pet;
    }

    @GetMapping(value = "getBookedPets", produces = "application/json")
    public PetCollectionDTO getSitPets(){
        return petService.getSitPets(userService);
    }
}