package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {
    private int val;
    public IntValue(int v){
        val = v;
    }

    @Override
    public Object getVal(){
        return val;
    }

    @Override
    public int getIntVal() {
        return val;
    }

    @Override
    public String toString(){

        return "" + val;
    }

    @Override
    public Value getValue(){
        return this;
    }

    @Override
    public Type getType(){
        return new IntType();
    }

    @Override
    public boolean equals(Value object){
        if (object.getVal().equals(val))
            return true;
        return false;
    }
}
