package petfinder.site.test.unit;

import org.junit.BeforeClass;
import org.junit.Test;
import petfinder.site.common.pet.PetService;
import petfinder.site.common.user.UserService;
import static org.junit.Assert.*;


public class UserEndpointTester {
    private static UserService service;

    @BeforeClass
    public static void init(){
        service = new UserService();
        service.setUserDao(new UserTester());
    }

    @Test
    public void testBaylorEmails(){
        assertEquals(service.findUsersTest("Linda_Livingstone@baylor.edu").get().getPassword(),
                "baylor password");

    }


}
