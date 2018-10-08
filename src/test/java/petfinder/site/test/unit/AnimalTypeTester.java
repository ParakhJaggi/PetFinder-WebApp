package petfinder.site.test.unit;

import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDao;
import petfinder.site.common.pet.PetDto;

import java.util.ArrayList;
import java.util.List;

public class AnimalTypeTester extends PetDao {
    public static final int CAT_NUM = 3, DOG_NUM = 2, RODENT_NUM = 3, REPTILE_NUM = 1, BIRD_NUM = 4;
    @Override
    public PetCollectionDTO findByType(AnimalTypeRequest atr){
        PetCollectionDTO toReturn = new PetCollectionDTO();
        List<String> presentTypes = atr.getTypes();
        List<PetDto> pets = new ArrayList<>();
        if(presentTypes.contains("cat")){
            pets.add(new PetDto((long)1, "fluffy", "cat"));
            pets.add(new PetDto((long)4, "catto", "cat"));
            pets.add(new PetDto((long)3000, "flufferino", "cat"));
        }
        if(presentTypes.contains("dog")){
            pets.add(new PetDto((long)123, "pup", "dog"));
            pets.add(new PetDto((long)45, "pupperino", "dog"));
        }
        if(presentTypes.contains("rodent")){
            pets.add(new PetDto((long)1989, "felix", "rodent"));
            pets.add(new PetDto((long)74, "syvester", "rodent"));
            pets.add(new PetDto((long)89, "hi", "rodent"));
        }
        if(presentTypes.contains("reptile")){
            pets.add(new PetDto((long)1, "gross", "reptile"));

        }
        if(presentTypes.contains("bird")){
            pets.add(new PetDto((long)39, "a", "bird"));
            pets.add(new PetDto((long)48788, "b", "bird"));
            pets.add(new PetDto((long)30005, "c", "bird"));
            pets.add(new PetDto((long)300, "d", "bird"));
        }
        toReturn.setPets(pets);
        return toReturn;
    }

}
