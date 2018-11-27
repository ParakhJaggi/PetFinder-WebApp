package petfinder.site.test.unit;

import org.junit.BeforeClass;
import org.junit.Test;
import petfinder.site.common.Exceptions.UserException;
import petfinder.site.common.user.UserCollectionDTO;
import petfinder.site.common.user.UserDto;
import petfinder.site.common.user.UserService;

import java.util.Optional;

import static org.junit.Assert.*;


public class UserEndpointTester {
    private static UserService service;

    @BeforeClass
    public static void init(){
        service = new UserService();
        service.setUserDao(new UserTester());
    }

    @Test
    public void testDoesExist(){
        Optional<UserDto> o = service.findUserByPrincipal("bob@test.com");
        assertTrue(o.isPresent());
    }

    @Test
    public void testDoesNotExist(){
        Optional<UserDto> o = service.findUserByPrincipal("DOES_NOT_EXIST@NOPE.com");
        assertFalse(o.isPresent());
    }

    @Test
    public void testSitterCollection() throws UserException {
        //i will test to see if it is finding them for zip code 59718
        UserCollectionDTO o = service.getAvailableSitters("bob@test.com");
//        assertEquals(o.getUsers().size(), UserTester.ZIP_59718_COUNT_SITTER_ALL_DAYS + UserTester.ZIP_59718_COUNT_SITTER_WEEKENDS);

        o = service.getAvailableSitters("freddy@test.com");
  //      assertEquals(o.getUsers().size(),
  //              UserTester.ZIP_59718_COUNT_SITTER_ALL_DAYS );
    }
}
