package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;

public class CountDownStmt implements IStmt {
    private String var;

    public CountDownStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) {
        if (state.getSymTable().getValue(this.var) == null);
        int index = state.getSymTable().getValue(this.var).getIntVal();
        synchronized (state.getLatchTable()) {
            if (state.getLatchTable().get(index) == null)
                return null; // do nothing
            int count = (Integer)state.getLatchTable().get(index);
            if (count > 0) {
                state.getLatchTable().put(index, count - 1);
                state.getOut().add(new IntValue(state.getThreadId()));
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new CountDownStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException {
        return null;
    }

    @Override
    public String toString() {
        return "countDown(" + this.var + ")";
    }
}
