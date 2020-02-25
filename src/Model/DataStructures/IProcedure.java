package Model.DataStructures;

import Model.Statement.IStmt;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public interface IProcedure  {
    void update(String newT1, Pair<ArrayList<String>, IStmt> newt2);
    ArrayList<String> getParameters(String name);
    IStmt getBody(String name);
    boolean exists(String name);
    Pair<ArrayList<String>, IStmt> lookup(String name);
    Iterable<Map.Entry<String, Pair<ArrayList<String>, IStmt>>> getAllll();
}
