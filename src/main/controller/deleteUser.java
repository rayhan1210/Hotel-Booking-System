package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.RetrieveUserDetailModel;

import java.net.URL;
import java.util.ResourceBundle;

public class deleteUser implements Initializable {

    @FXML
    private TableView<TableBookingInfoForUsers> userTable;
    @FXML
    private TableColumn<TableBookingInfoForUsers, Integer> ID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> nameID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> surnameID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> userID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> PW;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> Role;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> SecretQs;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> SecretAns;
    @FXML
    private TableColumn delete;
    private ObservableList<TableBookingInfoForUsers> list;
    RetrieveUserDetailModel rtv = new RetrieveUserDetailModel();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();
        updateInfo();
        delete.setCellFactory(getDeleteButton());
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

        //ads a confirm button for each row that being updated.
        list = rtv.getAllRecords();
        userTable.setItems(list);
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
