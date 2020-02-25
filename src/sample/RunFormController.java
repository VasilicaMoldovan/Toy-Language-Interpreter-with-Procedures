package sample;

import Controller.Controller;
import Model.DataStructures.*;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.IRepository;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunFormController implements Initializable {
    private Controller controller;
    private int currentId;

    @FXML
    private TableView<Map.Entry<Integer, String>> heapTableView;



    @FXML
    private TableView<Map.Entry<String, Value>> procedureTableView;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> procedureTableColumn1;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> procedureTableColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> heapValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, String>> fileTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, Integer> fileIdentifierColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> fileNameColumn;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symbolTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symbolTableValueColumn;

    @FXML
    private ListView<Integer> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private Button executeOneStepButton;

    public void setController(Controller newController){
        this.controller = newController;
        populateProgramStateIdentifiers();
    }

    private List<Integer> getProgramStateIds(List<PrgState> programStates){
        return programStates.stream()
                .map(PrgState::getThreadId).distinct().collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers(){
        List<PrgState> programStates = controller.getRepository().getProgramList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));
        numberOfProgramStatesTextField.setText("" + programStates.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p ->new SimpleStringProperty(p.getValue().getValue().toString()));

        fileIdentifierColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        fileNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + ""));

        symbolTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        //symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));


        programStateListView.setOnMouseClicked(mouseEvent -> { changeProgramState(getCurrentProgramState()); });
        executeOneStepButton.setOnAction(actionEvent -> { executeOneStep(); });

        procedureTableColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        procedureTableColumn1.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getKey()));

    }

    public void executeOneStep(){
        if (controller == null ){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //boolean programStateLeft = getCurrentProgramState().getStack().isEmpty();
        boolean programStateLeft =controller.getRepository().getProgramList().get(currentId - 1).getStack().isEmpty();
            if (programStateLeft) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            controller.executeOneStep();
        changeProgramState(controller.getRepository().getProgramList().get(currentId - 1));
        //changeProgramState(getCurrentProgramState());
        populateProgramStateIdentifiers();
    }

    private void changeProgramState(PrgState currentProgramState){
        if (currentProgramState == null )
            return;
        populateExecutionStack(currentProgramState);
        populateSymbolTable(currentProgramState);
        populateOutput(currentProgramState);
        populateFileTable(currentProgramState);
        populateHeapTable(currentProgramState);
        populateProcedureTable(currentProgramState);
    }

    public void populateProcedureTable(PrgState currentProgramState)
    {
        IProcedure procTbl=currentProgramState.getProcedure();

        List<Map.Entry<String,Value>> symbolTableList=new ArrayList<>();

        for(Map.Entry<String, Pair<ArrayList<String>,IStmt>> entry : procTbl.getAllll())
        {

            symbolTableList.add(Map.entry(entry.getKey(), new StringValue(entry.getValue().getKey().toString() + entry.getValue().getValue().toString() )));
        }
        procedureTableView.setItems(FXCollections.observableList(symbolTableList));
        procedureTableView.refresh();
    }

    private void populateHeapTable(PrgState currentProgramState){
       /* IHeap<Integer,Value> heapTable=currentProgramState.getHeapTable();

        List<Map.Entry<Integer,String>> heapTableList=new ArrayList<>();
        if ( heapTable != null ) {
            for (Map.Entry<Integer, Value> entry : heapTable.getEntrySet()) {
                Map.Entry<Integer, String> aux = new AbstractMap.SimpleEntry<Integer, String>(entry.getKey(), entry.getValue().toString());
                heapTableList.add(aux);
            }
            heapTableView.setItems(FXCollections.observableList(heapTableList));
            heapTableView.refresh();
        }*/

        IHeap<Integer, Value> heapTable = currentProgramState.getHeapTable();
        if (heapTable != null ) {
            Map<Integer, String> heapTableMap = new HashMap<>();
            for (Map.Entry<Integer, Value> entry : heapTable.getDictionary().entrySet()) {
               if (entry.getKey() > 0) {
                   heapTableMap.put(entry.getKey(), entry.getValue().toString());
               }
            }

            List<Map.Entry<Integer, String>> heapTableList = new ArrayList<>(heapTableMap.entrySet());
            heapTableView.setItems(FXCollections.observableList(heapTableList));
            heapTableView.refresh();
        }
    }

    private void populateFileTable(PrgState currentProgramState){
        MyIDictionary<StringValue, BufferedReader> fileTable = currentProgramState.getFileTable();
        if (fileTable != null ) {
            Map<Integer, String> fileTableMap = new HashMap<>();
            int cnt = 1;
            for (Map.Entry<StringValue, BufferedReader> entry : fileTable.getAll()) {
                fileTableMap.put(cnt, entry.getKey().toString());
                cnt++;
            }

            List<Map.Entry<Integer, String>> fileTableList = new ArrayList<>(fileTableMap.entrySet());
            fileTableView.setItems(FXCollections.observableList(fileTableList));
            fileTableView.refresh();
        }
    }

    private void populateOutput(PrgState currentProgramState){
        List<Integer> output = new ArrayList<>();
        MyIList<Value> out = currentProgramState.getOut();
        if ( out != null ) {
            for (int i = 0; i < out.size(); i++) {
                output.add(out.get(i).getIntVal());
            }
            outputListView.setItems(FXCollections.observableList(output));
            outputListView.refresh();
        }
    }


    private void populateSymbolTable(PrgState currentProgramState){
        MyIDictionary<String, Value> symbolTable = currentProgramState.getSymTable();

        if (symbolTable != null) {
            List<Map.Entry<String, Value>> symbolTableList = new ArrayList<>();
            for (Map.Entry<String, Value> entry : symbolTable.getAll()) {
                symbolTableList.add(entry);
            }
            symbolTableView.setItems(FXCollections.observableList(symbolTableList));
            symbolTableView.refresh();
        }
    }

    private void populateExecutionStack(PrgState currentProgramState){
        MyIStack<IStmt> executionStack = currentProgramState.getStack();

        if ( executionStack != null ) {
            List<String> executionStackList = new ArrayList<>();
            for (IStmt statement : executionStack.getAll()) {
                executionStackList.add(statement.toString());
            }

            executionStackListView.setItems(FXCollections.observableList(executionStackList));
            executionStackListView.refresh();
        }
    }

    private PrgState getCurrentProgramState(){
        if(programStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;

        currentId = programStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramStateWithId(currentId);
    }

}
