package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Account;
import models.Transaction;
import models.Transaction_Type;
import services.AccountService;
import services.TransactionService;
import utils.MyDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionController {

    @FXML
    private TextField addressField= new TextField();

    @FXML
    private TextField amountField = new TextField();

    @FXML
    private TextField authenticatorField = new TextField();

    @FXML
    private ChoiceBox<Transaction_Type> typeField = new ChoiceBox<>();



    @FXML
    private Button insert;

    @FXML
    void handleButtonAction(ActionEvent event) {

    }

    int index = -1;
    MyDatabase conn = null;
    ResultSet rs=null;
    Statement st=null;
    TransactionService transactionService=new TransactionService();
    Account acc=new Account();
    AccountService accountService=new AccountService();

    @FXML
    void initialize() throws SQLException {
        this.acc=this.accountService.getById(1);
        System.out.println(this.acc.toString());
        typeField.getItems().addAll(Transaction_Type.SAVINGS,Transaction_Type.BUSINESS,Transaction_Type.JOINT,Transaction_Type.STUDENT,Transaction_Type.CHECKING);

    }

    @FXML
    public void save(MouseEvent event) throws SQLException {
        conn = MyDatabase.getInstance();
        String addressField = this.addressField.getText();
        double amountField = Double.parseDouble(this.amountField.getText());
        double authenticator = Double.parseDouble(this.authenticatorField.getText());
        String typeField = this.typeField.getTypeSelector();

        TransactionService ts=new TransactionService();

     /*   if (addressField.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The Addresse Field is required");
            alert.showAndWait();

        } else if(Double.parseDouble(this.amountField.getText())>this.acc.getBalance()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The Amount exceedes your actual Balance");
            alert.showAndWait();
        }else if(typeField.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("You need to Chose with account you want use");
            alert.showAndWait();
        }else if((this.authenticatorField.getText().trim().length()<6)||(this.authenticatorField.getText().trim().length()>6)){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The authenticator Code has 6 numbers");
            alert.showAndWait();
        }else  {
            ts.create(t);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Transaction crée avec succés!");
            alert.show();


        }*/


        if (this.addressField.getText().isEmpty() || this.typeField.getValue() == null || this.amountField.getText().isEmpty() || this.authenticatorField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Empty Form", "Please fill in the form before submitting.");
        } else if (this.addressField.getText().isEmpty() || this.addressField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Address Field Required", "The Address Field is required.");
        } else if (this.typeField.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Account Type Required", "You need to choose which account you want to use.");
        } else {

                double amount = Double.parseDouble(this.amountField.getText());
                if (amount > acc.getBalance()) {
                    showAlert(Alert.AlertType.ERROR, "Amount Exceeds Balance", "The Amount exceeds your actual Balance.");
                } else if (authenticatorField.getText().trim().length() != 6) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Authenticator Code", "The authenticator Code must have exactly 6 numbers.");
                } else {
                    Transaction t=new Transaction(this.acc.getAccount_number(),this.typeField.getValue(),Double.parseDouble(this.amountField.getText()),Integer.parseInt(this.authenticatorField.getText()),this.addressField.getText(),0);

                    ts.create(t);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction créée avec succès!");
                }

        }




    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
