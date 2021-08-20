package main.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import main.model.RegisterModel;
import main.model.RetrieveUserDetailModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EmpManageAccController implements Initializable {
    public RegisterModel registerModel = new RegisterModel();
    RetrieveUserDetailModel rtv = new RetrieveUserDetailModel();
//    @FXML
//    private Label isConnectedReg;

    @FXML
    private TableView<TableBookingInfoForUsers> userTable;
    @FXML
    private TableColumn<TableBookingInfoForUsers, Integer> IDe;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> nameID,surnameID,userID,PW,Role,SecretQs,SecretAns;
    @FXML
    private TableColumn update;
    @FXML
    private TableColumn delete;
    private ObservableList<TableBookingInfoForUsers> list;
    UserHolder holder = UserHolder.getInstance();
    User ua = holder.getUser();
    @Override
    public void initialize(URL location, ResourceBundle resources){
        updateInfo();
        editUserInfo();
        //generates a update and delete button from each row.
        update.setCellFactory(getUpdateButton());
        delete.setCellFactory(getDeleteButton());
    }
    //User for editing table column information, choose a row, double click on the column make changes,
    //press enter and then press update button to update.
    public void editUserInfo(){
        nameID.setCellFactory(TextFieldTableCell.forTableColumn());
        nameID.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFirstName(event.getNewValue());
        });
        surnameID.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameID.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLastName(event.getNewValue());
        });
        userID.setCellFactory(TextFieldTableCell.forTableColumn());
        userID.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
        });
        PW.setCellFactory(TextFieldTableCell.forTableColumn());
        PW.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassWrd(event.getNewValue());
        });
        SecretAns.setCellFactory(TextFieldTableCell.forTableColumn());
        SecretAns.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSecretAns(event.getNewValue());
        });
        userTable.setEditable(true);
    }

    public void updateInfo(){
        IDe.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameID.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameID.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userID.setCellValueFactory(new PropertyValueFactory<>("name"));
        PW.setCellValueFactory(new PropertyValueFactory<>("passWrd"));
        Role.setCellValueFactory(new PropertyValueFactory<>("role"));
        SecretQs.setCellValueFactory(new PropertyValueFactory<>("secretQs"));
        SecretAns.setCellValueFactory(new PropertyValueFactory<>("secretAns"));
        list = rtv.getSpecificRecords(ua.getName());
        userTable.setItems(list);
    }
    public Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> getUpdateButton(){
        Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> cellFactory = (param) -> {
            final TableCell<TableBookingInfoForUsers,String> cell = new TableCell<TableBookingInfoForUsers, String >(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        //if row is empty then dont add a button.
                        setGraphic(null);
                    }else{
                        Button update = new Button("Update");
                        TableBookingInfoForUsers u = getTableView().getItems().get(getIndex()); //get table row info
                        update.setOnAction(event -> {
                            rtv.updateUsers(u.getFirstName(), u.getLastName(), u.getName(),
                                    u.getPassWrd(), u.getRole(), u.getSecretAns(), u.getId(),"Yes");
                            updateInfo();
                        });
                        setGraphic(update);
                    }
                    setText(null);
                }
            };
            return cell;
        };
        return cellFactory;
    }
    public Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> getDeleteButton(){
        Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> cellFactory = (param) -> {
            final TableCell<TableBookingInfoForUsers,String> cell = new TableCell<TableBookingInfoForUsers, String >(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        //if row is empty then dont add a button.
                        setGraphic(null);
                    }else{
                        Button delete = new Button("Delete");
                        TableBookingInfoForUsers u = getTableView().getItems().get(getIndex()); //get table row info
                        delete.setOnAction(event -> {
                            rtv.deleteUser(u.getId());
                            updateInfo();
                        });
                        setGraphic(delete);
                    }
                    setText(null);
                }
            };
            return cell;
        };
        return cellFactory;
    }
}
