package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;
import Model.Types.Type;

public class RelationalExp implements Exp {
    private Exp exp1;
    private Exp exp2;
    private int op; // 1 - <, 2 - <=, 3 - ==, 4 - !=, 5 - >, 6 - >=

    public RelationalExp(String ch, Exp newE1, Exp newE2) {
        this.exp1 = newE1;
        this.exp2 = newE2;
        if (ch == "<")
            this.op = 1;
        else if (ch == "<=")
            this.op = 2;
        else if (ch == "==")
            this.op = 3;
        else if (ch == "!=")
            this.op = 4;
        else if (ch == ">")
            this.op = 5;
        else if (ch == ">=")
            this.op = 6;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = exp2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                int n1, n2;
                n1 = (int) v1.getVal();
                n2 = (int) v2.getVal();
                if (op == 1) return new BoolValue(n1 < n2);
                if (op == 2) return new BoolValue(n1 <= n2);
                if (op == 3) return new BoolValue(n1 == n2);
                if (op == 4) return new BoolValue(n1 != n2);
                if (op == 5) return new BoolValue(n1 > n2);
                if (op == 6) return new BoolValue(n1 >= n2);
                else {
                    throw new MyException("second operand is not an integer");
                }
            } else
                throw new MyException("first operand is not an integer");
        }
        return null;
    }

    @Override
    public String toString() {
        String stringToPrint = exp1.toString();
        if (op == 1)
            stringToPrint += "<";
        else if (op == 2)
            stringToPrint += "<=";
        else if (op == 3)
            stringToPrint += "==";
        else if (op == 4)
            stringToPrint += "!=";
        else if (op == 5)
            stringToPrint += ">";
        else if (op == 6)
            stringToPrint += ">=";

        stringToPrint += exp2.toString();
        return stringToPrint;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
}
