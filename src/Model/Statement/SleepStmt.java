package Model.Statement;

import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;

public class SleepStmt implements IStmt {
   private int number;

   public SleepStmt(int newNr){
       this.number = newNr;
   }

   @Override
   public IStmt deepCopy(){
       return new SleepStmt(number);
   }

   @Override
   public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
       return null;
   }

   @Override
   public PrgState execute(PrgState state) throws MyException{
       //IStmt statement = state.getStack().pop();
       if (number != 0)
       {
           state.getStack().push(new SleepStmt(number - 1));
       }
       return null;
   }

   @Override
    public String toString(){
       return "Sleep (" + Integer.toString(this.number) + ") ";
   }

}
