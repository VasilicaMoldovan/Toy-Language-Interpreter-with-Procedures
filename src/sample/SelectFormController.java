package sample;

import Controller.Controller;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.InMemRepo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectFormController implements Initializable {
    private List<IStmt> programStatements;
    private RunFormController mainController;

    @FXML
    private ListView<String> programListView;

    @FXML
    private Button executeButton;

    public void setMainController(RunFormController newMainController){
        this.mainController = newMainController;
    }

    private void constructProgramStatements(){
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignmentStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignmentStmt("b",new ArithExp('+',new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignmentStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignmentStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        IStmt ex4 = new CompStmt(new CompStmt(new VarDeclStmt("a",new IntType()),
                new OpenRFileStmt(new ValueExp(new StringValue("log1.txt")))),
                new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("log1.txt")), "a"),
                        new CompStmt(new PrintStmt(new VarExp("a")),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("log1.txt")), "a"),
                                                new PrintStmt(new VarExp("a"))), new PrintStmt(new ValueExp(new IntValue(0)))),
                                        new CloseFileStmt(new ValueExp(new StringValue("log1.txt")))))));

        IStmt ex5 = new CompStmt(new CompStmt(new VarDeclStmt("a",new IntType()), new VarDeclStmt("b",new IntType())),
                new CompStmt(new OpenRFileStmt(new ValueExp(new StringValue("log2.txt"))), new CompStmt( new ReadFileStmt
                        (new ValueExp(new StringValue("log2.txt")), "a"), new CompStmt( new ReadFileStmt
                        (new ValueExp(new StringValue("log2.txt")), "b"),   new CompStmt(new PrintStmt(new VarExp("a")),
                        new CompStmt(new PrintStmt(new VarExp("b")),new CloseFileStmt(new ValueExp(new StringValue("log6.txt")))))))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(
                new AssignmentStmt("v",new ValueExp(new IntValue(10))), new WhileStmt(
                new RelationalExp(">", new VarExp("v"),new ValueExp(new IntValue(0))),
                new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignmentStmt( "v",new ArithExp('-',new VarExp("v"),
                                new ValueExp(new IntValue(1))))))));

        IStmt ex7 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new NewStmt("v", new ValueExp(new IntValue(20)))), new VarDeclStmt("a",
                new RefType(new RefType(new IntType())))), new NewStmt("a", new VarExp("v"))),
                new NewStmt("v", new ValueExp(new IntValue(30)))), new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))));

        IStmt ex8 = new CompStmt(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new NewStmt("v", new ValueExp(new IntValue(20)))),
                new CompStmt(new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        new NewStmt("a", new VarExp("v"))),  new CompStmt(new PrintStmt(new VarExp("v")),
                        new PrintStmt(new VarExp("a")))));

        IStmt ex9 = new CompStmt( new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt
                ("v", new RefType(new IntType())), new NewStmt("v", new ValueExp(new IntValue(20)))),
                new VarDeclStmt("a", new RefType(new RefType(new IntType())))), new NewStmt("a",
                new VarExp("v"))), new PrintStmt(new ReadHeapExp(new VarExp("v")))), new PrintStmt(
                new ArithExp('+', new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                        new ValueExp(new IntValue(5)))));

        IStmt ex10 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v",
                new RefType(new IntType())), new NewStmt("v",
                new ValueExp(new IntValue(20)))), new PrintStmt(new ReadHeapExp(new VarExp("v")))),
                new WriteHeapStmt("v", new ValueExp(new IntValue(30)))), new PrintStmt(
                new ArithExp('+', new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)))));

        IStmt ex11 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()), new VarDeclStmt("a", new RefType(new IntType()))),
                new AssignmentStmt("v", new ValueExp(new IntValue(10)))), new NewStmt("a", new ValueExp(new IntValue(30)))),
                new ForkStmt(new CompStmt(new CompStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(22))), new AssignmentStmt("v", new ValueExp(new IntValue(32)))),
                        new PrintStmt(new VarExp("v"))), new PrintStmt(new ReadHeapExp(new VarExp("a")))))), new PrintStmt(new VarExp("v"))),
                new PrintStmt(new ReadHeapExp(new VarExp("a"))));

        IStmt ex12 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()), new AssignmentStmt("v", new ValueExp(new IntValue(10)))),
                new ForkStmt(new CompStmt(new CompStmt(new AssignmentStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))),
                        new AssignmentStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))), new PrintStmt(new VarExp("v"))))),
                new SleepStmt(10)), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))));

        IStmt proc1 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(
                new VarDeclStmt("v", new IntType()), new VarDeclStmt("w", new IntType())),
                new AssignmentStmt("v", new ValueExp(new IntValue(2)))), new AssignmentStmt("w", new ValueExp(new IntValue(5)))),
                new CallStmt("sum", new ArrayList<>(Arrays.asList(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))), new VarExp("w"))))),
                new PrintStmt(new VarExp("v"))), new ForkStmt(new CallStmt("product", new ArrayList<>(Arrays.asList(new VarExp("v"), new VarExp("w"))))));
        programStatements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12, proc1));
    }

    private List<String> getStringRepresentations(){
        return programStatements.stream()
                .map(IStmt::toString).collect(Collectors.toList());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        constructProgramStatements();
        programListView.setItems(FXCollections.observableArrayList(getStringRepresentations()));

        executeButton.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();
            if (index < 0 )
                return;

            PrgState initialProgramState = new PrgState(programStatements.get(index));
            IRepository repository = new InMemRepo("log" + index + ".txt");
            repository.addProgramState(initialProgramState);
            Controller controller = new Controller(repository);

            mainController.setController(controller);
            //mainController.executeOneStep(initialProgramState);
        });
    }
}
