package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("runForm.fxml"));
        Parent mainWindow = mainLoader.load();

        RunFormController mainController = mainLoader.getController();

        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(mainWindow, 1100, 600));
        primaryStage.show();

        FXMLLoader secondLoader = new FXMLLoader();
        secondLoader.setLocation(getClass().getResource("selectForm.fxml"));
        Parent secondWindow = secondLoader.load();

        SelectFormController selectController = secondLoader.getController();
        selectController.setMainController(mainController);

        Stage secondStage = new Stage();
        secondStage.setTitle("Select Window");
        secondStage.setScene(new Scene(secondWindow, 500, 550));
        secondStage.show();
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
