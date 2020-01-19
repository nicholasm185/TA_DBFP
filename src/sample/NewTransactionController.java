package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTransactionController implements Initializable {

    String username;
    String role;
    private int currentBillNumber;
    private int total;
    @FXML private Label totalLabel;

    @FXML private TableView<Product> inventoryTable;
    @FXML private TableColumn<Product, String> productIDInvenCol;
    @FXML private TableColumn<Product, String> productNameInvenCol;
    @FXML private TableColumn<Product, String> priceInvenCol;

//    @FXML private ComboBox<Integer> qtyCombo;

    @FXML private TableView<ItemTransaction> cartTable;
    @FXML private TableColumn<ItemTransaction, String> productIDCartCol;
    @FXML private TableColumn<ItemTransaction, String> productNameCartCol;
    @FXML private TableColumn<ItemTransaction, String> priceCartCol;
    @FXML private TableColumn<ItemTransaction, String> qtyCartCol;
    @FXML private TableColumn<ItemTransaction, String> subtotalCartCol;

    private ObservableList<Product> inventoryList = FXCollections.observableArrayList();
    private ObservableList<ItemTransaction> cartList = FXCollections.observableArrayList();
    @FXML private TextField qtyField;

//    private Date date = new Date(); // this object controains the current date value
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Database.checkLastBill(currentBillNumber) == 0){
        System.out.println("AddInitialBill");
//        Database.deleteBill(currentBillNumber);
        Database.addInitBill();

        }
        currentBillNumber = Database.getBillNumber();
        System.out.println("currBillNum"+currentBillNumber);
        productIDInvenCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameInvenCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        priceInvenCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

//        -----------------------------------------------------------------------------------
//        qtyCombo.setItems(FXCollections.observableArrayList(1,2,3,4,5));
//        qtyCombo.setValue(1);

//        -----------------------------------------------------------------------------------

//        productIDCartCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
////        productNameCartCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
////        priceCartCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
//        qtyCartCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
//        subtotalCartCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        refresh();
    }

    @FXML
    public void passData(String username, String role){
        this.username = username;
        this.role = role;
        System.out.println("username "+username);
        System.out.println("role "+role);
    }

    @FXML
    public void refresh(){
        System.out.println("refreshparent");


        inventoryList.clear();
        inventoryList.addAll(Database.getAllProducts());
        inventoryTable.setItems(inventoryList);

        cartList.clear();
        cartList.addAll(Database.getAllItemTransactionCurrBill(currentBillNumber));
        cartTable.setItems(cartList);

//        this.total = Database.sumItemTransaction(currentBillNumber);
//        System.out.println("TOTAL"+this.total);
//        totalLabel.setText("TOTAL " + this.total);

    }
    //
    @FXML
    public void addItemButtonClicked(){
        System.out.println("addItemButtonClicked");
        try {
            Product selected = inventoryTable.getSelectionModel().getSelectedItem();


            int billID = currentBillNumber;
            int productID = selected.getProductID();
            int productPrice = selected.getProductPrice();
            int qty = 1;

            String qtytf = qtyField.getText();
            System.out.println("qtytf"+qtytf);
            if (!qtytf.equals("")){
                qty = Integer.parseInt(qtytf);
            }
            int subtotal = productPrice * qty;

            System.out.println("billID"+billID);
            System.out.println("ID"+productID);
            System.out.println("Price"+productPrice);
            System.out.println("subtotal"+subtotal);

            Database.addItemTransaction(billID, productID, qty, subtotal);
            refresh();


        } catch (NullPointerException e){
            System.out.println("no selection");
        }
    }

    @FXML
    public void deleteItemButtonClicked(){

//        try{
//            ItemTransaction selected = cartTable.getSelectionModel().getSelectedItem();
//            Database.deleteItemTransaction(selected.getItemID());
//            refresh();
//
//        } catch (NullPointerException e){
//            System.out.println("no selection");
//        }
    }

    @FXML
    public void checkOutButtonClicked(){
        System.out.println("checkOutButtonClicked");
//        int billID = currentBillNumber;
////        String transactionTime =dateFormat.format(date);
//        int cashierID = Database.getCashierID(username);
//
//
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("CheckOutPage.fxml"));
//            Parent CheckOutParent = loader.load();
//
//            Stage stage = new Stage(); // New stage (window)
//
//            CheckOutController controller = loader.getController();
//            controller.passData(this, billID, cashierID);
//
//            // Setting the stage up
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setResizable(false);
//            stage.setTitle("Check Out");
//            stage.setScene(new Scene(CheckOutParent));
//            stage.showAndWait();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void homeButtonClicked(ActionEvent event) throws IOException {
        System.out.println("HOME Btn Clicked");


//        System.out.println("deleteItemTransaction");
//        Database.deleteItemTransactionByBill(currentBillNumber);



        FXMLLoader loader = new FXMLLoader();

        String fxml;
        String title;

        if (role.equals("Cashier")){
            fxml = "CashierHomePage.fxml";
            title = "Cashier Home Page";
        } else{
            fxml = "AdminHomePage.fxml";
            title = "Admin Home Page";
        }


        loader.setLocation(getClass().getResource(fxml));
        Parent AdminHomePageParent = loader.load();
        Stage stage = new Stage();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        if (role.equals("Cashier")) {
            CashierHomeController controller = loader.getController();
            controller.passData(username, role);
        }else{
            AdminHomeController controller =loader.getController();
            controller.passData(username, role);
        }

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(new Scene(AdminHomePageParent));
        stage.show();
    }
}
