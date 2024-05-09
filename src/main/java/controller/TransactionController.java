package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;
import services.AccountService;
import services.ClientService;
import services.ContactService;
import services.TransactionService;
import utils.MyDatabase;
import javafx.embed.swing.SwingFXUtils;


import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;


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
    private ChoiceBox<String> contacts;

    @FXML
    void handleButtonAction(ActionEvent event) {

    }

    int index = -1;
    MyDatabase conn = null;
    ResultSet rs=null;
    Statement st=null;
    TransactionService transactionService=new TransactionService();
    Account acc=new Account();
    Client  c=new Client();
    AccountService accountService=new AccountService();
    ClientService clientService=new ClientService();
    ContactService contactService=new ContactService();

    @FXML
    private Button pndgdown;
    @FXML
    void initialize() throws SQLException {
        this.pndgdown.setVisible(false);

        this.c=this.clientService.getById(1);
        List<Account> ls = this.accountService.getAccountsOfClient(this.c.getId());
        this.acc=this.accountService.getById(1);
        System.out.println(this.acc.toString());
        System.out.println(ls);
        for(int i=0;i<ls.size();i++){
            System.out.println(ls.get(i).getAccount_type());
            typeField.getItems().add(i,ls.get(i).getAccount_type());
        }

        List<Contact> lc=this.contactService.getByClient(this.c.getId());
        //typeField.getItems().addAll(Transaction_Type.SAVINGS,Transaction_Type.BUSINESS,Transaction_Type.JOINT,Transaction_Type.STUDENT,Transaction_Type.CHECKING);

        for(int i=0;i<lc.size();i++){
            contacts.getItems().add(i,lc.get(i).getContactName());
        }
        this.contacts.getItems().add("other");
        this.contacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (!(newValue==null)&&(!newValue.equals("other"))){
                this.addressField.setDisable(true);
                this.addressField.setText(this.contactService.getContact(this.c.getId(),newValue).getContactAccount());
            } else if(Objects.equals(newValue, "other")){
                // If TextField has text, enable the button
                this.addressField.setDisable(false);
                this.addressField.setText("");
            }
            else{
                this.addressField.setDisable(false);

            }
        });
        this.typeField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Account acc1=this.accountService.getAccountByAccountType(String.valueOf(newValue),this.c.getId());
            Image qrCodeImage = generateQRCode(acc1.getAccount_number(), 112, 94);
            ImageView imageView = new ImageView(qrCodeImage);
            this.qrPane.getChildren().add(imageView);
            this.pndgdown.setVisible(true);
            pndgdown.setOnAction(e -> {
                try {
                    downloadImage(qrCodeImage, "image.png");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });


        });


        // Create an ImageView to display the QR code


    }

    private void downloadImage(Image image, String filename) throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save QR CODE");

        // Add extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png File (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(stage);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }
    @FXML
    private Pane qrPane;


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

        if (this.addressField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir une adresse email.");
            alert.showAndWait();
            return;
        }

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
                    Account acc1=this.accountService.getAccountByAccountType(String.valueOf(this.typeField.getValue()),this.c.getId());
                    this.accountService.updateBalance(acc1.getAccount_number(),-Double.parseDouble(this.amountField.getText()));
                    this.accountService.updateBalance(this.addressField.getText(),-Double.parseDouble(this.amountField.getText()));
                    Transaction t=new Transaction(acc1.getAccount_number(),this.typeField.getValue(),Double.parseDouble(this.amountField.getText()),Integer.parseInt(this.authenticatorField.getText()),this.addressField.getText(),0);

                    ts.create(t);
                    this.addressField.clear();
                    this.amountField.clear();
                    this.authenticatorField.clear();
                    
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

    private Image generateQRCode(String text, int width, int height) {
        try {
            // Create QR code writer
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Set encoding hints
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Generate QR code matrix
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // Convert bit matrix to image
            Image image = SwingFXUtils.toFXImage(MatrixToImageWriter.toBufferedImage(bitMatrix), null);
            return image;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
