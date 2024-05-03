package Mapper;

public interface Mapper<T,F> {
    T mapFrom(F f);
}
