package Model.DataStructures;

import Model.Statement.IStmt;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class Procedure implements IProcedure {
    private MyDictionary<String, Pair<ArrayList<String>, IStmt>> parameters;

    public Procedure(MyDictionary<String, Pair<ArrayList<String>, IStmt>> newParameters){
        this.parameters = newParameters;
    }

    public Procedure(){
        this.parameters = new MyDictionary<>();
    }

    @Override
   public void update(String newT1, Pair<ArrayList<String>, IStmt> newt2){
        this.parameters.update(newT1, newt2);
    }

    @Override
    public ArrayList<String> getParameters(String name){
        return this.parameters.lookup(name).getKey();
    }

    @Override
    public IStmt getBody(String name){
        return this.parameters.lookup(name).getValue();
    }

    @Override
    public boolean exists(String name){
        return this.parameters.containsKey(name);
    }

    @Override
    public Pair<ArrayList<String>, IStmt> lookup(String key){
        return parameters.lookup(key);
    }

    @Override
    public Iterable<Map.Entry<String, Pair<ArrayList<String>, IStmt>>> getAllll()
    {
        return parameters.getAll();
    }
}
