package petfinder.site.endpoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import petfinder.site.common.AnimalTypeRequest;
import petfinder.site.common.AnimalTypeRequestBuilder;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;
import petfinder.site.common.pet.PetService;
import petfinder.site.common.user.UserDto;

/**
 * Created by jlutteringer on 8/23/17.
 */
@RestController
@RequestMapping("/api/pets")
public class PetEndpoint {
	@Autowired
	private PetService petService;

	/**
	 * @author laird
	 */
	@GetMapping(value = "/populateTestData")
	public void popData(){
		petService.save(new PetDto((long)1, "fluffy", "cat"));
		petService.save(new PetDto((long)2, "pupperino", "dog"));
		petService.save(new PetDto((long)3, "ratto", "rodent"));
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public Optional<PetDto> getPet(@PathVariable("id") Long id) {
		return petService.findPet(id);
	}

	@PostMapping(produces = "application/json")
	public PetDto savePet(@RequestBody PetDto pet) {
		petService.save(pet);
		return pet;
	}

	@GetMapping(value = "/all", produces = "application/json")
	public PetCollectionDTO getAll(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().generate());
		//return petService.findByType(new AnimalTypeRequest(true, false, false, false, false));
	}

	@GetMapping(value = "/cats", produces = "application/json")
	public PetCollectionDTO getCats(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().setCat().setDog().setRodent().generate());
		//return petService.findByType(new AnimalTypeRequest(true, false, false, false, false));
	}

	@GetMapping(value = "/dogs", produces = "application/json")
	public PetCollectionDTO getDogs(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().setDog().generate());
		//return petService.findByType(new AnimalTypeRequest(false, true, false, false, false));
	}

	@GetMapping(value = "/reptiles", produces = "application/json")
	public PetCollectionDTO getReptiles(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().setReptile().generate());
		//return petService.findByType(new AnimalTypeRequest(false, false, true, false, false));

	}

	@GetMapping(value = "/rodents", produces = "application/json")
	public PetCollectionDTO getRodents(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().setRodent().generate());
		//return petService.findByType(new AnimalTypeRequest(false, false, false, true, false));
	}

	@GetMapping(value = "/birds", produces = "application/json")
	public PetCollectionDTO getBirds(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().setBird().generate());
		//return petService.findByType(new AnimalTypeRequest(false, false, false, false, true));
	}
	@GetMapping(value = "/{cat}/{dog}/{rodent}/{bird}/{reptile}", produces = "application/json")
	public PetCollectionDTO getBirds(@PathVariable("cat") Boolean cat, @PathVariable("dog") Boolean dog,
									 @PathVariable("rodent") Boolean rodent, @PathVariable("bird") Boolean bird,
									 @PathVariable("reptile") Boolean reptile){
		AnimalTypeRequestBuilder buildRequest = AnimalTypeRequestBuilder.getInstance();
		if(cat)
			buildRequest.setCat();
		if(dog)
			buildRequest.setDog();
		if(bird)
			buildRequest.setBird();
		if(rodent)
			buildRequest.setRodent();
		if(reptile)
			buildRequest.setReptile();
		return petService.findByType(buildRequest.generate());
	}
}

