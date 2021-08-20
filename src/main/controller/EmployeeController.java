package main.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.EmployeeModel;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class EmployeeController implements Initializable{
    @FXML
    private StackPane contenArea;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private Label home;

    public EmployeeModel employee = new EmployeeModel();
    UserHolder holder = UserHolder.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        User u = holder.getUser();
        String name = u.getName();
        home.setText(name.toUpperCase(Locale.ROOT));
    }

    public void home(javafx.scene.input.MouseEvent event){
        bp.setCenter(ap);
        User u = holder.getUser();
        String name = u.getName();
        home.setText(name.toUpperCase(Locale.ROOT));
    }
    public void book(javafx.scene.input.MouseEvent event){
        loadPage("emp_admin_Book");
    }

    public void managebook(javafx.scene.input.MouseEvent  event) throws IOException {
        loadPage("empManageBook");
    }

    public void manageacc(javafx.scene.input.MouseEvent  event) throws IOException {
        loadPage("empManageAcc");
    }

    public void logOUT(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }

    private void loadPage(String page){
        Parent fxml = null;
        try{
            fxml = FXMLLoader.load(getClass().getResource("../ui/"+page+".fxml"));
        }catch (IOException e){
            e.printStackTrace();
        }
        bp.setCenter(fxml);
    }
}
