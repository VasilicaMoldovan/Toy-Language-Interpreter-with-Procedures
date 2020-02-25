package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {
    Exp expression;

    public OpenRFileStmt(Exp newExp){
        this.expression = newExp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        //state.getStack().pop();
        Value result = expression.eval(state.getSymTable(), state.getHeapTable());
        if ( !result.getType().equals(new StringType()))
            throw new MyException("Not String Type");
        else {
            if (state.getFileTable().isDefined((StringValue)result))
                throw new MyException("Key already exists in the file table");
            else {
                try {
                    BufferedReader buff = new BufferedReader(new FileReader((String) result.getVal()));
                    state.getFileTable().update((StringValue)result, buff);
                }
                catch(IOException error) {
                    throw new MyException(error.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new OpenRFileStmt(expression);
    }

    @Override
    public String toString(){
        return expression.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        expression.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }
}
