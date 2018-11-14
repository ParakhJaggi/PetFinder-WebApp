package petfinder.site.common.user;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import petfinder.site.common.CustomGeoPoint;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.elasticsearch.UserElasticSearchRepository;

/**
 * Created by jlutteringer on 8/23/17.
 */
@Repository
public class UserDao {
	@Autowired
	private UserElasticSearchRepository repository;

	// JOHN
	public Optional<UserAuthenticationDto> findUser(String id) {
//		return repository.find(id, UserAuthenticationDto.class);
		return null;
	}

	public Optional<UserAuthenticationDto> findUserByPrincipal(String principal) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		String queryString = String.format("user.principal===\"%s\"", principal.replace("\"", ""));
		searchSourceBuilder.query(QueryBuilders.queryStringQuery(queryString));
		return repository.search(searchSourceBuilder).stream().filter(x->x.getMomento().equals(principal)).findFirst();
	}

	public void save(UserAuthenticationDto userAuthentication) {

		repository.save(userAuthentication);
	}
	public boolean delete(String principal){
		repository.delete(principal);
		//now see if the removal was successful by checking if the user can now be found
		return findUserByPrincipal(principal).isPresent();
	}

	public UserCollectionDTO findByFieldMatch(String term, List<Object> toMatch){
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
		UserCollectionDTO results = new UserCollectionDTO();
		List<UserDto> users = new LinkedList<>();
		List<UserAuthenticationDto> res = this.repository.search(sourceBuilder);
		res.forEach(x->users.add(x.getUser()));
		results.setUsers( users);
		return results;
	}

	public UserCollectionDTO findSitters(CustomGeoPoint p){
		QueryBuilder queryBuilder = null;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		GeoDistanceQueryBuilder g = QueryBuilders.geoDistanceQuery("geographicPoint");
		org.elasticsearch.common.geo.GeoPoint altGeoPoint = new org.elasticsearch.common.geo.GeoPoint();
		altGeoPoint.resetLon(p.getLon());
		altGeoPoint.resetLat(p.getLat());
		g.point(altGeoPoint).distance("35 km");
		SortBuilder sb = SortBuilders.geoDistanceSort("geographicPoint", p.getLat(), p.getLon()).unit(DistanceUnit.KILOMETERS);
//		BoolQueryBuilder qs = QueryBuilders.boolQuery();
//		BoolQueryBuilder q = QueryBuilders.boolQuery();
//		//BoolQueryBuilder r = QueryBuilders.boolQuery();
//
//		//city or zip
//		qs.must(QueryBuilders.termQuery("user.city", city))
//				.must(QueryBuilders.termQuery("user.state", state));
//		q.should(qs)
//		        .should(QueryBuilders.termQuery("user.zip", zip));
//	//	r.must(QueryBuilders.termQuery("user.type", "petfinder.site.common.user.UserDto.UserType.SITTER"));
//				//.must(q);
		sourceBuilder.query(g).size(100).sort(sb);
		UserCollectionDTO results = new UserCollectionDTO();
		List<UserDto> users = new LinkedList<>();
		List<UserAuthenticationDto> res = this.repository.search(sourceBuilder);
		res.forEach(x->users.add(x.getUser()));
		results.setUsers( users);
		return results;
	}
}