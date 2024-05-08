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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import models.*;
import org.apache.poi.ss.usermodel.Cell;
import services.AccountService;
import services.ClientService;
import services.FactureService;
import services.TransactionService;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


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
    private TableColumn<Transaction, String> delete;

    @FXML
    private TableView<Transaction> tableView;

    @FXML
    private Button exportExcel;

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
    private AnchorPane facture;

    DataSingleton d=DataSingleton.getInstance();

    Client c=new Client();
    ClientService cs=new ClientService();
    TransactionService ts = new TransactionService();
    @FXML
    void initialize() throws SQLException {
        this.c=this.cs.getById(1);

        loadData();

    }

    public void loadData() throws SQLException {
        this.acc=this.accountService.getById(1);

        System.out.println(ts.readAllByClient(this.c.getId()));
        ObservableList<Transaction> observableliste = FXCollections.observableList(ts.readAllByClient(this.c.getId()));
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
                    final Button btn2=new Button("delete");
                    final ButtonGroup bg=new ButtonGroup();



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


                                                try {
                                                    d.setId(data.getId());


                                                    Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/Facture.fxml"));
                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                    alert.setTitle("Facture n"+data.getId());
                                                    alert.getDialogPane().setContent(newLoadedPane);
                                                    alert.show();


                                                } catch (IOException ex) {
                                                    ex.printStackTrace();
                                                    // Handle the exception (e.g., show an error message)
                                                }


                                            }
                                    );

                                }
                                if (!factureService.hasFacture(getTableView().getItems().get(getIndex()).getId())){

                                    setGraphic(btn1);

                                    btn1.setOnAction(event -> {
                                        // Get the data from the current row
                                        Transaction data = getTableView().getItems().get(getIndex());
                                        double s=(data.getAmount()*1)/100;
                                        try {

                                            factureService.create(new Facture(data.getId(),1,data.getAmount()+s));
                                            loadData();


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
        delete.setCellFactory(new Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>>() {
            @Override
            public TableCell<Transaction, String> call(TableColumn<Transaction, String> param) {
                final TableCell<Transaction, String> cell = new TableCell<Transaction, String>() {

                    final Button btn2=new Button("delete");



                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {

                            setGraphic(btn2);

                            btn2.setOnAction(event -> {
                                Transaction data = getTableView().getItems().get(getIndex());
                                System.out.println(data.getAmount());

                                try {
                                    ts.update(data);
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Facture n"+data.getId());
                                    alert.setContentText("success deleting");
                                    alert.show();
                                    loadData();

                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                            } );


                        }





                    }
                };
                return cell;
            }

        });

// Add the button column to your TableView
        tableView.getColumns().add(delete);

        tableview();
        List<Transaction> td=ts.readAllByAccounntNumber(this.acc.getAccount_number());

        exportExcel.setOnAction(actionEvent ->
        {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Data");

                // Write data to Excel
               /* for (int i = 0; i <td.size(); i++) {
                    Row row = sheet.createRow(td.);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("Data " + i);
                }*/

                Row row1=sheet.createRow(0);
                row1.createCell(0).setCellValue("Account Number");
                row1.createCell(1).setCellValue("Receiver Account");
                row1.createCell(2).setCellValue("Amount");
                row1.createCell(3).setCellValue("Date");
                row1.createCell(4).setCellValue("Type");


                int rowNum=1;
                for(Transaction tr:td) {
                    Row row =sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(tr.getAccount_number());
                    row.createCell(1).setCellValue(tr.getReceiver_account_number());
                    row.createCell(2).setCellValue(tr.getAmount());
                    row.createCell(3).setCellValue(tr.getDate());
                    row.createCell(4).setCellValue(""+tr.getTransaction_type());


                }

                for(int i=0;i<5;i++){
                    sheet.autoSizeColumn(i);
                }

                // Choose file location to save the Excel file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Excel File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
                File file = fileChooser.showSaveDialog(null);

                // Save workbook to file
                if (file != null) {
                    try (FileOutputStream fileOut = new FileOutputStream(file)) {
                        workbook.write(fileOut);
                        System.out.println("Excel file has been exported successfully!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        }






    @FXML
    void refrechTable(MouseEvent event) throws SQLException {
        initialize();

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
