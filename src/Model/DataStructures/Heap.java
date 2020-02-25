package Model.DataStructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap<I extends Number, Value> implements IHeap<Integer, Value> {
    private Integer firstFreeAddress;
    private HashMap<Integer, Value> heapTable;

    public Heap(){
        this.firstFreeAddress = 1;
        this.heapTable = new HashMap<>();
        this.heapTable.put(0, null);
    }

    @Override
    public boolean isDefined(Integer id){
        return this.heapTable.containsKey(id);
    }

    @Override
    public Value getValue(Integer id){
        return this.heapTable.get(id);
    }

    @Override
    public void update(Integer address, Value content){
        this.heapTable.put(address, content);
    }

    @Override
    public Value lookup(String id){
        return this.heapTable.get(id);
    }

    @Override
    public void remove(Value id){
        this.heapTable.remove(id);
    }

    @Override
    public Integer getFirstFree(){
        return this.firstFreeAddress;
    }

    @Override
    public void update(Value newVal){
        this.heapTable.put(this.firstFreeAddress, newVal);
        this.firstFreeAddress++;
    }

    @Override
    public Set<Map.Entry<Integer, Value>> getEntrySet() {
        return this.heapTable.entrySet();
    }

    @Override
    public void setContent(Map<Integer, Model.Values.Value> content){
        this.heapTable = (HashMap)content;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for ( Integer key = 1; key < this.firstFreeAddress; key++) {
            result.append(key.toString()).append(" --> ").append(this.heapTable.get(key).toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public HashMap<Integer, Value> getDictionary(){
        return heapTable;
    }
}

