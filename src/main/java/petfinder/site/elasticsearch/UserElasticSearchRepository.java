package petfinder.site.elasticsearch;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alloy.elasticsearch.ElasticSearchClientProvider;
import alloy.elasticsearch.ElasticSearchIndex;
import alloy.elasticsearch.ElasticSearchRepository.ElasticSearchJsonRepository;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.user.UserAuthenticationDto;
import petfinder.site.common.user.UserCollectionDTO;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jlutteringer on 1/16/18.
 */
@Service
public class UserElasticSearchRepository extends ElasticSearchJsonRepository<UserAuthenticationDto, String> {
	@Autowired
	public UserElasticSearchRepository(ElasticSearchClientProvider provider) {
		super(new ElasticSearchIndex(provider, "petfinder-users"), UserAuthenticationDto.class);
	}
}