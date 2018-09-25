package petfinder.site.endpoint;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class TestEndpoint {
    @GetMapping("/")
    public String run(){

    return "{"+'"'+"data"+'"'+":"+'"'+ "Hello World!!!!!!"+'"'+"}";
    }

    @PostMapping(value = "/testsend")
    public void register(@RequestBody String request) {
        System.out.println(request);
    }
}