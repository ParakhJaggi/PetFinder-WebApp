package petfinder.site.test.unit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import petfinder.site.common.user.UserDto;

public class UserDTO_tester {

    public static String PRINCIPAL = "test@test.edu";
    public static UserDto.UserType userType = UserDto.UserType.SITTER;
    public static UserDto user;

    @Before
    public void prep(){
        user = new UserDto(PRINCIPAL, null, userType, null);
    }
    @Test
    public void testType(){
        assertEquals(userType.getClass().toString(), UserDto.UserType.SITTER.getClass().toString());
    }
    @Test
    public void testPrinciple(){
        assertEquals(user.getPrincipal(), PRINCIPAL);
    }
    @Test
    public void testRoles(){
        assertEquals(user.getRoles(), null);
    }
    @Test
    public void testAtributes(){
        assertEquals(user.getAttributes(), null);

    }

}
