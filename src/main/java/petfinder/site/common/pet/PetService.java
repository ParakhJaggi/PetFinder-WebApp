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
	final static Logger logger = Logger.getLogger(PetService.class.toString());

	public Optional<PetDto> findPet(Long id) {
		return petDao.findPet(id);
	}

	public void save(PetDto pet) {
		petDao.save(pet);
	}

	public PetCollectionDTO findAllCats(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setCat().generate());
	}

	public PetCollectionDTO findAllDogs(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setDog().generate());
	}

	public PetCollectionDTO findAllRodents(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setRodent().generate());
	}

	public PetCollectionDTO findAllBirds(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setBird().generate());
	}

	public PetCollectionDTO findAllReptiles(){
		return petDao.findByType(AnimalTypeRequestBuilder.getInstance().setReptile().generate());
	}

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

	public PetCollectionDTO findByType(AnimalTypeRequest atr){
		return petDao.findByType(atr);
	}
	public PetCollectionDTO findByFieldMatch(String term, List<Object> toMatch){
		try{
			return petDao.findByFieldMatch(term, toMatch);
		}catch(PetException p){
			logger.info("a pet exception was thrown");
			logger.severe(p.getMessage());
			return null;
		}
	}
}