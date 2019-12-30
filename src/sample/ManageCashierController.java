package sample;

import impl.org.controlsfx.tools.rectangle.change.NewChangeStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javax.xml.crypto.Data;
import java.io.IOException;
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

    @FXML
    public void newCashierButtonClicked(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewCashierPage.fxml"));
            Parent NewCashierParent = loader.load();

            Stage stage = new Stage(); // New stage (window)

            NewCashierController controller = loader.getController();
            controller.passData(this);

            // Setting the stage up
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("New Cashier");
            stage.setScene(new Scene(NewCashierParent));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editCashierButtonClicked(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditCashierPage.fxml"));
            Parent EditCashierParent = loader.load();

            Stage stage = new Stage(); // New stage (window)

            EditCashierController controller = loader.getController();
            controller.passData(this, cashierTable.getSelectionModel().getSelectedItem());

            // Setting the stage up
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Edit Cashier");
            stage.setScene(new Scene(EditCashierParent));
            stage.showAndWait();
        } catch (NullPointerException e){
            System.out.println("No selection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteCashierButtonClicked(){
        try{
            Cashier selected = cashierTable.getSelectionModel().getSelectedItem();
            Database.deleteCashier(selected.getCashierID());
            refresh();

        } catch (NullPointerException e){
            System.out.println("no selection");
        }
    }


}
