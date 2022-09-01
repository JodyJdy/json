package example;

public class Human <T>{
    T t;

    @Override
    public String toString() {
        return t.toString();
    }
}
