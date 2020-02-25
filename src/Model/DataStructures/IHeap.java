package Model.DataStructures;

import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IHeap<T1, T2> {
    boolean isDefined(T1 id);
    T2 getValue(T1 id);
    void update(T1 newT1, T2 newT2);
    T2 lookup(String id);
    void remove(T2 id);
    T1 getFirstFree();
    void update(T2 newT2);
    Set<Map.Entry<T1, T2>> getEntrySet();
    void setContent(Map<Integer, Value> content);
    HashMap<T1, T2> getDictionary();
}
