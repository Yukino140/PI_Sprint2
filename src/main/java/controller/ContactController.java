package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.Client;
import models.Contact;
import services.AccountService;
import services.ClientService;
import services.ContactService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactController implements Initializable {

    @FXML
    private Pane error;

    @FXML
    private Pane success;

    @FXML
    private Button verify;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    AccountService ac=new AccountService();

    @FXML
    private Text emptyError;

    @FXML
    private Text nameError;

    @FXML
    private Text addressError;

    ClientService clientService=new ClientService();
    ContactService contactService=new ContactService();
    Client client=new Client();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.error.setVisible(false);
        this.success.setVisible(false);
        this.emptyError.setVisible(false);
        this.addressError.setVisible(false);
        this.nameError.setVisible(false);
        try {
             this.client=this.clientService.getById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void verify(ActionEvent event) {
        try {
            if(this.ac.accountExist(this.addressField.getText())){
                this.success.setVisible(true);

            }else{
                this.success.setVisible(false);
                this.error.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void newContact(ActionEvent event) throws SQLException {
        if(nameField.getText().isEmpty()&&addressField.getText().isEmpty()){
            this.emptyError.setVisible(true);
        }else if(this.addressField.getText().isEmpty()||!(this.ac.accountExist(this.addressField.getText()))){
            this.addressError.setVisible(true);
        }else if(this.nameField.getText().isEmpty()){
            this.nameError.setVisible(true);
        }else {
            Contact c=new Contact(this.client.getId(),this.nameField.getText(),this.addressField.getText(),0);
            this.contactService.create(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success");
            alert.setContentText("You added a new Contact");
            alert.showAndWait();
            this.addressField.clear();
            this.nameField.clear();
            this.nameError.setVisible(false);
            this.addressError.setVisible(false);
            this.error.setVisible(false);
            this.success.setVisible(false);
        }
    }
}
