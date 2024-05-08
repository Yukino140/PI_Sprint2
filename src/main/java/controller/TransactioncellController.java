package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactioncellController implements Initializable {

    @FXML
    private Label amount;

    @FXML
    private Label date;

    @FXML
    private Label from;

    @FXML
    private Label to;

    @FXML
    private Label type;

    private final Transaction transaction;

    public TransactioncellController(Transaction transaction){
        this.transaction=transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amount.setText(""+transaction.getAmount());
        date.setText(""+transaction.getDate());
        from.setText(transaction.getAccount_number());
        to.setText(transaction.getReceiver_account_number());
        type.setText(""+transaction.getTransaction_type());
    }
}
