package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProductController implements Initializable {

    Product selectedProduct;

    ManageProductsController parentController;

    @FXML private TextField productPriceField;
    @FXML private TextField productNameField;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void passData(Product selectedProduct, ManageProductsController parentController){
        this.selectedProduct = selectedProduct;
        this.parentController = parentController;

        productNameField.setText(selectedProduct.getProductName());
        productPriceField.setText(String.valueOf(selectedProduct.getProductPrice()));

    }

    public void editProductButtonClicked(ActionEvent event){

        try{
            Database.updateProduct(selectedProduct.getProductID(), productNameField.getText(), Integer.parseInt(productPriceField.getText()));

            parentController.refresh();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (NumberFormatException e){
            errorLabel.setVisible(true);
        }

    }
}
