package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        return null;
    }
}
