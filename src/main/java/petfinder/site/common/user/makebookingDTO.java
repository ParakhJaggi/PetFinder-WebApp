package petfinder.site.common.user;

public class makebookingDTO {
    private UserTimesDTO times;
    private String userName;

    public UserTimesDTO getTimes() {
        return times;
    }

    public void setTimes(UserTimesDTO times) {
        this.times = times;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
