package View;
import Controller.Controller;
import Model.*;
import Model.DataStructures.MyList;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.Statement.*;
import Model.Types.BoolType;
import Model.Types.RefType;
import Model.Values.BoolValue;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.RefValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.InMemRepo;

import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
    private Controller controller;
    private MyList<IStmt> statements;

    public Interpreter(Controller newController, MyList<IStmt> newStatements){
        this.controller = newController;
        this.statements = newStatements;
    }

    private void printMenu(){
        System.out.println("\n-------Toy Language Interpreter-------");
        for(int i = 0; i < statements.size(); i++){
            System.out.println(String.format("%d: %s", i, statements.get(i).toString()));
        }
        System.out.println("-1: Exit.");
    }

    private int getInteger(Scanner scanner) throws MyException{
        try{
            return Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e){
            throw new MyException("Invalid integer");
        }
    }

    private void infiniteLoop() throws MyException{
        Scanner scanner = new Scanner(System.in);

        while (true){
            printMenu();
            System.out.println("Choose one option:");
            int option = -1;
            try{
                option = getInteger(scanner);
                if (option == -1) break;
                if (option < statements.size()){
                    try{
                        controller.allStep();
                    }
                    catch (MyException e){
                        e.printStackTrace();
                    }
                }
            }
            catch(MyException e){
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }


    public void runInterpreter() throws MyException{
        infiniteLoop();
    }
}
