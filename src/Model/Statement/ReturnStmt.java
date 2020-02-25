package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;

public class ReturnStmt implements IStmt {

    public ReturnStmt(){

    }

    @Override
    public IStmt deepCopy(){
        return new ReturnStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException {
        return null;
    }

    @Override
    public String toString(){
        return "Return";
    }

    @Override
    public PrgState execute(PrgState state){
        state.getSymTableStack().pop();
        return null;
    }

}
