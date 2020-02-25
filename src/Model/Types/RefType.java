package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner=inner;
    }

    public RefType(){
        this.inner = null;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof RefType) {
            RefType aux = (RefType) another;
            return inner.equals(aux.getInner());
        }
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" +inner.toString()+")";
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0,inner);
    }
}
