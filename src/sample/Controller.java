package sample;

import javafx.fxml.FXML;

public class Controller {

    @FXML
    private void testbuttonClicked(){
        Database.newItemTransaction(3,3, 1);
        Database.testconnect();
    }

}
