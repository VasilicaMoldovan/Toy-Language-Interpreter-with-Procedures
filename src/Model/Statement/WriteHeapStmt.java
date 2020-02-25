package Model.Statement;

import Model.DataStructures.IHeap;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class WriteHeapStmt implements IStmt {
    private String varName;
    private Exp expression;

    public WriteHeapStmt(String newName, Exp newExp){
        this.varName = newName;
        this.expression = newExp;
    }


/*    @Override
    public PrgState execute(PrgState state) throws MyException{
        if ( state.getSymTable().isDefined(varName) ){
            Value aux = state.getSymTable().getValue(varName);
            if ( aux.getType() instanceof RefType){
                RefValue aux2 = (RefValue)aux;
                if (state.getHeapTable().isDefined(aux2.getAddr())){
                    Value expResult = expression.eval(state.getSymTable(), state.getHeapTable());
                    if ( expResult.getType().equals(aux2.getType())){
                        state.getHeapTable().update(((RefValue) aux).getAddr(), aux2);
                    }
                    else
                        throw new MyException("Different types");
                }
                else
                    throw new MyException("Undefined key");
            }
            else
                throw new MyException("Invalid type");
        }
        else
            throw new MyException("Undefined key");
        return null;
    }*/

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        IHeap<Integer, Value> heapTable = state.getHeapTable();

        if (!symbolTable.containsKey(varName))
            throw new MyException("failed");

        Value oldVal = symbolTable.getValue(varName);

        if (!(oldVal.getType() instanceof RefType)) {
            throw new MyException("Failed to write to heap. Type is not reference!");
        }

        if (!heapTable.isDefined(((RefValue) oldVal).getAddr()))
            throw new MyException("Failed to write to heap. Address not defined!");

        Value newVal =expression.eval(symbolTable,heapTable);

        if(!newVal.getType().equals(((RefValue)oldVal).getVal()))
        {
            throw new MyException("Failed");
        }

        heapTable.update(((RefValue)oldVal).getAddr(),newVal);
        return state;


    }

    @Override
    public IStmt deepCopy(){
        return new WriteHeapStmt(varName, expression);
    }

    @Override
    public String toString(){
        return "WriteHeap(" + varName + "->" + expression.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        Type typevar = typeEnv.lookup(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }
}
