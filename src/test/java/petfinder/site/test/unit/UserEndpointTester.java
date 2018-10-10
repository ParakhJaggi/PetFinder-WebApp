package petfinder.site.test.unit;

import org.junit.BeforeClass;
import org.junit.Test;
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
                UserTester.emailEndings[0]);
    }
    @Test
    public void testGoogleEmails(){
        assertEquals(service.findUsersTest("Linda_Livingstone@Gmail.com").get().getPassword(),
                UserTester.emailEndings[1]);
    }
    @Test
    public void testYahooEmails(){
        assertEquals(service.findUsersTest("Linda_Livingstone@Yahoo.com").get().getPassword(),
                UserTester.emailEndings[2]);
    }
    @Test
    public void testHotmailEmails(){
        assertEquals(service.findUsersTest("Linda_Livingstone@Hotmail.com").get().getPassword(),
                UserTester.emailEndings[3]);
    }
    @Test
    public void testOutlookEmails(){
        assertEquals(service.findUsersTest("Linda_Livingstone@Outlook.com").get().getPassword(),
                UserTester.emailEndings[4]);
    }
    @Test
    public void testPrincipleReturn(){
        String test = "Linda_Livingstone@baylor.edu";
        assertEquals(service.findUsersTest(test).get().getUser().getPrincipal(),
              test );
    }


}
