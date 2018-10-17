package petfinder.site.common.user;

public class UserTimesDTO {
    private boolean [] bools;
    public UserTimesDTO(){
        bools = new boolean[7];
    }

    public boolean[] getBools() {
        return bools;
    }

    public void setBools(boolean[] bools) {
        this.bools = bools;
    }
}
