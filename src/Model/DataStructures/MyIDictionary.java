package Model.DataStructures;

import Model.Values.Value;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public interface MyIDictionary<T2, T3> {
    boolean isDefined(T2 id);
    T3 getValue(T2 id);
    void update(T2 newT2, T3 newT3);
    T3 lookup(String id);
    void delete(Value id);
    List<T3> getContent();
    MyIDictionary<T2, T3> deepcopy();
    Iterable<Map.Entry<T2, T3>> getAll();
    boolean containsKey(T2 key);
}
