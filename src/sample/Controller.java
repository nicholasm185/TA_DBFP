package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TextField usrnmField;
    @FXML private PasswordField passField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void testbuttonClicked(){
        Database.newItemTransaction(3,3, 1);
        Database.testconnect();
    }
}
