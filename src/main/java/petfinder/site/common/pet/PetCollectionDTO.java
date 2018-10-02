package petfinder.site.common.pet;

import java.util.ArrayList;
import java.util.List;

public class PetCollectionDTO {
    private List<PetDto> pets = new ArrayList<>();
    public PetCollectionDTO(){}
    public PetCollectionDTO(List<PetDto> pets) {
        this.pets = pets;
    }

    public List<PetDto> getPets() {
        return pets;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
    }
}
