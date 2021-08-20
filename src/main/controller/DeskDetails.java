package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.BookingModel;
import main.model.UserTableViewModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeskDetails implements Initializable {

    @FXML
    private TableView<TableBookingInfoForUsers> userTableview;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> userIDview;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> tableIDview;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> statusview;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> bookDateview;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> confirmDateview;
    @FXML
    private ObservableList<TableBookingInfoForUsers> listView;
    public UserTableViewModel userTableViewModel = new UserTableViewModel();

    String[] stats = {"Pending", "Accepted", "Cancelled", "CheckedIn"};
    String dflt = ""; //dflt=>default
//    UserHolder holder = UserHolder.getInstance();
//    User ua = holder.getUser();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Date today = new Date();
//        String confirmedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(today);
//        System.out.println("confDate: " + confirmedDate);
        getInfo();

    }
    public void getInfo(){
        try {
            listView = FXCollections.observableArrayList();
            userIDview.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableIDview.setCellValueFactory(new PropertyValueFactory<>("table"));
            statusview.setCellValueFactory(new PropertyValueFactory<>("status"));
            bookDateview.setCellValueFactory(new PropertyValueFactory<>("dateOfBook"));
            confirmDateview.setCellValueFactory(new PropertyValueFactory<>("confirmDate"));
            listView = userTableViewModel.getAllRecords(stats[0], stats[1], stats[2], dflt,dflt,dflt,stats[3]);
            userTableview.setItems(listView);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}