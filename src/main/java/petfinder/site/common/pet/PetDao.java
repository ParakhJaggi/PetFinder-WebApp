package petfinder.site.common.pet;

import java.util.List;
import java.util.Optional;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import alloy.elasticsearch.ElasticSearchClientProvider;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.elasticsearch.PetElasticsearchRepository;

/**
 * Created by jlutteringer on 8/23/17.
 */
@Repository
public class PetDao {
	@Autowired
	private PetElasticsearchRepository petElasticsearchRepository;

	@Autowired
	private ElasticSearchClientProvider elasticSearchClientProvider;

	public Optional<PetDto> findPet(Long id) {

        return petElasticsearchRepository.find(id);

	}

	public Optional<PetDto> findPetLowTech(Long id) {
		RestHighLevelClient client = elasticSearchClientProvider.getClient();
		// Use the client to make your search and manually parse the results
		return Optional.empty();
	}

	public void save(PetDto pet) {
		petElasticsearchRepository.save(pet);
	}

	public PetCollectionDTO findByType(AnimalTypeRequest atr){
		return petElasticsearchRepository.findByType(atr);
	}
	public PetCollectionDTO findByFieldMatch(String term, List<Object> toMatch) throws PetException {
		return petElasticsearchRepository.findByFieldMatch(term, toMatch);
	}
	public PetCollectionDTO findByOwner(List<Object> toMatch){
		return petElasticsearchRepository.findByOwner(toMatch);
	}
	public void deletePet(Long id){
		petElasticsearchRepository.delete(id);
	}
}