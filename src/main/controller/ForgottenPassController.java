package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.ForgottenPassModel;
import main.model.ResetModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;

public class ForgottenPassController implements Initializable {

    public ForgottenPassModel forgetModel = new ForgottenPassModel();
    @FXML
    private Label isConnectedRes;
    @FXML
    private TextField userName;


    UserHolder holder = UserHolder.getInstance();
    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (forgetModel.isDbConnected()){
            isConnectedRes.setText("Connected");
        }else{
            isConnectedRes.setText("Not Connected");
        }

    }
//    public void Submit(ActionEvent event) throws IOException {
//
//        try {
//            if (forgetModel.isUser(userName.getText())) {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/resetPW.fxml"));
//                Parent root = loader.load();
//                ResetPassController resetctrl = loader.getController();
//                resetctrl.transferMessage(userName.getText());
//                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                window.setScene(new Scene(root));
//                window.show();
//            }else{
//                isConnectedRes.setText("Incorrect username, username does not exist.");
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//    }

    public void SendData(MouseEvent event) throws IOException {
        User u = new User();
        String a = userName.getText();
        try {
            if (forgetModel.isUser(userName.getText())) {
                u.setName(a);
                holder.setUser(u);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/resetPW.fxml"));
                Parent root = loader.load();
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(root));
                window.show();
            }else{
                isConnectedRes.setText("Incorrect username, username does not exist.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void Login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }

}
