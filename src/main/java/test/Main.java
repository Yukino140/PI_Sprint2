package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends Application {
    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashFX.fxml"));
            Parent  parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setTitle("FinancialHub");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public void changeScene(String fxmlFile) {
        Parent root = null;
        try {
            // Charge le fichier FXML
            root = FXMLLoader.load(Main.class.getResource(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}