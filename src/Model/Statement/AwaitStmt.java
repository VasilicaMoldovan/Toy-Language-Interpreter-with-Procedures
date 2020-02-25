package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;

public class AwaitStmt implements IStmt {
    private String varName;

    public AwaitStmt(String newVar){
        this.varName = newVar;
    }

    @Override
    public IStmt deepCopy(){
        return new AwaitStmt(varName);
    }

    @Override
    public String toString(){
        return "Await( " + varName  + " )";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        if (!state.getSymTable().containsKey(varName))
            return null;
        int foundIndex = state.getSymTable().getValue(varName).getIntVal();
        if(!state.getLatchTable().contains(foundIndex))
            return null;
        else
            state.getStack().push(this);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException {
        return null;
    }
}
