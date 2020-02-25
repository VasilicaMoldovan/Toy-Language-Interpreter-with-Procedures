package Model.DataStructures;

import Model.Values.Value;

import java.util.*;

public class MyDictionary<T2, T3> implements MyIDictionary<T2, T3> {
    private HashMap<T2, T3> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    public MyDictionary(HashMap<T2, T3> newDict) {

        dictionary = newDict;
    }

    @Override
    public boolean isDefined(T2 id){
        for(T2 key : dictionary.keySet()){
            if (key.toString().equals(id.toString()))
                return true;
        }
        //if (dictionary.get(id) == null)
        //   return false;
        return false;
    }

    @Override
    public T3 getValue(T2 id){
        for(T2 key : dictionary.keySet()){
            if (key.toString().equals(id.toString()))
                return dictionary.get(key);
        }
        return null;
    }

    @Override
    public void update(T2 newT2, T3 newT3){
        dictionary.put(newT2, newT3);
    }

    @Override
    public T3 lookup(String key){
        return dictionary.get(key);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for ( T2 key : dictionary.keySet() ) {
            result.append(key.toString() + " --> " + this.dictionary.get(key).toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public void delete(Value id)
    {
        dictionary.remove(id);
    }

    @Override
    public List<T3> getContent() {
        return new LinkedList<>(this.dictionary.values());
    }

    @Override
    public  MyIDictionary<T2, T3> deepcopy(){
        HashMap<T2, T3> copy = new HashMap<>();
        for (Map.Entry<T2, T3> element : dictionary.entrySet()){
            copy.put(element.getKey(), element.getValue());
        }
        return new MyDictionary<T2, T3>(copy);
    }

    @Override
    public  Iterable<Map.Entry<T2, T3>> getAll(){
        return dictionary.entrySet();
    }

    @Override
    public boolean containsKey(T2 key){
        return dictionary.containsKey(key);
    }
}
