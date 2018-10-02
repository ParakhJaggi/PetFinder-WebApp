package petfinder.site.common.RestRequests;

import java.util.ArrayList;
import java.util.List;

public class AnimalTypeRequest {
    boolean cat, dog, rodent, reptile, bird;
    public AnimalTypeRequest(boolean cat, boolean dog,boolean rodent ,boolean reptile,boolean bird){
        this.cat = cat;
        this.dog = dog;
        this.rodent = rodent;
        this.reptile = reptile;
        this.bird = bird;
    }
    public int getCount(){
        int count = 0;
        count = cat ? count + 1 : count;
        count = dog ? count + 1 : count;
        count = rodent ? count + 1 : count;
        count = reptile ? count + 1 : count;
        count = bird ? count + 1 : count;
        return count;
    }
    public List<String> getTypes(){
        List<String> strings = new ArrayList<>();
        if(this.cat)
            strings.add("cat");
        if(this.dog)
            strings.add("dog");
        if(this.rodent)
            strings.add("rodent");
        if(this.reptile)
            strings.add("reptile");
        if(this.bird)
            strings.add("bird");
        return strings;
    }
    public List<Object> getObjects(){
        List<Object> strings = new ArrayList<>();
        if(this.cat)
            strings.add("cat");
        if(this.dog)
            strings.add("dog");
        if(this.rodent)
            strings.add("rodent");
        if(this.reptile)
            strings.add("reptile");
        if(this.bird)
            strings.add("bird");
        return strings;
    }
}
