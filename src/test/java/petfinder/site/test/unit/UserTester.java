package petfinder.site.test.unit;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.BeforeClass;
import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.Exceptions.UserException;
import petfinder.site.common.pet.PetService;
import petfinder.site.common.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Laird
 */
public class UserTester extends UserDao {
    // public static final String emailEndings[] = {"baylor password","gmail password", "yahoo password", "hotmail password","outlook password"};
     private static List<UserDto> usersTotal = null;
     public static final int ZIP_59718_COUNT_SITTER_ALL_DAYS = 4,
                             ZIP_59718_COUNT_SITTER_WEEKENDS = 1;
     static {
         usersTotal = new ArrayList<>();
         boolean [] allDays = {true, true, true, true, true, true, true},
                    weekdays = {true, true, true, true, true, false, false},
                    weekEnds = {false, false, false, false, false, true, true};
         usersTotal.add(new UserDto("bob@test.com",
                 null,
                 UserDto.UserType.OWNER,
                 null,
                 "1000 west wallaby way",
                 "clemson",
                 "NC",
                 "59718", allDays
                 ));
         usersTotal.add(new UserDto("fred@test.com",
                 null,
                 UserDto.UserType.OWNER,
                 null,
                 "1001 west wallaby way",
                 "clemson",
                 "NC",
                 "12345", weekdays));
         usersTotal.add(new UserDto("freddy@test.com",
                 null,
                 UserDto.UserType.OWNER,
                 null,
                 "none of your business road",
                 "bozeman",
                 "MT",
                 "59718", weekEnds));
         usersTotal.add(new UserDto("plop@plop.com",
                 null,
                 UserDto.UserType.OWNER,
                 null,
                 "pennsylvania avenue",
                 "washington D.C.",
                 "DC",
                 "99999", allDays));

         usersTotal.add(new UserDto("one@sitter.com",
                 null,
                 UserDto.UserType.SITTER,
                 null,
                 "one",
                 "BOZEMAN",
                 "MT",
                 "59718", allDays));
         usersTotal.add(new UserDto("two@sitter.com",
                 null,
                 UserDto.UserType.SITTER,
                 null,
                 "one",
                 "BOZEMAN",
                 "MT",
                 "59718", allDays));
         usersTotal.add(new UserDto("three@sitter.com",
                 null,
                 UserDto.UserType.SITTER,
                 null,
                 "one",
                 "BOZEMAN",
                 "MT",
                 "59718", allDays));
         usersTotal.add(new UserDto("four@sitter.com",
                 null,
                 UserDto.UserType.SITTER,
                 null,
                 "one",
                 "BOZEMAN",
                 "MT",
                 "59718", allDays));

         usersTotal.add(new UserDto("five@sitter.com",
                 null,
                 UserDto.UserType.SITTER,
                 null,
                 "one",
                 "Butte",
                 "MT",
                 "59701", allDays));

         usersTotal.add(new UserDto("six@sitter.com",
                 null,
                 UserDto.UserType.SITTER,
                 null,
                 "one",
                 "Waco",
                 "TX",
                 "76798", allDays));

         usersTotal.add(new UserDto("seven@sitter.com",
                 null,
                 UserDto.UserType.SITTER,
                 null,
                 "one",
                 "Waco",
                 "TX",
                 "59718", weekdays));
     }

    @Override
    public Optional<UserAuthenticationDto> findUserByPrincipal(String principal) {
        for(UserDto u : usersTotal){
            if(u.getPrincipal().equals(principal)){
                return Optional.of(new UserAuthenticationDto(u, null));
            }
        }
        return Optional.empty();
    }

    /**
     * This allows testing to be performed without actually using elasticsearch.
     * Filtering is performed using streams.
     * @author Laird
     * @param term the attribute name to match by
     * @param toMatch the allowable values for the field name
     * @return all matches
     */
    @Override
    public UserCollectionDTO findByFieldMatch(String term, List<Object> toMatch)  {
        UserCollectionDTO toReturn = new UserCollectionDTO();
        List<UserDto> users = new ArrayList<>();
        if(term.contains( "zip")) {
            usersTotal.stream().filter(s -> toMatch.contains(s.getZip())).forEach(users::add);
        } else if(term.contains("principal")){
            usersTotal.stream().filter(s -> toMatch.contains(s.getPrincipal())).forEach(users::add);
        }else if(term.contains("id")){
            usersTotal.stream().filter(s -> toMatch.contains(s.getZip())).forEach(users::add);
        }else if(term.contains("city")){
            usersTotal.stream().filter(s -> toMatch.contains(s.getCity())).forEach(users::add);
        }else if(term.contains("state")){
            usersTotal.stream().filter(s -> toMatch.contains(s.getState())).forEach(users::add);
        }else if(term.contains("type")){
            usersTotal.stream().filter(s -> toMatch.contains(s.getType())).forEach(users::add);
        }
        toReturn.setUsers(users);
        return toReturn;
    }

}
