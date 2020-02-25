package Model;

import Model.DataStructures.*;
import Model.Exceptions.MyException;
import Model.Expressions.ArithExp;
import Model.Expressions.VarExp;
import Model.Statement.*;
import Model.Types.IntType;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIStack<MyIDictionary<String, Value>> symTableStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram; //optional field, but good to have
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heapTable;
    private int id = 1;
    private static int lastAssign = 1;
    private ILatch latchTable;
    private IProcedure procedureTable;

    public static int getId(){
        lastAssign++;
        return lastAssign;
    }

    public int getThreadId(){
        return id;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl,
                    MyIList<Value> ot, IStmt prg, MyIDictionary<StringValue, BufferedReader> fileTbl, IHeap heap, int newId,
                    ILatch newLatchTable, MyIStack<MyIDictionary<String, Value>> newSymTableStack,
                    IProcedure newProcedureTable){

        exeStack = stk;
        symTable = symtbl;
        symTableStack = newSymTableStack;
        out = ot;
        originalProgram = prg.deepCopy();//recreate the entire original prg
        fileTable = fileTbl;
        heapTable = heap;
        id = newId;
        exeStack.push(prg);
        latchTable = newLatchTable;
        procedureTable = newProcedureTable;
    }

    public PrgState(IStmt initialProgram){
        exeStack = new MyStack<>();
        //symTable = new MyStack<>();
        symTable = new MyDictionary<>();
        symTableStack = new MyStack<>();
        symTableStack.push(symTable);
        out = new MyList<Value>();
        this.originalProgram = initialProgram;
        fileTable = new MyDictionary<>();
        heapTable = new Heap<Integer, Value>();
        procedureTable = new Procedure();

        exeStack.push(originalProgram);

        this.procedureTable.update("product", new Pair<>(new ArrayList<String>(Arrays.asList("a", "b")), new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()), new AssignmentStmt("v",
                new ArithExp('*', new VarExp("a"), new VarExp("b")))), new PrintStmt(new VarExp("v")))));
        this.procedureTable.update("sum", new Pair<>(new ArrayList<String>(Arrays.asList("a", "b")), new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()), new AssignmentStmt("v",
                new ArithExp('+', new VarExp("a"), new VarExp("b")))), new PrintStmt(new VarExp("v")))));

    }

    /*public int getId(){
        return id;
    }*/

    public ILatch getLatchTable(){
        return this.latchTable;
    }

    public void setId(int newId){
        id = newId;
    }

    public PrgState oneStep() throws MyException{
        if (exeStack.isEmpty())
            throw new MyException("Program state stack is empty");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted(){
        if (this.exeStack.isEmpty())
            return false;
        return true;
    }

    public void addOutput(Value element){
        this.out.add(element);
    }

    @Override
    public String toString(){
        return "Thread id: " + id + " " + exeStack.toString() + "Symbol Table:\n" + symTableStack.top().toString() + out.toString() +
                "File table:\n" + fileTable.toString() + "\n" + "Heap:\n" + heapTable.toString() + "\n";
    }
    public MyIStack<IStmt> getStack() {
        return exeStack;
    }

    /*public MyIDictionary<String, Value> getSymTable(){
        return symTable;
    }

     */
    public MyIDictionary<String, Value> getSymTable(){
        //if ( !symTableStack.isEmpty())
            return symTableStack.top();
        //return symTable;
    }

    public MyIStack<MyIDictionary<String, Value>> getSymTableStack() {
        return symTableStack;
    }

    public IHeap getHeapTable(){
        return heapTable;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public IStmt getOriginalProgram(){
        return originalProgram;
    }

    public void setExeStack(MyIStack<IStmt> newExeStack) {
        exeStack = newExeStack;
    }

    public void setSymTable(MyIDictionary<String, Value> newSymTable){
        symTable = newSymTable;
        symTableStack.push(symTable);
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public void setOriginalProgram(IStmt newProgram){
        originalProgram = newProgram;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTbl){
        this.fileTable = fileTbl;
    }

    public IProcedure getProcedure(){
        return procedureTable;
    }

    public void setProcedureTable(IProcedure proc){
        this.procedureTable = proc;
    }

    //public void addFile(Value, Bu)
}
