package controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Transaction;
import models.Transaction_Type;
import services.TransactionService;
import utils.MyDatabase;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ImageView Exit;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView menuback;

    @FXML
    private SplitPane slide;

    @FXML
    private SplitPane menu_divider;

    @FXML
    private ImageView open;

    @FXML
    private ImageView close;

    @FXML
    private Pane overview;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane body;

    @FXML
    private TableColumn<Transaction,Double> amount;

    @FXML
    private TableColumn<Transaction,Date> date;

    @FXML
    private TableColumn<Transaction,String> from;

    @FXML
    private TableView<Transaction> tableView;

    @FXML
    private TableColumn<Transaction,String> to;

    @FXML
    private TableColumn<Transaction,Transaction_Type> type;

    ObservableList<Transaction> listT;

    int index = -1;
    MyDatabase conn = null;
    ResultSet rs=null;
    Statement st=null;
    TransactionService transactionService=new TransactionService();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SplitPane.setResizableWithParent(menu_divider, Boolean.FALSE);
        SplitPane.setResizableWithParent(slide, Boolean.FALSE);

        slide.setDividerPosition(0,0.0687);
        menu.setOnMouseClicked(event->{

            TranslateTransition slider=new TranslateTransition();
            slider.setDuration(Duration.seconds(0.4));
            slider.setNode(slide);

            slider.setToX(0);
            slider.play();

            slide.setDividerPosition(0,0.3);

            slider.setOnFinished((ActionEvent e)-> {
                menu.setVisible(false);
                menuback.setVisible(true);
            });
        });

        menuback.setOnMouseClicked(event->{

            TranslateTransition slider=new TranslateTransition();
            slider.setDuration(Duration.seconds(0.4));
            slider.setNode(slide);

            slider.setToX(1);
            slider.play();

            slide.setDividerPosition(0,0.0687);

            slider.setOnFinished((ActionEvent e)-> {
                menu.setVisible(true);
                menuback.setVisible(false);
            });
        });

        menu_divider.setDividerPosition(0,0.3748);
        open.setOnMouseClicked(event ->{
            TranslateTransition listSlide = new TranslateTransition();
            listSlide.setDuration(Duration.seconds(0.4));
            listSlide.setNode(menu_divider);

            listSlide.play();

            menu_divider.setDividerPosition(0,0.649);

            listSlide.setOnFinished((ActionEvent e)->{
                open.setVisible(false);
                close.setVisible(true);
            });
        });

        close.setOnMouseClicked(event ->{
            TranslateTransition listSlide = new TranslateTransition();
            listSlide.setDuration(Duration.seconds(0.4));
            listSlide.setNode(menu_divider);

            listSlide.play();

            menu_divider.setDividerPosition(0,0.3748);

            listSlide.setOnFinished((ActionEvent e)->{
                open.setVisible(true);
                close.setVisible(false);
            });
        });


    }

    private void loadDate() {

        //listT=this.transactionService.read();

        this.from = new TableColumn<>();
        this.to = new TableColumn<>();
        this.amount = new TableColumn<>();
        this.date = new TableColumn<>();
        this.type = new TableColumn<>();

        from.setCellValueFactory(new PropertyValueFactory<Transaction,String>("account_number"));
        to.setCellValueFactory(new PropertyValueFactory<Transaction,String>("receiver_account_number"));
        amount.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("amount"));
        type.setCellValueFactory(new PropertyValueFactory<Transaction,Transaction_Type>("transaction_type"));


        ObservableList<Transaction> data = FXCollections.observableArrayList(this.transactionService.read());


        tableView.setItems(data);


    }

    public Controller() {
    }

    public void toOverview(Event e) throws IOException {
        try {
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/Overview.fxml"));

            AnchorPane.setTopAnchor(newLoadedPane, 0.0);
            AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
            AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
            AnchorPane.setRightAnchor(newLoadedPane, 0.0);
            if(body.getChildren().isEmpty()){
                body.getChildren().add(newLoadedPane);

            }else{
                body.getChildren().set(0,newLoadedPane);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    public void toAllTransactions(MouseEvent e) throws IOException{
        try {
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/Transactions.fxml"));

            AnchorPane.setTopAnchor(newLoadedPane, 0.0);
            AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
            AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
            AnchorPane.setRightAnchor(newLoadedPane, 0.0);
            if(body.getChildren().isEmpty()){
                body.getChildren().add(newLoadedPane);
               /* this.tableView = new TableView<>();
                loadDate();*/


            }else{
                body.getChildren().set(0,newLoadedPane);
                this.tableView = new TableView<>();

               // loadDate();


            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    public void toAllTransactions2() throws IOException{
        try {
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/TransactionsTable.fxml.fxml"));

            AnchorPane.setTopAnchor(newLoadedPane, 0.0);
            AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
            AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
            AnchorPane.setRightAnchor(newLoadedPane, 0.0);
            if(body.getChildren().isEmpty()){
                body.getChildren().add(newLoadedPane);
               /* this.tableView = new TableView<>();
                loadDate();*/


            }else{
                body.getChildren().set(0,newLoadedPane);
                this.tableView = new TableView<>();

                // loadDate();


            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    public void toAddTransaction(MouseEvent e) throws IOException{
        try {
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/addTransaction.fxml"));

            AnchorPane.setTopAnchor(newLoadedPane, 0.0);
            AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
            AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
            AnchorPane.setRightAnchor(newLoadedPane, 0.0);
            if(body.getChildren().isEmpty()){
                body.getChildren().add(newLoadedPane);

            }else{
                body.getChildren().set(0,newLoadedPane);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }


    public void toContacts(MouseEvent mouseEvent) {
        try {
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/contacts.fxml"));

            AnchorPane.setTopAnchor(newLoadedPane, 0.0);
            AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
            AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
            AnchorPane.setRightAnchor(newLoadedPane, 0.0);
            if(body.getChildren().isEmpty()){
                body.getChildren().add(newLoadedPane);

            }else{
                body.getChildren().set(0,newLoadedPane);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
}
