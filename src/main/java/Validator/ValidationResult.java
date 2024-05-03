package Validator;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors = new ArrayList<>();
    public void add(String error){
        this.errors.add(error);
    }
    public boolean isValid(){
        return errors.isEmpty();
    }

}
