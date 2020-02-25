package View;

import Controller.Controller;
import Model.DataStructures.MyDictionary;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Types.Type;

public class RunExample extends Command {
    private Controller ctrl;
    public RunExample(String key, String desc,Controller ctrl){
        super(key, desc);
        this.ctrl=ctrl;
    }
    @Override
    public void execute() {
        try{
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            ctrl.getOriginalProgram().typecheck(typeEnv);
            System.out.println(typeEnv.toString());
            ctrl.allStep(); }
        catch (MyException error) {
            System.out.println(error.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }

}
