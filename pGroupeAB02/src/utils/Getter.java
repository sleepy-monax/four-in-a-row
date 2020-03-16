package utils;

@FunctionalInterface
public interface Getter<V>  {
        V call();
}
