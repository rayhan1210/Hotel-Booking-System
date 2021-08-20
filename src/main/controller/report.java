package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.model.ReportModel;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class report implements Initializable {
//    @FXML
//    private Button book
    ReportModel reportModel = new ReportModel();
    String[] stat = {"Pending", "Accepted","Cancelled","CheckedIn"};
    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void bookReport(ActionEvent event) throws SQLException {
        //call function
        Stage popupwindow = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Booking Report");
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(
           new FileChooser.ExtensionFilter("Microsoft Excel Comma Separated Values File", "*.csv"));
        File file = fileChooser.showSaveDialog(popupwindow);

        if(file != null) {
            String f = file.toString();
            System.out.println("f: " + f);
            reportModel.getBookReport(stat[0], stat[1],stat[2],stat[3], f);
        }
    }
    public void empReport(ActionEvent event) throws SQLException {
        //call fucntion
        Stage popupwindow = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Employee Report");
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Microsoft Excel Comma Separated Values File", "*.csv"));
        File file = fileChooser.showSaveDialog(popupwindow);

        if(file != null) {
            String f = file.toString();
            System.out.println("f: " + f);
            reportModel.getEmpReport("employee", f);
        }
    }

}
