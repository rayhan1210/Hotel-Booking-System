package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private Label home;
    UserHolder holder = UserHolder.getInstance();

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
    public void book(ActionEvent event){
        loadPage("emp_admin_Book");
    }
    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }

    public void managebook(ActionEvent event) throws IOException {
        loadPage("admManageBook");
    }
    public void deskDetails(ActionEvent event) throws IOException {
        loadPage("DeskDetails");
    }

    public void AddUser(ActionEvent event) throws IOException {
        loadPage("addUser");
    }
    public void UpdateUser(ActionEvent event) throws IOException {
        loadPage("updateUser");
    }
    public void DeleteUser(ActionEvent event) throws IOException {
        loadPage("deleteUser");
    }
    public void Report(ActionEvent event) throws IOException {
        loadPage("Report");
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
