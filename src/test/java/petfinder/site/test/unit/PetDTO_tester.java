package petfinder.site.test.unit;

import org.junit.Before;
import org.junit.Test;
import petfinder.site.common.pet.PetDto;

import static org.junit.Assert.assertEquals;
import static petfinder.site.test.unit.UserDTO_tester.PRINCIPAL;

public class PetDTO_tester {
    public Long l;
    public String name;
    public String type;
    public PetDto test;
    @Before
    public void prep(){
        l=Long.MIN_VALUE;
        name = "TestyMcTestFace";
        type = "Dog";
        test = new PetDto(l,name,type);

    }
    @Test
    public void testID(){
        assertEquals(test.getId(),l);
    }
    @Test
    public void testName(){
        assertEquals(test.getName(),name);
    }
    @Test
    public void testType(){
        assertEquals(test.getType(), type);
    }
    @Test
    public void testSetters(){
        Long l2 = Long.parseLong("2");
        test.setId(l2);
        test.setName("Test");
        test.setType("Cat");
        assertEquals(test.getId(),l2);
        assertEquals(test.getName(),"Test");
        assertEquals(test.getType(),"Cat");



    }

}
