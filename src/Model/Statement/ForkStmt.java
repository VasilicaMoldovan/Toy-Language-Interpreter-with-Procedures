package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.DataStructures.MyStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt newStatement){
        statement = newStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        return new PrgState(new MyStack<>(), state.getSymTable().deepcopy(),
                state.getOut(), this.statement, state.getFileTable(), state.getHeapTable(), state.getId(), state.getLatchTable(),
                state.getSymTableStack(), state.getProcedure());
    }

    @Override
    public IStmt deepCopy(){
        return new ForkStmt(statement);
    }

    @Override
    public String toString(){
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        return statement.typecheck(typeEnv.deepcopy());
    }
}
