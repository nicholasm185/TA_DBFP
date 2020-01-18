package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCashierController implements Initializable {

    @FXML public PasswordField confirmPass;
    @FXML private TextField nameField;
    @FXML private PasswordField passField;
    @FXML private ComboBox<String> adminCombo;

    ManageUserController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminCombo.setItems(FXCollections.observableArrayList("NO", "YES"));
        adminCombo.setValue("NO");
    }

    public void passData(ManageUserController controller){
        this.parentController = controller;
    }

    public void addNewCashierButtonClicked(ActionEvent event){
        String name = nameField.getText();
        String pass = passField.getText();
        int admin = 0;
        if(adminCombo.getValue().equals("YES")){
            admin = 1;
        }

        if(passField.getText().equals(confirmPass.getText())){
            Database.addCashier(name, pass, admin);
            parentController.refresh();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password fields do not match");
            alert.setHeaderText("Please input the same password");
            alert.setContentText("Make sure the passwords you input on the new password and confirm password fields are the same");

            alert.showAndWait();
        }


    }

}
