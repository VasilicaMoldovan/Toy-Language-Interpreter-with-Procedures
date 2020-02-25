package Model.Statement;

import Model.*;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    @Override
    public String toString(){
        return type.toString() + " " + name;
    }

    public VarDeclStmt(String newName, Type newType){
        this.name = newName;
        this.type = newType;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new MyException("Variable is already declared");
        symTable.update(name, type.defaultValue());

        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new VarDeclStmt(name, type);
    }

    @Override
    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        typeEnv.update(name,type);
        return typeEnv;
    }
}
