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
     public static final String emailEndings[] = {"baylor password","gmail password", "yahoo password", "hotmail password","outlook password"};
    @Override
    public Optional<UserAuthenticationDto> findUserByPrincipal(String principal) {
        UserAuthenticationDto ret = new UserAuthenticationDto();
        if(principal.toLowerCase().contains("@baylor")){
            ret.setPrincicple(new UserDto(principal,null,null,null),emailEndings[0]);
        } else if(principal.toLowerCase().contains("@gmail")){
            ret.setPrincicple(new UserDto(principal,null,null,null),emailEndings[1]);
        } else if(principal.toLowerCase().contains("@yahoo")){
            ret.setPrincicple(new UserDto(principal,null,null,null),emailEndings[2]);
        } else if(principal.toLowerCase().contains("@hotmail")){
            ret.setPrincicple(new UserDto(principal,null,null,null),emailEndings[3]);
        } else if(principal.toLowerCase().contains("@outlook")){
            ret.setPrincicple(new UserDto(principal,null,null,null),emailEndings[4]);
        } else{
            ret.setPrincicple(new UserDto(principal,null,null,null),"Test");
        }



    return Optional.of(ret);
    }


}
