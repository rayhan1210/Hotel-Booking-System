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
import javafx.stage.Stage;
import main.model.RegisterModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    public RegisterModel registerModel = new RegisterModel();
    @FXML
    private Label isConnectedReg;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField user;
    @FXML
    private TextField pw;
    @FXML
    private TextField role;
    @FXML
//    private TextField SQ;
    private ComboBox SQ;
    @FXML
    private TextField SA;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
            ObservableList<String> options = FXCollections.observableArrayList(
                    "What is your favourite sport",
                    "What is your favourite fruit");
        SQ.setItems(options);
        SQ.getSelectionModel().selectFirst();
        if (registerModel.isDbConnected()){
            isConnectedReg.setText("Connected");
        }else{
            isConnectedReg.setText("Not Connected");
        }

    }

    public void Register(ActionEvent event) {
//        System.out.println("in REGISTER");
        String fname = name.getText();
        String lname = surname.getText();
        String username = user.getText();
        String password = pw.getText();
        String roleUser = role.getText();
        String secretQs = (String) SQ.getValue();
        String secretAns = SA.getText();
        String whiteList = "Yes";
        if (fname.equals("") || lname.equals("") || username.equals("") || roleUser.equals("")
                || password.equals("") || secretQs.equals("")) {
            isConnectedReg.setText("Invalid input, please fill in the required inputs");
        } else if (password.length() < 8) {
            isConnectedReg.setText("password should be longer then 8 word.");
        } else if (!roleUser.equalsIgnoreCase("admin") &&
                !roleUser.equalsIgnoreCase("employee")){
            isConnectedReg.setText("role should be either admin or employee");
        }else{
            try {
                if (registerModel.isRegister(fname, lname, username, password, roleUser, secretQs, secretAns,whiteList)) {
                    clearText();
                    isConnectedReg.setText("Registered successfully");
                } else {
                    isConnectedReg.setText("Username exists");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void Login(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }
    public void clearText(){
        name.clear();
        surname.clear();
        user.clear();
        pw.clear();
        role.clear();
        SA.clear();
    }
}
