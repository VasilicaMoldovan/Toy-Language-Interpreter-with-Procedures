package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue(boolean newValue)
    {
        this.val = newValue;
    }

    @Override
    public Value getValue(){
        return new BoolValue(val);
    }

    @Override
    public Object getVal(){
        return val;
    }

    @Override
    public Type getType(){

        return new BoolType();
    }

    @Override
    public String toString(){
        if ( val == true )
            return "true";
        else
            return "false";
    }

    @Override
    public boolean equals(Value object){
        if (object.getVal().equals(val))
            return true;
        return false;
    }

    @Override
    public int getIntVal(){
        return -1;
    }
}
