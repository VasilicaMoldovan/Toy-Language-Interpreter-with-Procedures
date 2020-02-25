package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type {
    public boolean equals(Object another){
        if (another instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public String toString()
    {
        return "int";

    }

    @Override
    public Value defaultValue(){
        return new IntValue(0);
    }
}
