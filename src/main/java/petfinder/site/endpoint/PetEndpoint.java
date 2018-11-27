package petfinder.site.endpoint;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Email;
import org.json.JSONArray;
import org.json.JSONObject;
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
	 * Note: this is an endpoint just for testing purposes.
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

	/**
	 * @author Laird
	 * @param id the id of the animal
	 * @return the pet of the id.
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	public Optional<PetDto> getPet(@PathVariable("id") Long id) {
		return petService.findPet(id);
	}

	/**
	 * @author Laird
	 * @param pet the pet to be saved in Elasticsearch
	 * @return {@link PetDto} the given pet
	 * Note: a new random id is associated with the pet.
	 */
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

	/**
	 * @author Laird
	 * @param pet the pet to edit
	 * @return the given pet
	 */
	@PostMapping(value = "/edit", produces = "application/json")
	public PetDto editPet(@RequestBody PetDto pet) {
		petService.editPet(pet);
		return pet;
	}


	/**
	 * Way to get all pets in the system
	 * @author Laird
	 * @return all pets in elasticsearch
	 */
	@GetMapping(value = "/all", produces = "application/json")
	public PetCollectionDTO getAll(){
		return petService.findByType(AnimalTypeRequestBuilder.getInstance().generate());
		//return petService.findByType(new AnimalTypeRequest(true, false, false, false, false));
	}

	/**
	 * @author Laird
	 * @return all cats in elasticsearch
	 */
	@GetMapping(value = "/cats", produces = "application/json")
	public PetCollectionDTO getCats(){
		//return petService.findByFieldMatch("type", AnimalTypeRequestBuilder.getInstance().setCat().generate().getObjects());
		return petService.findAllCats();
	}

	/**
	 * @author Laird
	 * @return all dogs in elasticsearch
	 */
	@GetMapping(value = "/dogs", produces = "application/json")
	public PetCollectionDTO getDogs(){
		return petService.findAllDogs();
	}

	/**
	 * @author Laird
	 * @return all reptiles in elasticsearch
	 */
	@GetMapping(value = "/reptiles", produces = "application/json")
	public PetCollectionDTO getReptiles(){
		return petService.findAllReptiles();
	}

	/**
	 * @author Laird
	 * @return all rodents in elasticsearch
	 */
	@GetMapping(value = "/rodents", produces = "application/json")
	public PetCollectionDTO getRodents(){
		return petService.findAllRodents();
	}

	/**
	 * Way for a user to get custom search of all animals in elasticsearch
	 * @author Laird
	 * @return all desired animals in elasticsearch
	 */
	@GetMapping(value = "/other", produces = "application/json")
	public PetCollectionDTO getOther(){
		return petService.findAllOther();
	}

	/**
	 * @author Laird
	 * @param cat indicates if cats should be included
	 * @param dog indicates if dogs should be included
	 * @param rodent ndicates if rodents should be included
	 * @param other indicates if birds should be included
	 * @param reptile indicates if reptiles should be included
	 * @return
	 */
	@GetMapping(value = "/{cat}/{dog}/{rodent}/{other}/{reptile}", produces = "application/json")
	public PetCollectionDTO getOther(@PathVariable("cat") Boolean cat, @PathVariable("dog") Boolean dog,
									 @PathVariable("rodent") Boolean rodent, @PathVariable("other") Boolean other,
									 @PathVariable("reptile") Boolean reptile){
		return petService.findByCustomType(cat, dog, rodent, other, reptile);
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

