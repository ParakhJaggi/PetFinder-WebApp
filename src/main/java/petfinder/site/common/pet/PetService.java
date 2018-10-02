package petfinder.site.common.pet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.RestRequests.AnimalTypeRequest;

/**
 * Created by jlutteringer on 8/23/17.
 */
@Service
public class PetService {
	@Autowired
	private PetDao petDao;

	public Optional<PetDto> findPet(Long id) {
		return petDao.findPet(id);
	}

	public void save(PetDto pet) {
		petDao.save(pet);
	}

	public PetCollectionDTO findByType(AnimalTypeRequest atr){
		return petDao.findByType(atr);
	}
	public PetCollectionDTO findByFieldMatch(String term, List<Object> toMatch) throws PetException {
		return petDao.findByFieldMatch(term, toMatch);
	}
}