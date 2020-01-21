package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemsTransactionController implements Initializable {

    int billID;
    int total = 0;

    ObservableList<ItemTransactionCart> cartList = FXCollections.observableArrayList();


    @FXML private TableView<ItemTransactionCart> cartTable;
    @FXML private TableColumn<ItemTransactionCart, String> productIDCartCol;
    @FXML private TableColumn<ItemTransactionCart, String> productNameCartCol;
    @FXML private TableColumn<ItemTransactionCart, String> priceCartCol;
    @FXML private TableColumn<ItemTransactionCart, String> qtyCartCol;
    @FXML private TableColumn<ItemTransactionCart, String> subtotalCartCol;

    @FXML private Label totalPay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void passData(int billID){
        this.billID = billID;
        getTransactions();
    }

    public void getTransactions(){
        ResultSet rs;
        rs = Database.getAllItemTransactions(billID);
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
        totalPay.setText("Rp. " + this.total);
    }

    public void closeButtonClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
