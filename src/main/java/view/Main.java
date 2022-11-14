package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Employees Management");

//        primaryStage.getIcons().add(new Image("file:C:\\Users\\x390\\My Code\\Code Java\\ManagementStaff\\src\\main\\resources\\img\\uchiha.jpg"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
