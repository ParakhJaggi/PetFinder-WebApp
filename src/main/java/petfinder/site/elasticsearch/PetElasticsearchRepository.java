package petfinder.site.elasticsearch;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import alloy.elasticsearch.ElasticSearchClientProvider;
import alloy.elasticsearch.ElasticSearchIndex;
import alloy.elasticsearch.ElasticSearchRepository.ElasticSearchJsonRepository;
import petfinder.site.common.AnimalTypeRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDto;

import javax.naming.directory.SearchResult;
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
	public PetCollectionDTO findByType(AnimalTypeRequest atr) {
		int count = atr.getCount();
		List<String> strings = atr.getTypes();
		//MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("type", );
		QueryBuilder queryBuilder = null;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		if(count == 1)
			queryBuilder = QueryBuilders.multiMatchQuery(TYPE, strings.get(0));
		if(count == 2)
			queryBuilder = QueryBuilders.multiMatchQuery(TYPE, strings.get(0), strings.get(1));
		if(count == 3)
			queryBuilder = QueryBuilders.multiMatchQuery(TYPE, strings.get(0), strings.get(1), strings.get(2));
		if(count == 4)
			queryBuilder = QueryBuilders.multiMatchQuery(TYPE, strings.get(0),strings.get(1), strings.get(2),strings.get(3));
		if(count == 5)
			queryBuilder = QueryBuilders.multiMatchQuery(TYPE, strings.get(0), strings.get(1), strings.get(2), strings.get(3), strings.get(4));
		sourceBuilder.query(queryBuilder);
		PetCollectionDTO results = new PetCollectionDTO();
		results.setPets( this.index.search(sourceBuilder, this.serializer));
		return results;
	}
}