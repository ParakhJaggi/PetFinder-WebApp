package petfinder.site.endpoint;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.RestRequests.AnimalTypeRequestBuilder;
import petfinder.site.common.RestRequests.SingleFieldRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;
import petfinder.site.common.pet.PetService;

/**
 * Created by jlutteringer on 8/23/17.
 * Modified by laird 10/2/2018
 */
@RestController
@RequestMapping("/pets")
public class PetEndpoint {
	@Autowired
	private PetService petService;

	/**
	 * @author laird
	 */
	@GetMapping(value = "/populateTestData")
	public void popData(){
		long petID = new Random().nextLong();
		if(petID < 0)
			petID*=-1;
		petService.save(new PetDto(petID, "fluffy", "cat", "greg", "albino"));
		petService.save(new PetDto((long)2, "doggo", "dog"));
		petService.save(new PetDto((long)3, "ratto", "rodent"));
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public Optional<PetDto> getPet(@PathVariable("id") Long id) {
		return petService.findPet(id);
	}

	@PostMapping(produces = "application/json")
	public PetDto savePet(@RequestBody PetDto pet) {
		//if(pet.getId() == null) {
			long petID = new Random().nextLong();
			if (petID < 0)
				petID *= -1;
			pet.setId(petID);
		//}
		petService.save(pet);
		return pet;
	}
	@PostMapping(value = "/edit", produces = "application/json")
	public PetDto editPet(@RequestBody PetDto pet) {
		petService.editPet(pet);
		return pet;
	}


	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/all", produces = "application/json")
	public PetCollectionDTO getAll(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().generate());
		//return petService.findByType(new AnimalTypeRequest(true, false, false, false, false));
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/cats", produces = "application/json")
	public PetCollectionDTO getCats(){
		//return petService.findByFieldMatch("type", AnimalTypeRequestBuilder.getInstance().setCat().generate().getObjects());
		return petService.findAllCats();
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/dogs", produces = "application/json")
	public PetCollectionDTO getDogs(){
		return petService.findAllDogs();
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/reptiles", produces = "application/json")
	public PetCollectionDTO getReptiles(){
		return petService.findAllReptiles();
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/rodents", produces = "application/json")
	public PetCollectionDTO getRodents(){
		return petService.findAllRodents();
	}

	/**
	 * @author Laird
	 * @return
	 */
	@GetMapping(value = "/birds", produces = "application/json")
	public PetCollectionDTO getBirds(){
		return petService.findAllBirds();
	}

	/**
	 * @author Laird
	 * @param cat
	 * @param dog
	 * @param rodent
	 * @param bird
	 * @param reptile
	 * @return
	 */
	@GetMapping(value = "/{cat}/{dog}/{rodent}/{bird}/{reptile}", produces = "application/json")
	public PetCollectionDTO getBirds(@PathVariable("cat") Boolean cat, @PathVariable("dog") Boolean dog,
									 @PathVariable("rodent") Boolean rodent, @PathVariable("bird") Boolean bird,
									 @PathVariable("reptile") Boolean reptile){
		return petService.findByCustomType(cat, dog, rodent, bird, reptile);
	}

	/**
	 * @author Laird
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/byType", produces = "application/json")
	public PetCollectionDTO getByGenericType(@RequestBody SingleFieldRequest request) {
		return petService.findByFieldMatch(request.getField(), request.getDesiredValues());
	}
}

