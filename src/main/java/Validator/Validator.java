package Validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
