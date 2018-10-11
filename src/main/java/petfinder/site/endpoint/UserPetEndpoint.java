package petfinder.site.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;
import petfinder.site.common.pet.PetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/userPets")
public class UserPetEndpoint {
    @Autowired
    private PetService petService;

    @GetMapping("/getPets")
    public PetCollectionDTO getUsersPets(){
        List<Object> userName = new ArrayList<>();
        userName.add(SecurityContextHolder.getContext().getAuthentication().getName());
        return petService.findByFieldMatch("owner", userName);
    }

    @PostMapping(value = "/savePet",produces = "application/json")
    public PetDto savePet(@RequestBody PetDto pet) {
        //if(pet.getId() == null) {
        long petID = new Random().nextLong();
        if (petID < 0)
            petID *= -1;
        pet.setId(petID);
        pet.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        //}
        petService.save(pet);
        return pet;
    }
}