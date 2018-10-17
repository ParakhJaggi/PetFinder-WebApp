package petfinder.site.common.user;

import java.util.Arrays;
import java.util.Objects;

public class BookingDTO {
    private String principal;
    private boolean [] days;

    public BookingDTO(String principal, boolean[] days) {
        this.principal = principal;
        this.days = days;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDTO that = (BookingDTO) o;
        return Objects.equals(principal, that.principal) &&
                Arrays.equals(days, that.days);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(principal);
        result = 31 * result + Arrays.hashCode(days);
        return result;
    }
}
