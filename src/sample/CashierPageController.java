package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierPageController implements Initializable {

    String username;

    @FXML private Label WelcomeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void passData(String username){
        this.username = username;
        WelcomeLabel.setText("Welcome " + username);
    }
}
