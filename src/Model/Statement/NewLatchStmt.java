package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.IntValue;

public class NewLatchStmt implements IStmt {
    private String varName;
    private Exp expression;
    private static int newFreeLocation = -1;

    public NewLatchStmt(String newVar, Exp newExp){
        this.varName = newVar;
        this.expression = newExp;
    }

    @Override
    public  PrgState execute(PrgState state) throws MyException{
        try{
            int number = this.expression.eval(state.getSymTable(), state.getHeapTable()).getIntVal();
            synchronized (state.getLatchTable()){
                ++newFreeLocation;
                state.getLatchTable().put(newFreeLocation, number);
                state.getSymTable().update(this.varName, new IntValue(newFreeLocation));
                return null;
            }
        }catch(MyException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public IStmt deepCopy(){
        return new NewLatchStmt(varName, expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typevar = typeEnv.lookup(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("New Latch stmt: right hand side and left hand side have different types ");

    }

    @Override
    public String toString(){
        return "newLatch(" + this.varName + ", " + this.expression.toString() + ")";
    }

}
