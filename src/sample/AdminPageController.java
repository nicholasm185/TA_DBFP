package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {

    String username;

    @FXML private Label WelcomeAdmin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void passData(String username){
        this.username = username;
        WelcomeAdmin.setText("Welcome admin " + username);
    }
}
