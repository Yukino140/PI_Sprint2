package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TranController implements Initializable {

    public ListView listT;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listT.setCellFactory(param -> new ListCellFactory());
    }
}
