package petfinder.site.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class TestEndpoint {
    @GetMapping("/")
    public String run(){

    return "{"+'"'+"data"+'"'+":"+'"'+ "Hello World!"+'"'+"}";
    }
}