package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageCashierController implements Initializable {

    @FXML private TableView<Cashier> cashierTable;
    @FXML private TableColumn<Cashier, Integer> cashierIDCol;
    @FXML private TableColumn<Cashier, String> cashierNameCol;
    @FXML private TableColumn<Cashier, String> cashierPassCol;
    @FXML private TableColumn<Cashier, String> adminStatusCol;
    ObservableList<Cashier> cashierList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cashierIDCol.setCellValueFactory(new PropertyValueFactory<>("cashierID"));
        cashierNameCol.setCellValueFactory(new PropertyValueFactory<>("cashierName"));
        cashierPassCol.setCellValueFactory(new PropertyValueFactory<>("cashierPass"));
        adminStatusCol.setCellValueFactory(new PropertyValueFactory<>("adminStatus"));

        refresh();
    }

    @FXML
    public void refresh(){
        cashierList.clear();
        cashierList.addAll(Database.getAllCashier());
        cashierTable.setItems(cashierList);

    }
}
