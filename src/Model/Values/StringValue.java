package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    private String string;

    public StringValue(String newString){
        this.string = newString;
    }

    @Override
    public Value getValue(){
        return new StringValue(string);
    }

    @Override
    public Type getType(){
        return new StringType();
    }

    @Override
    public Object getVal(){
        return string;
    }

    @Override
    public int hashCode(){
        return string.hashCode();
    }

    @Override
    public boolean equals(Value object){
        if (object instanceof StringValue ) {
            StringValue x = (StringValue)object;
            return x.getVal().equals(string);
        }

        //if (object.getVal().equals(string))
        //    return true;
        return false;
    }

    @Override
    public String toString(){
        return string;
    }

    @Override
    public int getIntVal(){
        return -1;
    }
}
