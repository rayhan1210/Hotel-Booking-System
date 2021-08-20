package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.RegisterModel;
import main.model.RetrieveUserDetailModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddUser implements Initializable {
    public RegisterModel registerModel = new RegisterModel();
    RetrieveUserDetailModel rtv = new RetrieveUserDetailModel();
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
    private TextField roleAdm;
    @FXML
    private ComboBox SQ;
    @FXML
    private TextField SA;
    @FXML
    private TableView<TableBookingInfoForUsers> userTable;
    @FXML
    private TableColumn<TableBookingInfoForUsers, Integer> ID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> nameID,surnameID,userID,PW,Role,SecretQs,SecretAns, WhiteList;
    private ObservableList<TableBookingInfoForUsers> list;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        ObservableList<String> options = FXCollections.observableArrayList(
                "What is your favourite sport",
                "What is your favourite fruit");
        SQ.setItems(options);
        SQ.getSelectionModel().selectFirst();
        updateInfo();
        if (registerModel.isDbConnected()){
            isConnectedReg.setText("Connected");
        }else{
            isConnectedReg.setText("Not Connected");
        }

    }
    public void updateInfo(){
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameID.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameID.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userID.setCellValueFactory(new PropertyValueFactory<>("name"));
        PW.setCellValueFactory(new PropertyValueFactory<>("passWrd"));
        Role.setCellValueFactory(new PropertyValueFactory<>("role"));
        SecretQs.setCellValueFactory(new PropertyValueFactory<>("secretQs"));
        SecretAns.setCellValueFactory(new PropertyValueFactory<>("secretAns"));
        WhiteList.setCellValueFactory(new PropertyValueFactory<>("whiteList"));
        //ads a confirm button for each row that being updated.
        list = rtv.getAllRecords();
        userTable.setItems(list);
    }

    public void RegisterAdm(ActionEvent event) {
        String fname = name.getText();
        String lname = surname.getText();
        String username = user.getText();
        String password = pw.getText();
        String roleUser = roleAdm.getText();
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
                if (registerModel.isRegister(fname, lname, username, password, roleUser, secretQs,
                        secretAns, whiteList)) {
                    updateInfo();
                    clearValuesSuccessfulRegister();
                    isConnectedReg.setText("Registered successfully");
                } else {
                    isConnectedReg.setText("Username exists");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void clearValuesSuccessfulRegister(){
        name.clear();
        surname.clear();
        user.clear();
        pw.clear();
        roleAdm.clear();
        SA.clear();

    }
}
