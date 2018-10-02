package petfinder.site.elasticsearch;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import alloy.elasticsearch.ElasticSearchClientProvider;
import alloy.elasticsearch.ElasticSearchIndex;
import alloy.elasticsearch.ElasticSearchRepository.ElasticSearchJsonRepository;
import petfinder.site.common.AnimalTypeRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;

import javax.naming.directory.SearchResult;
import javax.xml.transform.Source;
import java.util.List;
import java.util.Optional;

/**
 * Created by jlutteringer on 2/7/18.
 */
@Service
public class PetElasticsearchRepository extends ElasticSearchJsonRepository<PetDto, Long> {
	public PetElasticsearchRepository(ElasticSearchClientProvider provider) {
		super(new ElasticSearchIndex(provider, "petfinder-pets"), PetDto.class);
	}
	public final String TYPE = "type";

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
}