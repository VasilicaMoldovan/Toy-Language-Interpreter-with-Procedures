package Model.DataStructures;

import Model.DataStructures.MyIList;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList(){
        list = new ArrayList<T>();
    }

    @Override
    public boolean isEmpty(){
        return list.isEmpty();
    }

    @Override
    public void add(T el){
        list.add(el);
    }

    @Override
    public void clear(){
        list.clear();
    }

    @Override
    public T get(int index){
        return list.get(index);
    }

    @Override
    public int size(){
        return list.size();
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Out:\n");
        int size = this.list.size() - 1;
        for (int index = 0; index <= size; index++)
            result.append(this.list.get(index).toString()).append("\n");

        return result.toString();
    }

}
