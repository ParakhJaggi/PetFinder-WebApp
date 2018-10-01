package petfinder.site.common;

public class AnimalTypeRequestBuilder {
    private static AnimalTypeRequestBuilder at = new AnimalTypeRequestBuilder();
    private boolean cat = false, dog = false, reptile = false, bird = false, rodent = false;
    private AnimalTypeRequestBuilder(){}
    public static AnimalTypeRequestBuilder getInstance(){
        return at;
    }
    public AnimalTypeRequest generate(){
        this.reset();
        return new AnimalTypeRequest(cat, dog, rodent, reptile, bird);
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