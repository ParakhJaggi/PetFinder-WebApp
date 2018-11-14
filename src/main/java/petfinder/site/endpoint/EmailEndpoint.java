package petfinder.site.endpoint;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Email;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailEndpoint {
    @GetMapping(value = "/TestEmail/{email:.+}")
    public void testEmail(@PathVariable("email") String email) throws MailjetSocketTimeoutException, MailjetException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("141f6e47ca4cc452b41aaa540312bc7a", "d8acde824e69d34ac0c55def4a1fbf12");
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "parakh_jaggi@baylor.edu")
                .property(Email.FROMNAME, "Group 4 admin")
                .property(Email.SUBJECT, "Test Email!")
                .property(Email.TEXTPART, "Dear User, This is a test email!")
                .property(Email.RECIPIENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("Email",email)));


        response = client.post(request);
        System.out.println(response.getData());
    }
}
