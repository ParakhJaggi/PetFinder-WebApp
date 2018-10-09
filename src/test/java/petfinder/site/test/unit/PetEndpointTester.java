package petfinder.site.test.unit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import petfinder.site.common.pet.PetService;

import java.util.ArrayList;
import java.util.List;

public class PetEndpointTester {

    private static PetService service;

    @BeforeClass
    public static void init(){
        service = new PetService();
        service.setPetDao(new AnimalTypeTester());
    }

    @Test
    public void testCats(){
        assertEquals(service.findAllCats().getPets().size(), AnimalTypeTester.CAT_NUM);
    }
    @Test
    public void testDogs(){
        assertEquals(service.findAllDogs().getPets().size(), AnimalTypeTester.DOG_NUM);
    }
    @Test
    public void testBirds(){
        assertEquals(service.findAllReptiles().getPets().size(), AnimalTypeTester.REPTILE_NUM);
    }
    @Test
    public void testRodents(){
        assertEquals(service.findAllRodents().getPets().size(), AnimalTypeTester.RODENT_NUM);
    }
    @Test
    public void testReptiles(){
        assertEquals(service.findAllReptiles().getPets().size(), AnimalTypeTester.REPTILE_NUM);
    }
    @Test
    public void testDogAndCat(){
        assertEquals(service.findByCustomType(true, true, false, false, false)
                .getPets().size(), AnimalTypeTester.CAT_NUM + AnimalTypeTester.DOG_NUM);
        assertEquals(service.findByCustomType(false, true, false, false, false)
                .getPets().size(), AnimalTypeTester.DOG_NUM);
        assertEquals(service.findByCustomType(true, true, true, true, true)
                .getPets().size(), AnimalTypeTester.CAT_NUM + AnimalTypeTester.DOG_NUM
                +AnimalTypeTester.BIRD_NUM + AnimalTypeTester.REPTILE_NUM + AnimalTypeTester.RODENT_NUM);
    }
    @Test
    public void testFieldMatch(){
        List<Object> animals = new ArrayList<>();
        List<Object> names = new ArrayList<>();
        List<Object> ids = new ArrayList<>();
        animals.add("cat");
        animals.add("dog");
        assertEquals(service.findByFieldMatch("type",animals)
                .getPets().size(), AnimalTypeTester.CAT_NUM + AnimalTypeTester.DOG_NUM);
        animals.add("reptile");
        animals.add("rodent");
        animals.add("bird");
        assertEquals(service.findByFieldMatch("type",animals)
                .getPets().size(), AnimalTypeTester.CAT_NUM + AnimalTypeTester.DOG_NUM
                +AnimalTypeTester.BIRD_NUM + AnimalTypeTester.REPTILE_NUM + AnimalTypeTester.RODENT_NUM + 1);
        names.add("fluffy");
        assertEquals(service.findByFieldMatch("name", names).getPets().size(), AnimalTypeTester.FLUFFY_NUM);
        names.add("zeek");
        names.add("chimp");
        assertEquals(service.findByFieldMatch("name", names).getPets().size(), AnimalTypeTester.FLUFFY_NUM);
        names.clear();
        assertEquals(service.findByFieldMatch("name", names).getPets().size(), 0);
        ids.add((long)1);
        assertEquals(service.findByFieldMatch("id", ids).getPets().size(), 1);
        ids.add((long)-1);
        assertEquals(service.findByFieldMatch("id", ids).getPets().size(), 1);
        ids.clear();
        assertEquals(service.findByFieldMatch("id", ids).getPets().size(), 0);
    }
}
