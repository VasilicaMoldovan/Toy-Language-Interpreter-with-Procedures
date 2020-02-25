package Model.Statement;

import Model.*;
import Model.DataStructures.MyIDictionary;
import Model.DataStructures.MyIStack;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.Types.Type;
import Model.Values.Value;

public class AssignmentStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignmentStmt(String newId, Exp n) {
        this.id = newId;
        this.exp = n;
    }

    @Override
    public String toString() {

        return id + "=" + exp.toString();
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value val = exp.eval(symTbl, state.getHeapTable());
        if (symTbl.isDefined(id)) {
            Type typId = (symTbl.getValue(id)).getType();
            if (val.getType().equals(typId)) {
                symTbl.update(id, val);
                //state.getSymTable().push(symTbl);
            } else {
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
            }
        } else {
            throw new MyException("the used variable " + id + " was not declared before");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignmentStmt(id, exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}