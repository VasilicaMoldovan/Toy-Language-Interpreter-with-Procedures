package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStmt implements IStmt {
    Exp expression;

    public CloseFileStmt(Exp newExp){
        expression = newExp;
    }

    @Override
    public IStmt deepCopy(){
        return new CloseFileStmt(expression);
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        //state.getStack().pop();

        Value result = expression.eval(state.getSymTable(), state.getHeapTable());
        if ( !(result instanceof StringValue))
            throw new MyException("Not string value");
        else{
            if (!state.getFileTable().isDefined((StringValue)result))
                throw new MyException("Invalid entry");
            else {
                BufferedReader buff = state.getFileTable().getValue((StringValue) result);
                try {
                    buff.close();
                    state.getFileTable().delete((StringValue)result);
                }
                catch(IOException error){
                    throw new MyException("Error closing the file");
                }
            }

        }


        return null;
    }

    @Override
    public String toString(){
        return " close " + expression.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        expression.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }
}
