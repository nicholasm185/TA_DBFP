package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CheckOutController implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField passField;
    @FXML private ComboBox<String> storeCombo;
    @FXML private ComboBox<String> paymentTypeCombo;

    NewTransactionController parentController;

    private int billID;
    private String transactionTime;
    private int cashierID;
    private ArrayList<String> stores = new ArrayList<>();
    private ArrayList<String> paymentTypeList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshStoreComboBox();
        storeCombo.setValue("Select Store");
        refreshPaymentTypeComboBox();
        paymentTypeCombo.setValue("Select Payment");

    }


    public void passData(NewTransactionController controller, int billID, int cashierID){
        this.parentController = controller;
        this.billID = billID;
        this.cashierID = cashierID;
    }

    @FXML
    private void refreshStoreComboBox(){
        stores.clear();
        stores = Database.getAllStores();
        storeCombo.setItems(FXCollections.observableArrayList(stores));

    }
    @FXML
    private void refreshPaymentTypeComboBox(){
        paymentTypeList.clear();
        paymentTypeList = Database.getAllPaymentTypes();
        paymentTypeCombo.setItems(FXCollections.observableArrayList(paymentTypeList));

    }

    public void checkOutButtonClicked(ActionEvent event){
        String storeName = storeCombo.getValue();
        String paymentTYpe = paymentTypeCombo.getValue();

        if(storeName.equals("Select Store") || paymentTYpe.equals("Select Payment")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed to check-out");
            alert.setHeaderText("Payment Type/Store not selected");
            alert.setContentText("Make sure both the payment type and store is selected");

            alert.showAndWait();
        }else{

            System.out.println("storeName "+storeName);
            int storeID =  Database.getStoreID(storeName);
            System.out.println("storeID"+storeID);
            int paymentTypeID = Database.getPaymentID(paymentTYpe);
            System.out.println("paymentTypeID"+paymentTypeID);
            Database.updateBill(billID, cashierID, storeID, paymentTypeID);
//            System.out.println("refresh1");
//        parentController.refresh();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }


    }

}
