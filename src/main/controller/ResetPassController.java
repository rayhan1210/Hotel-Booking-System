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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.model.ForgottenPassModel;
import main.model.ResetModel;
import main.model.RetrieveUserDetailModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.ResourceBundle;

public class ResetPassController implements Initializable {
    Connection connection;
    public ResetModel resetModel = new ResetModel();
    public RetrieveUserDetailModel rtusrModel = new RetrieveUserDetailModel();
    @FXML
    private Label isConnectedRes;
    @FXML
    private TextField sQs;
    @FXML
    private TextField sAns;
    @FXML
    private TextField userName;
    @FXML
    private TextField newPassWord;
    @FXML
    private Label newPassLabel;
    @FXML
    private Button resetpass;
    @FXML
    private Button goLogin;

    UserHolder holder = UserHolder.getInstance();
    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        userName.setVisible(false);
        goLogin.setVisible(false);
        newPassWord.setVisible(false);
        newPassLabel.setVisible(false);
        sQs.setEditable(false);
        User u = holder.getUser();
        String a = u.getName();
        userName.setText(a);
        String uName = userName.getText();
        System.out.println("uName: " + uName);
        try {
            String Quest = rtusrModel.getSecretQs(uName);
            sQs.setText(Quest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (resetModel.isDbConnected()){
            isConnectedRes.setText("Connected");
        }else{
            isConnectedRes.setText("Not Connected");
        }

    }
    public void Reset(ActionEvent event) throws SQLException {
        String name = userName.getText();
        try {
            if (resetModel.isReset(name, sAns.getText())){
                isConnectedRes.setText("Answer is right");
                String p = rtusrModel.getPassword(name);
                newPassWord.setText(p);
                newPassWord.setVisible(true);
                newPassLabel.setVisible(true);
                sAns.setEditable(false);
                newPassWord.setEditable(false);
                resetpass.setVisible(false);
                goLogin.setVisible(true);
            }else{
                isConnectedRes.setText("Wrong answer try again!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void GoLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }
}
