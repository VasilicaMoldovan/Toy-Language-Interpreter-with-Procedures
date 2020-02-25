package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.Exceptions.MyException;
import Model.DataStructures.MyIDictionary;
import Model.Values.Value;
import Model.Types.Type;

public class VarExp implements Exp {
    String id;

    public VarExp(String newId){
        this.id = newId;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer,Value> heap) throws MyException {
        return tbl.lookup(id);
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        return typeEnv.lookup(id);
    }
}
