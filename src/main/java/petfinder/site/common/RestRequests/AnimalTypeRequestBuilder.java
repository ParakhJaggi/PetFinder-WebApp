package petfinder.site.common.RestRequests;

public class AnimalTypeRequestBuilder {
    private static AnimalTypeRequestBuilder at = new AnimalTypeRequestBuilder();
    private boolean cat = false, dog = false, reptile = false, bird = false, rodent = false;
    private AnimalTypeRequestBuilder(){}
    public static AnimalTypeRequestBuilder getInstance(){
        return at;
    }
    public AnimalTypeRequest generate(){
        AnimalTypeRequest toReturn = new AnimalTypeRequest(cat, dog, rodent, reptile, bird);
        this.reset();
        return toReturn;
    }
    private void reset(){
        cat = false; dog = false; reptile = false; bird = false; rodent = false;
    }
    public AnimalTypeRequestBuilder setCat(){
        this.cat = true;
        return this;
    }
    public AnimalTypeRequestBuilder setDog(){
        this.dog = true;
        return this;

    }
    public AnimalTypeRequestBuilder setReptile(){
        this.reptile = true;
        return this;
    }
    public AnimalTypeRequestBuilder setRodent(){
        this.rodent = true;
        return this;
    }
    public AnimalTypeRequestBuilder setBird(){
        this.bird = true;
        return this;
    }

}
