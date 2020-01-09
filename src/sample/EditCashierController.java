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

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ResourceBundle;

public class EditCashierController implements Initializable {

    Cashier cashier;

    @FXML private TextField nameField;
    @FXML private TextField passField;
    @FXML public PasswordField confirmPass;
    @FXML public PasswordField oldPass;
    @FXML private ComboBox<String> adminCombo;

    ManageUserController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminCombo.setItems(FXCollections.observableArrayList("NO", "YES"));
    }

    public void passData(ManageUserController controller, Cashier cashier){
        this.parentController = controller;
        this.cashier = cashier;

        nameField.setText(cashier.getCashierName());
        passField.setText(cashier.getCashierPass());

        adminCombo.setValue(cashier.getAdminStatus());
    }

    @FXML
    public void updateCashierButtonClicked(ActionEvent event){
        String name = nameField.getText();
        String oldpass = oldPass.getText();
        String pass = passField.getText();
        boolean status = Database.checkPassword(cashier.getCashierID(),oldpass);

        if(passField.getText().equals(confirmPass.getText())) {
            if (status) {
                int admin = 0;
                if (adminCombo.getValue().equals("YES")) {
                    admin = 1;
                }
                Database.updateCashier(cashier.getCashierID(), name, pass, admin);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Password");
                alert.setHeaderText("Please input correct old password");
                alert.setContentText("The old password you input does not match");

                alert.showAndWait();
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password fields do not match");
            alert.setHeaderText("Please input the same password");
            alert.setContentText("Make sure the passwords you input on the new password and confirm password fields are the same");

            alert.showAndWait();
        }

        parentController.refresh();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
