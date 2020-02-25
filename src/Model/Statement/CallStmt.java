package Model.Statement;

import Model.DataStructures.MyDictionary;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

import java.util.ArrayList;

public class CallStmt implements IStmt {
    private String name;
    private ArrayList<Exp> parameters;

    public CallStmt(String newName, ArrayList<Exp> newParam){
        this.name = newName;
        this.parameters = newParam;
    }

    @Override
    public IStmt deepCopy(){
        return new CallStmt(name, parameters);
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        if ( !state.getProcedure().exists(this.name) ){
            throw new MyException("Procedure does not exist");
        }
        else {
            MyIDictionary<String, Value > symTable = new MyDictionary<>();
            IStmt statement = state.getProcedure().getBody(this.name);


            for(int i=0;i<this.parameters.size();i++)
            {
                symTable.update(state.getProcedure().lookup(name).getKey().get(i),this.parameters.get(i).eval(state.getSymTableStack().top(),state.getHeapTable()));
            }

            state.getSymTableStack().push(symTable);
            state.getStack().push(new ReturnStmt());
            state.getStack().push(statement);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException {
        return null;
    }

    @Override
    public String toString(){
        return "call " + this.name + " " + this.parameters.toString() + "\n";
    }
}
