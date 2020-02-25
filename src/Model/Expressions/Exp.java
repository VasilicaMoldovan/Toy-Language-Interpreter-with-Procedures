package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.Exceptions.MyException;
import Model.DataStructures.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl,  IHeap<Integer,Value> heap) throws MyException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
