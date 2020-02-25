package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;
import Model.Types.Type;

public class ReadHeapExp implements Exp {
    private Exp expression;

    public ReadHeapExp(Exp newExpression) {
        this.expression = newExpression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value auxValue = expression.eval(tbl, heap);
        if (auxValue instanceof RefValue) {
            RefValue aux = (RefValue) auxValue;
            if (heap.isDefined(aux.getAddr())) {
                return heap.getValue(aux.getAddr());
            } else
                throw new MyException("Key is not in the heap");
        } else
            throw new MyException("Not RefValue instance");
    }

    @Override
    public String toString() {
        return "Read(" + expression.toString() + ")";
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }
}
