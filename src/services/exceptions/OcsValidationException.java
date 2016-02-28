package services.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OcsValidationException extends OcsServiceException {
    public static class ValidationItem {
        private String message;
        
        public ValidationItem(String message) {
            if (message == null) {
                throw new IllegalArgumentException("message can't be null");
            }
            
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
    }
    
    private List<ValidationItem> items;
    
    public OcsValidationException(List<ValidationItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("items can't be null");
        }
        
        // Copy the list to prevent modification through the original list
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }
    
    public OcsValidationException(ValidationItem item) {
        if (item == null) {
            throw new IllegalArgumentException("item can't be null");
        }
        
        List<ValidationItem> tempItems = new ArrayList<>();
        tempItems.add(item);
        
        items = Collections.unmodifiableList(tempItems);
    }
    
    public List<ValidationItem> getValidationItems() {
        return items;
    }
}
