package Model.DataStructures;

import java.util.List;

public interface MyIList<T> {
    boolean isEmpty();
    void add(T el);
    void clear();
    T get(int index);
    int size();
}
