package Model.Statement;

import Model.DataStructures.IHeap;
import Model.DataStructures.MyIDictionary;
import Model.DataStructures.MyIStack;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStmt implements IStmt{
    private Exp expression;
    private IStmt statement;

    public WhileStmt(Exp newExp, IStmt newstatement){
        this.expression = newExp;
        this.statement = newstatement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stack = state.getStack();
        MyIDictionary symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeapTable();
        Value aux = expression.eval(symTable, heap);

        if (aux.getType() != new BoolType()){
            if (((BoolValue)aux).getVal().equals(false)){
                return state;
            }
            else{
                stack.push(this);
                stack.push(statement);
            }
        }
        else
            throw new MyException("Not a bool type");
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new WhileStmt(expression, statement);
    }

    @Override
    public String toString(){
        return "while(" + expression.toString() + ")execute(" + statement.toString() + ") ";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        Type typexp=expression.typecheck(typeEnv);
        if (typexp.equals(new IntType()) || typexp.equals(new BoolType())) {
            statement.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of while has not the type bool");
    }
}
