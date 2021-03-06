package petfinder.site.test.unit;

import petfinder.site.common.Exceptions.PetException;
import petfinder.site.common.RestRequests.AnimalTypeRequest;
import petfinder.site.common.pet.PetCollectionDTO;
import petfinder.site.common.pet.PetDao;
import petfinder.site.common.pet.PetDto;

import java.util.ArrayList;
import java.util.List;

/**
 * An access object solely to be used to test the pet endpoints. Functions like a fake database
 * and has a few dummy entries inside of it which are filtered using java stream/lambda.
 * @author Laird
 */
public class AnimalTypeTester extends PetDao {
    public static final int CAT_NUM = 3, DOG_NUM = 2, RODENT_NUM = 3, REPTILE_NUM = 1, OTHER_NUM = 4;
    public static final int FLUFFY_NUM = 2;

    /**
     * find animals of the desired types
     * @param atr the tupes of animals that are to be returned.
     * @return all desired animals
     */
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
        if(presentTypes.contains("other")){
            pets.add(new PetDto((long)39, "a", "other"));
            pets.add(new PetDto((long)48788, "b", "other"));
            pets.add(new PetDto((long)30005, "c", "other"));
            pets.add(new PetDto((long)300, "d", "other"));
        }
        toReturn.setPets(pets);
        return toReturn;
    }

    /**
     * Finds all animals that match the desired attribute on one of the desired values
     * @param term the attribute name
     * @param toMatch any value that should match for the attribute
     * @return all desired pets
     * @throws PetException
     */
    @Override
    public PetCollectionDTO findByFieldMatch(String term, List<Object> toMatch) throws PetException {
        PetCollectionDTO toReturn = new PetCollectionDTO();
        List<PetDto> pets = new ArrayList<>(), petsTotal = new ArrayList<>();
        petsTotal.add(new PetDto((long) 1, "fluffy", "cat"));
        petsTotal.add(new PetDto((long) 4, "fluffy", "rodent"));
        petsTotal.add(new PetDto((long) 4, "catto", "cat"));
        petsTotal.add(new PetDto((long) 3000, "flufferino", "cat"));
        petsTotal.add(new PetDto((long) 123, "pup", "dog"));
        petsTotal.add(new PetDto((long) 45, "pupperino", "dog"));
        petsTotal.add(new PetDto((long) 1989, "felix", "rodent"));
        petsTotal.add(new PetDto((long) 74, "syvester", "rodent"));
        petsTotal.add(new PetDto((long) 89, "hi", "rodent"));
        petsTotal.add(new PetDto((long) 10008, "gross", "reptile"));
        petsTotal.add(new PetDto((long) 39, "a", "other"));
        petsTotal.add(new PetDto((long) 48788, "b", "other"));
        petsTotal.add(new PetDto((long) 30005, "c", "other"));
        petsTotal.add(new PetDto((long) 300, "d", "other"));
        if(term.equals( "type")) {
            petsTotal.stream().filter(s -> toMatch.contains(s.getType())).forEach(pets::add);
        } else if(term.equals("name")){
            //i am proud of this stream
            petsTotal.stream().filter(s -> toMatch.contains(s.getName())).forEach(pets::add);
        }else if(term.equals("id")){
            petsTotal.stream().filter(s -> toMatch.contains(s.getId())).forEach(pets::add);
        }
        toReturn.setPets(pets);
        return toReturn;
    }
}
