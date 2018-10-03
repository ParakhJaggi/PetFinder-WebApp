package petfinder.site.common.RestRequests;

import java.util.List;

/**
 * @author Laird
 */
public class SingleFieldRequest {
    private String field;
    private List<Object> desiredValues;

    public SingleFieldRequest(){}
    public SingleFieldRequest(String field, List<Object> desiredValues) {
        this.field = field;
        this.desiredValues = desiredValues;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<Object> getDesiredValues() {
        return desiredValues;
    }

    public void setDesiredValues(List<Object> desiredValues) {
        this.desiredValues = desiredValues;
    }
}
