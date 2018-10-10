package petfinder.site.test.unit;

import org.junit.Before;
import org.junit.Test;
import petfinder.site.common.user.UserAuthenticationDto;
import petfinder.site.common.user.UserDto;

import static org.junit.Assert.assertEquals;

public class UserAuthenticationDTO_tester {

    public String username;
    public String password;
    public UserAuthenticationDto test;
    public UserDto userTest;
    @Before
    public void prep(){
        username = "TestyMcTestFace";
        password = "SecurePassword";
        userTest = new UserDto(username,null,null,null);
        test = new UserAuthenticationDto(userTest,password);
    }
    @Test
    public void testUsername(){
        assertEquals(test.getUser(),userTest);

    }
    @Test
    public void getPassword(){
        assertEquals(test.getPassword(),password);
    }

}
