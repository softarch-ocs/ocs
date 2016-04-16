package external.dto;

import java.io.Serializable;
import java.util.List;

public class EmployeeToEvaluateDto implements Serializable {
    private String document;
    private List<String> features;
    
    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }
}
