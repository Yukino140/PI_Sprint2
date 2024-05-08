package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class OverviewController  {

    private static final String API_KEY = "SGPG486PGV476B73";
    private static final String API_KEY2 = "7QH7BVFAFCQ8162E";

    private static final String API_ENDPOINT = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=USD&to_currency=EUR&apikey=" + API_KEY2;

    private static final String API_ENDPOINT1 = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=EUR&to_currency=TND&apikey=" + API_KEY2;
    private static final String API_ENDPOINT2 = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=USD&to_currency=TND&apikey=" + API_KEY2;
    private static final String API_ENDPOINT3 = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=USD&to_currency=JPY&apikey=" + API_KEY2;


    @FXML
    private Label eurtnd;

    @FXML
    private Label usdeur;

    @FXML
    private Label usdjpy;

    @FXML
    private Label usdtnd;

    public void initialize() {
            this.eurtnd.setText("LOADING...");
            this.usdjpy.setText("LOADING...");
            this.usdtnd.setText("LOADING...");
            this.usdeur.setText("LOADING...");

           //this.loadData();

            this.loadChart();


    }
    public void loadData(){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                JSONObject response = fetchCurrencyExchangeRate(API_ENDPOINT);
                if (response != null) {
                    String exchangeRate = response.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
                    updateMessage("USD/EUR: " + exchangeRate);
                    System.out.println(exchangeRate);

                } else {

                    updateMessage("Failed to fetch data");
                    System.out.println("Failer");


                }
                return null;
            }
        };

        this.usdeur.textProperty().bind(task.messageProperty());

        Task<Void> task1 = new Task<>() {
            @Override
            protected Void call() throws Exception {
                JSONObject response = fetchCurrencyExchangeRate(API_ENDPOINT1);
                if (response != null) {
                    String exchangeRate = response.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
                    updateMessage("EUR/TND: " + exchangeRate);
                    System.out.println(exchangeRate);

                } else {

                    updateMessage("Failed to fetch data");
                    System.out.println("Failer");


                }
                return null;
            }
        };
        this.eurtnd.textProperty().bind(task1.messageProperty());
        Task<Void> task2 = new Task<>() {
            @Override
            protected Void call() throws Exception {
                JSONObject response = fetchCurrencyExchangeRate(API_ENDPOINT2);
                if (response != null) {
                    String exchangeRate = response.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
                    updateMessage("USD/TND: " + exchangeRate);
                    System.out.println(exchangeRate);

                } else {

                    updateMessage("Failed to fetch data");
                    System.out.println("Failer");


                }
                return null;
            }
        };
        this.usdtnd.textProperty().bind(task2.messageProperty());

        Task<Void> task3 = new Task<>() {
            @Override
            protected Void call() throws Exception {
                JSONObject response = fetchCurrencyExchangeRate(API_ENDPOINT3);
                if (response != null) {
                    String exchangeRate = response.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
                    updateMessage("USD/JPY: " + exchangeRate);
                    System.out.println(exchangeRate);

                } else {

                    updateMessage("Failed to fetch data");
                    System.out.println("Failer");


                }
                return null;
            }
        };
        this.usdjpy.textProperty().bind(task3.messageProperty());
        // Start the task
        new Thread(task).start();
        new Thread(task1).start();
        new Thread(task2).start();
        new Thread(task3).start();
    }
    private JSONObject fetchCurrencyExchangeRate(String s) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(s);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                System.out.println(response);

            }
            reader.close();

            return new JSONObject(response.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @FXML
    void refresh(ActionEvent event) {
        this.loadData();
    }

    @FXML
    private Pane line;
    @FXML
    private Pane pie;


    public void loadChart(){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(1, 10));
        series.getData().add(new XYChart.Data<>(2, 20));
        series.getData().add(new XYChart.Data<>(3, 15));
        series.getData().add(new XYChart.Data<>(4, 25));

        // Create line chart
        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.getData().add(series);
        this.line.getChildren().add(lineChart);
        // Create label for displaying summary statistics
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Category A", 30),
                new PieChart.Data("Category B", 20),
                new PieChart.Data("Category C", 50));

        // Create pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setPrefSize(200, 200);
        this.pie.getChildren().add(pieChart);
    }
}
