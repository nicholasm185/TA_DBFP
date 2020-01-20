package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
//        root.getStylesheets().add("sample/style.css");
        primaryStage.setTitle("Login Page");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 489, 311));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.out.println("closing");
                Database.close();
                Platform.exit();
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
//        Database.testconnect();
        Database.connect();
        launch(args);
    }
}
