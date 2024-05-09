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
        this.amount.setText(""+this.transaction.getAmount());
        this.date.setText(""+this.transaction.getDate());
        this.from.setText(this.transaction.getAccount_number());
        this.to.setText(this.transaction.getReceiver_account_number());
        this.type.setText(""+this.transaction.getTransaction_type());
    }
}
