package petfinder.site.test.unit;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.BeforeClass;
import petfinder.site.common.pet.PetService;
import petfinder.site.common.user.UserAuthenticationDto;
import petfinder.site.common.user.UserDao;
import petfinder.site.common.user.UserDto;
import petfinder.site.common.user.UserService;

import java.util.Optional;

public class UserTester extends UserDao {
    @Override
    public Optional<UserAuthenticationDto> findUserByPrincipal(String principal) {
        UserAuthenticationDto ret = new UserAuthenticationDto();
        if(principal.toLowerCase().contains("@baylor.edu")){
            ret.setPrincicple(new UserDto(principal,null,null,null),"baylor password");
        }

    return Optional.of(ret);
    }


}
