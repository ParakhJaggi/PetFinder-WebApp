package petfinder.site.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetService;

import java.util.ArrayList;
import java.util.List;

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
}