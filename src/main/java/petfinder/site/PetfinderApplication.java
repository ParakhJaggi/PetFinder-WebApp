package petfinder.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import petfinder.site.common.pet.PetDto;
import petfinder.site.common.pet.PetService;

import java.util.Optional;

/**
 * Created by jlutteringer on 8/22/17.
 */



@SpringBootApplication
public class PetfinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetfinderApplication.class, args);
        //PetService service = new PetService();

        //service.save(new PetDto(Long.parseLong("5"),"test","test"));
        //Optional<PetDto> dto = service.findPet(Long.parseLong("4"));

      //  System.out.println(dto.get().getName());


	}
}
//branchtest
//branchtest2
/*Test*/