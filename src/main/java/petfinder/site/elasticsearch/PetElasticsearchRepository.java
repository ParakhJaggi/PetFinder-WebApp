package petfinder.site.elasticsearch;

import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import alloy.elasticsearch.ElasticSearchClientProvider;
import alloy.elasticsearch.ElasticSearchIndex;
import alloy.elasticsearch.ElasticSearchRepository.ElasticSearchJsonRepository;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jlutteringer on 2/7/18.
 */
@Service
public class PetElasticsearchRepository extends ElasticSearchJsonRepository<PetDto, Long> {
	public PetElasticsearchRepository(ElasticSearchClientProvider provider) {
		super(new ElasticSearchIndex(provider, "petfinder-pets"), PetDto.class);
	}
	public final String TYPE = "type";
	final String [] VALID_TYPES = {"type", "id", "name"};

	/**
	 * Author laird
	 * @param atr pet results
	 * @return pet results
	 */
	public PetCollectionDTO findByType(AnimalTypeRequest atr) {
		int count = atr.getCount();
		List<String> strings = atr.getTypes();
		QueryBuilder queryBuilder = null;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder qs = QueryBuilders.boolQuery();
		if(count == 0){
			//match on all animals
			sourceBuilder.query(QueryBuilders.matchAllQuery());
		}else {
			for (int i = 0; i < count; i++) {
				qs = qs.should(QueryBuilders.termQuery("type", strings.get(i)));
			}
		}
		sourceBuilder.query(qs);
		PetCollectionDTO results = new PetCollectionDTO();
		results.setPets( this.index.search(sourceBuilder, this.serializer));
		return results;
	}
	public PetCollectionDTO findByFieldMatch(String term, List<Object> toMatch) throws PetException{
		if(!Arrays.asList(VALID_TYPES).contains(term)){
			throw new PetException("field " + term + "not existent");
		}
		int count = toMatch.size();
		QueryBuilder queryBuilder = null;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder qs = QueryBuilders.boolQuery();
		if(count == 0){
			//match on all animals
			sourceBuilder.query(QueryBuilders.matchAllQuery());
		}else {
			for (int i = 0; i < count; i++) {
				qs = qs.should(QueryBuilders.termQuery(term, toMatch.get(i)));
			}
		}
		sourceBuilder.query(qs);
		PetCollectionDTO results = new PetCollectionDTO();
		results.setPets( this.index.search(sourceBuilder, this.serializer));
		return results;
	}
}