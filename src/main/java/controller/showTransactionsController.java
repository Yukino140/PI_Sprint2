package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import models.Account;
import models.Facture;
import models.Transaction;
import services.AccountService;
import services.FactureService;
import services.TransactionService;

import java.io.IOException;
import java.sql.SQLException;

public class showTransactionsController {

    @FXML
    private TableColumn<?, ?> ColAmount;

    @FXML
    private TableColumn<?, ?> ColDate;

    @FXML
    private TableColumn<?, ?> ColFrom;

    @FXML
    private TableColumn<?, ?> ColTo;

    @FXML
    private TableColumn<?, ?> ColType;

    @FXML
    private TableColumn<Transaction, String> ColAction;


    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TextField tDepartement;

    @FXML
    private TextField tId;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tName;

    @FXML
    private TextField tPassword;

    @FXML
    private TextField tPrenom;

    @FXML
    private TextField tRole;

    @FXML
    private TextField tSalaire;

    @FXML
    private TableView<Transaction> tableView;

   /* @FXML
    void AjouterUser(ActionEvent event) throws SQLException {
        User u = new User(Integer.parseInt(tSalaire.getText()),tEmail.getText(),tRole.getText(), tPassword.getText(), tName.getText(), tPrenom.getText() , tDepartement.getText());
        UserService us = new UserService();
        try {
            us.create(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("User insérée avec succés!");
            alert.show();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e.getMessage());
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ModifierUsee(ActionEvent event) {
        User u = new User(Integer.parseInt(tId.getText()),Integer.parseInt(tSalaire.getText()),tEmail.getText(),tRole.getText(), tPassword.getText(), tName.getText(), tPrenom.getText() , tDepartement.getText());
        UserService us = new UserService();
        try {
            us.update(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("User modifier avec succés!");
            alert.show();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e.getMessage());
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SupprimerUser(ActionEvent event) {
        UserService us = new UserService();
        int id1 = Integer.parseInt(tId.getText());
        try {
            us.delete(id1);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("User supprimer avec succés!");
            alert.show();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e.getMessage());
            alert.show();
            throw new RuntimeException(e);
        }
    }
*/
   @FXML
   private AnchorPane body;


   Account acc=new Account();
    AccountService accountService=new AccountService();
    FactureService factureService=new FactureService();

    @FXML
    void initialize() throws SQLException {
        body = new AnchorPane();
        this.acc=this.accountService.getById(1);
        TransactionService ts = new TransactionService();
        ObservableList<Transaction> observableliste = FXCollections.observableList(ts.readAllByAccounntNumber(this.acc.getAccount_number()));
        tableView.setItems(observableliste);
        // Remplir les colonnes avec les propriétés des objets User
        ColAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        ColFrom.setCellValueFactory(new PropertyValueFactory<>("account_number"));
        ColTo.setCellValueFactory(new PropertyValueFactory<>("receiver_account_number"));
        ColType.setCellValueFactory(new PropertyValueFactory<>("transaction_type"));
        ColAction.setCellFactory(new Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>>() {
            @Override
            public TableCell<Transaction, String> call(TableColumn<Transaction, String> param) {
                final TableCell<Transaction, String> cell = new TableCell<Transaction, String>() {
                    final Button btn = new Button("fact");
                    final Button btn1=new Button("create Fcture");


                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            try {
                                if (factureService.hasFacture(getTableView().getItems().get(getIndex()).getId())){
                                    setGraphic(btn);

                                btn.setOnMouseClicked(event -> {
                                    // Get the data from the current row
                                    Transaction data = getTableView().getItems().get(getIndex());
                                    Controller c=new Controller();
                                    try {
                                        c.toAddTransaction(event);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }


                                });}
                                if (!factureService.hasFacture(getTableView().getItems().get(getIndex()).getId())){
                                    setGraphic(btn1);

                                    btn1.setOnAction(event -> {
                                        // Get the data from the current row
                                        Transaction data = getTableView().getItems().get(getIndex());
                                        double s=(data.getAmount()*1)/100;
                                        try {

                                            factureService.create(new Facture(data.getId(),1,data.getAmount()+s));



                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }


                                    });}
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                };
                return cell;
            }
        });

// Add the button column to your TableView
        tableView.getColumns().add(ColAction);

        tableview();

    }






    @FXML
    void tableview() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Transaction selectedUser = tableView.getSelectionModel().getSelectedItem();

            }
        });
    }


}
