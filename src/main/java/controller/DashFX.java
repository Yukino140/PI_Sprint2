package controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Transaction;
import models.Transaction_Type;
import services.TransactionService;
import utils.MyDatabase;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class DashFX extends Application {

    @FXML
    private TableColumn<Transaction,Double> amount;

    @FXML
    private TableColumn<Transaction, Date> date;

    @FXML
    private TableColumn<Transaction,String> from;

    @FXML
    private TableView<Transaction> tableView;

    @FXML
    private TableColumn<Transaction,String> to;

    @FXML
    private TableColumn<Transaction, Transaction_Type> type;

    ObservableList<Transaction> listT;

    int index = -1;
    MyDatabase conn = null;
    ResultSet rs=null;
    Statement st=null;
    TransactionService transactionService=new TransactionService();
    private double xOffset = 0;
    private double yOffset = 0;

    private void loadDate() {

        //listT=this.transactionService.read();

        this.from = new TableColumn<>();
        this.to = new TableColumn<>();
        this.amount = new TableColumn<>();
        this.date = new TableColumn<>();
        this.type = new TableColumn<>();
        this.tableView=new TableView<>();

        from.setCellValueFactory(new PropertyValueFactory<Transaction,String>("account_number"));
        to.setCellValueFactory(new PropertyValueFactory<Transaction,String>("receiver_account_number"));
        amount.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("amount"));
        type.setCellValueFactory(new PropertyValueFactory<Transaction,Transaction_Type>("transaction_type"));


        ObservableList<Transaction> data = FXCollections.observableArrayList(this.transactionService.read());


        tableView.setItems(data);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ViewTransactions.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);

            loadDate();
        // Create columns

        //grab your root here
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        //set transparent
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
