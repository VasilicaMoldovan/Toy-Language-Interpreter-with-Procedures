package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;


public class RefValue implements Value {
    int address;
    Type locationType;

    public int getAddr() {
        return address;
    }

    public RefValue(int heapAddress, Type type){
        this.address = heapAddress;
        this.locationType = type;
    }

    public Type getLocationType(){
        return this.locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value getValue(){
        return new RefValue(address, locationType);
    }

    @Override
    public boolean equals(Value another){
        if ( another instanceof RefValue){
            RefValue aux = (RefValue)another;
            if ( aux.getVal().equals(locationType) && aux.getAddr() == address)
                return true;
            return false;
        }
        return false;
    }

    public void setAddress(int newAddress){
        this.address = newAddress;
    }

    @Override
    public Object getVal(){
        return this.locationType;
    }

    @Override
    public String toString(){
        return "( " + Integer.toString(this.address) + ", " + locationType.toString() + " )";
    }

    @Override
    public int getIntVal(){
        return -1;
    }
}
