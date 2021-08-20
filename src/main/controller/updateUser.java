package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import main.model.RetrieveUserDetailModel;

import java.net.URL;
import java.util.ResourceBundle;


public class updateUser implements Initializable {

    @FXML
    private TableView<TableBookingInfoForUsers> userTable;
    @FXML
    private TableColumn<TableBookingInfoForUsers, Integer> IDA;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> nameID,surnameID,userID,PW,Role,SecretQs,SecretAns, WhiteList;
    @FXML
    private TableColumn update;
    @FXML
    private BorderPane bp;

    private ObservableList<TableBookingInfoForUsers> list;
    RetrieveUserDetailModel rtv = new RetrieveUserDetailModel();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();
        updateInfo();
        editUserInfo();
        update.setCellFactory(getUpdateButton());
    }
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
        Role.setCellFactory(TextFieldTableCell.forTableColumn());
        Role.setOnEditCommit(event -> {
            if(!event.getNewValue().equalsIgnoreCase("admin")
                    && !event.getNewValue().equalsIgnoreCase("employee")){
                System.out.println("ROle is either admin or employee");
            }else {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setRole(event.getNewValue());
            }
        });
        SecretAns.setCellFactory(TextFieldTableCell.forTableColumn());
        SecretAns.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSecretAns(event.getNewValue());
        });
        WhiteList.setCellFactory(TextFieldTableCell.forTableColumn());
        WhiteList.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setWhiteList(event.getNewValue());
        });
        userTable.setEditable(true);
    }

    public void updateInfo(){
        IDA.setCellValueFactory(new PropertyValueFactory<>("id"));
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
                                    u.getPassWrd(), u.getRole(), u.getSecretAns(), u.getId(),u.getWhiteList());
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
}
