package sample;

import javafx.fxml.FXML;

public class Controller {

    @FXML
    private void testbuttonClicked(){
        Database.testconnect();
    }

}
