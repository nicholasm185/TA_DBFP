package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    private ArrayList<String> stores = new ArrayList<>();
    @FXML private TextField productNameField;
    @FXML private TextField productPriceField;
    @FXML private Label errorLabel;

    ManageProductsController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init");
    }


    public void passData(ManageProductsController controller){
        this.parentController = controller;
    }

    public void addProductButtonClicked(ActionEvent event){
        try{
            String productName = productNameField.getText();
            int productPrice =Integer.parseInt(productPriceField.getText());
            Database.addProduct(productName, productPrice);

            parentController.refresh();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }catch (Exception e) {
            System.out.println("Please Make sure input is correct");
            errorLabel.setVisible(true);
        }
    }

}
