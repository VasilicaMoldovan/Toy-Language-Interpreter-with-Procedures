package Model.DataStructures;

import java.util.Collection;
import java.util.Map;

public interface ILatch<K,V> {
    void put(K key, V value);
    V get(K key) ;
    Collection<V> values();
    Collection<K> keys();
    void remove(K fd);
    boolean contains(K key);
    ILatch<K, V> clone();
    Map<K, V> toMap();
}
