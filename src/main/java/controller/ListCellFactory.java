package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import models.Transaction;

public class ListCellFactory extends ListCell<Transaction> {

    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/ViewTransactions.fxml"));
            TransactioncellController controller = new TransactioncellController(transaction);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
