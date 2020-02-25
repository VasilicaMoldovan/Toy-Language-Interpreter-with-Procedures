package Model.Values;

import Model.Types.Type;

public interface Value {
    Type getType();
    Value getValue();
    boolean equals(Value object);
    Object getVal();
    int getIntVal();
}
