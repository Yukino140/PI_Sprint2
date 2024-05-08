package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.DataSingleton;
import models.Facture;
import models.Transaction;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.FactureService;
import services.TransactionService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

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

    @FXML
    private Button extractPDF;

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

        extractPDF.setOnMouseClicked((event) -> {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");

            // Add extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show save file dialog
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    // Create a new PDF document
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    document.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(200, 700);
                    contentStream.showText("Facture n°"+f.getId());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Account Number: "+t.getAccount_number());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Receiver Account Number: "+t.getReceiver_account_number());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("date: "+t.getDate());
                    contentStream.endText();

                    // Create table
                    float margin = 50;
                    float startX = margin;
                    float startY = 600;
                    final float rowHeight = 20f;
                    final int rows = 4;
                    final int cols = 3; // Assuming 3 columns
                    final float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

                    // Data for the table
                    String[][] data = {
                            {"Transaction", "Date", "Montant"},
                            {"Transfert", ""+t.getDate(), t.getAmount()+"TND"},
                            {"Tax", "", f.getTax()+"%"},
                            {"Total", " ", f.getMontant_ttc()+"TND"}

                    };

                    // Draw the rows
                    float nextY = startY;
                    for (int i = 0; i <= rows; i++) {
                        contentStream.drawLine(startX, nextY, startX + tableWidth, nextY);
                        nextY -= rowHeight;
                    }

                    // Drawing columns
                    float nextX = startX;
                    contentStream.drawLine(nextX, startY, nextX, nextY + rowHeight);
                    nextX += tableWidth / cols;
                    contentStream.drawLine(nextX, startY, nextX, nextY + rowHeight);
                    nextX += tableWidth / cols;
                    contentStream.drawLine(nextX, startY, nextX, nextY + rowHeight);

                    // Add data to table cells
                    float textX = startX + 5;

                    for (int i = 0; i < data.length; i++) {
                         float textY = startY - rowHeight * (i + 1) + rowHeight / 2;
                         for (int j = 0; j < data[i].length; j++) {
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.HELVETICA, 10);
                            contentStream.newLineAtOffset(textX + j * (tableWidth / cols), textY);
                            contentStream.showText(data[i][j]);
                            contentStream.endText();
                        }
                    }


                    contentStream.close(); // Close content stream after drawing all content

                    // Save the document
                    document.save(file);
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
