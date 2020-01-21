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
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @FXML private TableView<ItemTransactionCart> cartTable;
    @FXML private TableColumn<ItemTransactionCart, Integer> productIDCartCol;
    @FXML private TableColumn<ItemTransactionCart, String> productNameCartCol;
    @FXML private TableColumn<ItemTransactionCart, Integer> priceCartCol;
    @FXML private TableColumn<ItemTransactionCart, Integer> qtyCartCol;
    @FXML private TableColumn<ItemTransactionCart, Integer> subtotalCartCol;

    private ObservableList<Product> inventoryList = FXCollections.observableArrayList();
    private ObservableList<ItemTransactionCart> cartList = FXCollections.observableArrayList();
    @FXML private TextField qtyField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentBillNumber = Database.getBillNumber();
        if (!Database.lastBillEmpty(currentBillNumber)){
            System.out.println("AddInitialBill");
            Database.addInitBill();
        }
        else{
            System.out.println("Recover unfinished transaction");
        }
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

//      INVENTORY TABLE
        currentBillNumber = Database.getBillNumber();
        System.out.println("currBillNum"+currentBillNumber);
        productIDInvenCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameInvenCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        priceInvenCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));


        inventoryList.clear();
        inventoryList.addAll(Database.getAllProducts());
        inventoryTable.setItems(inventoryList);

//      CART TABLE
        ResultSet rs;
        rs = Database.getAllItemTransactions(currentBillNumber);

        try {
            cartList.clear();
            this.total = 0;

            while (rs.next()) {
                int subtotal_cart = rs.getInt("productPrice")*rs.getInt("qty");
                this.total += subtotal_cart;

                cartList.add(new ItemTransactionCart(rs.getInt("productID"),

                        rs.getString("productName"), rs.getInt("productPrice")
                , rs.getInt("qty"), subtotal_cart));
            }

            rs.close();

            productIDCartCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
            productNameCartCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
            priceCartCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
            qtyCartCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
            subtotalCartCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
            cartTable.setItems(cartList);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("no data");
        }



//         Database.sumItemTransaction(currentBillNumber);
        System.out.println("TOTAL"+this.total);
        totalLabel.setText("TOTAL " + this.total);

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

            String qtytxtfield = qtyField.getText();
            if (!qtytxtfield.equals("")){
                qty = Integer.parseInt(qtytxtfield);
            }

//            if item not found, then add new item transaction
            int itemQty = Database.itemTransactionExist(currentBillNumber,productID);
            if (itemQty == 0){
                System.out.println("item does not exist, PrevitemQty = "+itemQty);
                Database.addItemTransaction(billID, productID, qty);
            }
//            otherwise update item transaction qty
            else{
                System.out.println("item exists, PrevitemQty = "+ itemQty);
                int newQty = itemQty + qty;
                Database.updateItemTransaction(currentBillNumber, productID, newQty);
            }
            refresh();


        } catch (NullPointerException e){
            System.out.println("no selection");
        }
    }

    @FXML
    public void deleteItemButtonClicked(){

        try{
            ItemTransactionCart selected = cartTable.getSelectionModel().getSelectedItem();
            Database.deleteItemTransaction(selected.getProductID(),currentBillNumber);
            refresh();

        } catch (NullPointerException e){
            System.out.println("no selection");
        }
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
