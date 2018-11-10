package petfinder.site.common.pet;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.RestRequests.AnimalTypeRequestBuilder;
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
		petDao.save(pet);
	}

	/**
	 * @author Laird
	 * @return
	 */
	public PetCollectionDTO findAllCats(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setCat().generate());
	}

	/**
	 * @author Laird
	 * @return
	 */
	public PetCollectionDTO findAllDogs(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setDog().generate());
	}

	/**
	 * @author Laird
	 * @return
	 */
	public PetCollectionDTO findAllRodents(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setRodent().generate());
	}

	/**
	 * @author Laird
	 * @return
	 */
	public PetCollectionDTO findAllBirds(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setBird().generate());
	}

	/**
	 * @author Laird
	 * @return
	 */
	public PetCollectionDTO findAllReptiles(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setReptile().generate());
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
	public PetCollectionDTO findByCustomType(boolean cat, boolean dog,
											 boolean rodent, boolean bird, boolean reptile){
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
		return petDao.findByType(buildRequest.generate());
	}

	/**
	 * @author Laird
	 * @param atr
	 * @return
	 */
	public PetCollectionDTO findByType(AnimalTypeRequest atr){
		return petDao.findByType(atr);
	}

	/**
	 * @author Laird
	 * @param term
	 * @param toMatch
	 * @return
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
	 * @author Laird
	 * @param newPet
	 */
	public void editPet(PetDto newPet){
		petDao.deletePet(newPet.getId());
		petDao.save(newPet);
	}
}