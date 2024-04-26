package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import models.DataSingleton;
import models.Facture;
import models.Transaction;
import services.FactureService;
import services.TransactionService;

import java.sql.SQLException;

public class FactureController {

    @FXML
    private Text accnum;

    @FXML
    private Text date;

    @FXML
    private Text date2;

    @FXML
    private Button delete;

    @FXML
    private Text montant;

    @FXML
    private Text rcnum;

    @FXML
    private Text tax;

    @FXML
    private Text titre;

    @FXML
    private Text ttc;
    DataSingleton data=DataSingleton.getInstance();

    FactureService factureService=new FactureService();
    TransactionService transactionService=new TransactionService();
    Facture f=new Facture();
    Transaction t=new Transaction();

    public void initialize() throws SQLException {
         f=this.factureService.getById(data.getId());
         t=this.transactionService.getById(data.getId());

        titre.setText("Facture n°"+f.getId());
        accnum.setText(t.getAccount_number());
        rcnum.setText(t.getReceiver_account_number());
        date.setText(""+t.getDate());
        date2.setText(""+t.getDate());
        montant.setText(""+t.getAmount()+"TND");
        tax.setText(""+f.getTax()+"%");
        ttc.setText(""+f.getMontant_ttc()+"TND");
        delete.setOnAction(event->{
            try {
                this.factureService.delete(f.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("success deleting facture n°"+f.getId());
                alert.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
