package petfinder.site.common.pet;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.RestRequests.AnimalTypeRequestBuilder;
import petfinder.site.common.user.BookingDTO;
import petfinder.site.common.user.UserAuthenticationDto;
import petfinder.site.common.user.UserDto;
import petfinder.site.common.user.UserService;
import petfinder.site.endpoint.PetEndpoint;

/**
 * Created by jlutteringer on 8/23/17.
 */
@Service
public class PetService {
	@Autowired
	private PetDao petDao;

	//only for unit testing!
	public void setPetDao(PetDao petDao) {
		this.petDao = petDao;
	}

	final static Logger logger = Logger.getLogger(PetService.class.toString());

	public Optional<PetDto> findPet(Long id) {
		return petDao.findPet(id);
	}

	public void save(PetDto pet) {
		List<Object> userName = new ArrayList<>();
		userName.add(SecurityContextHolder.getContext().getAuthentication().getName());
		PetCollectionDTO pdo = findByOwner(userName);

		long petID = new Random().nextLong();
		if (petID < 0)
			petID *= -1;
		pet.setId(petID);
		pet.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());

		//make sure that the owner does not double list an animal
		if(pdo.getPets().stream().noneMatch(x  ->
				x.getName().equals(pet.getName()) &&
						x.getType().equals(pet.getType()) &&
						x.getSubtype().equals(pet.getSubtype()))){
			petDao.save(pet);
		}
	}

	public void update(PetDto p){
		Optional<PetDto> toCheck = petDao.findPet(p.getId());
		if(toCheck.isPresent() && toCheck.get().getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
			PetDto oldVersion = toCheck.get();
			oldVersion.setName(oldVersion.getName());
			oldVersion.setPreferences(oldVersion.getPreferences());
			petDao.save(p);
		}
	}

	/**
	 * Gets the pets of the current user.
	 * @author Laird
	 * @param us the service that can be used to get users
	 * @return all pets of the currently logged in user
	 */
	public PetCollectionDTO getSitPets(UserService us){
		Set<PetDto> setOfPets = new HashSet<>();
		PetCollectionDTO toReturn = new PetCollectionDTO();
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserDto> retrieved = us.findUserByPrincipal(principal);
		if(!retrieved.isPresent() || retrieved.get().getType() == UserDto.UserType.OWNER){
			return null;
		}
		UserDto user = retrieved.get();
		for(BookingDTO b : user.getBookings()){
			List<Object> toMatch = new ArrayList<>();
			toMatch.add(b.getPrincipal());
			setOfPets.addAll((findByOwner(toMatch)).getPets());
		}
		toReturn.setPets(setOfPets.stream().collect(Collectors.toList()));
		return toReturn;
	}

	/**
	 * Gets all cats in the DB.
	 * @author Laird
	 * @return all cats
	 */
	public PetCollectionDTO findAllCats(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setCat().generate());
	}

	/**
	 * Gets all dogs in the DB
	 * @author Laird
	 * @return all dogs
	 */
	public PetCollectionDTO findAllDogs(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setDog().generate());
	}

	/**
	 * Gets all rodents in the DB
	 * @author Laird
	 * @return all rodents
	 */
	public PetCollectionDTO findAllRodents(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setRodent().generate());
	}

	/**
	 * Gets all birds in the DB
	 * @author Laird
	 * @return all birds
	 */
	public PetCollectionDTO findAllOther(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setOther().generate());
	}

	/**
	 * Gets all reptiles in the DB
	 * @author Laird
	 * @return all reptiles
	 */
	public PetCollectionDTO findAllReptiles(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setReptile().generate());
	}

	/**
	 * @author Laird
	 * @param cat if cats should be included in the results
	 * @param dog if dogs should be included in the results
	 * @param rodent if rodents should be included in the results
	 * @param other if birds should be included in the results
	 * @param reptile if reptiles should be included in the results
	 * @return all pets that match any of the set types
	 */
	public PetCollectionDTO findByCustomType(boolean cat, boolean dog,
											 boolean rodent, boolean other, boolean reptile){
		AnimalTypeRequestBuilder buildRequest = AnimalTypeRequestBuilder.getInstance();
		if(cat)
			buildRequest.setCat();
		if(dog)
			buildRequest.setDog();
		if(other)
			buildRequest.setOther();
		if(rodent)
			buildRequest.setRodent();
		if(reptile)
			buildRequest.setReptile();
		return petDao.findByType(buildRequest.generate());
	}

	/**
	 * @author Laird
	 * @param atr all animal types that should be searched for
	 * @return all pets
	 */
	public PetCollectionDTO findByType(AnimalTypeRequest atr){
		return petDao.findByType(atr);
	}

	/**
	 * @author Laird
	 * @param term the name of the attribute that is to be matched to
	 * @param toMatch all objects that can be matched
	 * @return all pets that match on an attribute with one of the values contained in toMatch
	 */
	public PetCollectionDTO findByFieldMatch(String term, List<Object> toMatch){
		try{
			return petDao.findByFieldMatch(term, toMatch);
		}catch(PetException p){
			logger.info("a pet exception was thrown");
			logger.severe(p.getMessage());
			return null;
		}
	}

	public PetCollectionDTO findByOwner(List<Object> toMatch){
		return petDao.findByOwner(toMatch);
	}
	/**
	 * Way for an existing pet to be changed without having an id randomly assigned to it.
	 * @author Laird
	 * @param changePet the pet whose name is to be changed
	 */
	public void editPet(PetDto changePet){
		petDao.deletePet(changePet.getId());
		petDao.save(changePet);
	}
}