package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;
import Model.Types.Type;

public class ArithExp  implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(char ch, Exp newE1, Exp newE2) {
        this.e1 = newE1;
        this.e2 = newE2;
        if (ch == '+')
            this.op = 1;
        else if (ch == '-')
            this.op = 2;
        else if (ch == '*')
            this.op = 3;
        else if (ch == '/')
            this.op = 4;
    }


    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                int n1, n2;
                n1 = (int) v1.getVal();
                n2 = (int) v2.getVal();
                if (op == 1) return new IntValue(n1 + n2);
                if (op == 2) return new IntValue(n1 - n2);
                if (op == 3) return new IntValue(n1 * n2);
                if (op == 4) {
                    if (n2 == 0)
                        throw new MyException("division by zero");
                    else
                        return new IntValue(n1 / n2);
                } else {
                    throw new MyException("second operand is not an integer");
                }
            } else
                throw new MyException("first operand is not an integer");
        }
        return null;
    }

    @Override
    public String toString() {
        String stringToPrint = e1.toString();
        switch (op) {
            case 1:
                stringToPrint += "+";
                break;
            case 2:
                stringToPrint += "-";
                break;
            case 3:
                stringToPrint += "*";
                break;
            case 4:
                stringToPrint += "/";
                break;
        }

        stringToPrint += e2.toString();
        return stringToPrint;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }
}