package Model.Statement;

import Model.DataStructures.IProcedure;
import Model.DataStructures.MyDictionary;
import Model.DataStructures.MyIDictionary;
import Model.DataStructures.Procedure;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.StringValue;
import javafx.util.Pair;

import java.util.ArrayList;

public class NewProcedure implements IStmt {
    private String name;
    private ArrayList<String> parameters;
    private IStmt body;

    public NewProcedure(String newName, ArrayList<String> newParam, IStmt newBody) {
        this.name = newName;
        this.parameters = newParam;
        this.body = newBody;
    }

    @Override
    public IStmt deepCopy() {
        return new NewProcedure(name, parameters, body);
    }

    @Override
    public String toString() {
        return "Procedure " + this.name + " (" + this.parameters.toString() + ") {\n" + body.toString() + "\n}";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException {
        return null;
    }

    @Override
    public PrgState execute(PrgState state){
        MyDictionary<String, Pair<ArrayList<String>, IStmt>> dict = new MyDictionary<>();
        dict.update(this.name, new Pair(this.parameters, this.body));
        Procedure proc = new Procedure(dict);
        state.getProcedure().update(this.name, new Pair(this.parameters, this.body));
        return null;
    }
}