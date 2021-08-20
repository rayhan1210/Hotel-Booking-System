package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.BookingModel;
import main.model.UserTableViewModel;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class EmpManageBookController implements Initializable {

    @FXML
    private TableView<TableBookingInfoForUsers> userTable;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> userID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> tableID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> status;
    @FXML
    private TableColumn cancel;
    @FXML
    private TableColumn checkin;
    private ObservableList<TableBookingInfoForUsers> list;
    public BookingModel booking = new BookingModel();
    public UserTableViewModel userTableViewModel = new UserTableViewModel();
    UserHolder holder = UserHolder.getInstance();
    User ua = holder.getUser();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date cDateInDate2 = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cancelledDate = dateFormat.format(cDateInDate2);
        updateInfo();
        //generates a cancel and checkin button for each row.
        cancel.setCellFactory(getCancelButton(cancelledDate, cDateInDate2));
        checkin.setCellFactory(getRejectButton(cancelledDate));
    }

    public Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers,String>> getCancelButton(String cancelledDate, Date cDateInDate2){
        Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> cellFactory = (param) -> {
            final TableCell<TableBookingInfoForUsers,String> cell = new TableCell<TableBookingInfoForUsers, String >(){
                @Override
                public void updateItem(String item, boolean empty){
                super.updateItem(item,empty);
                if(empty){
                    //if row is empty then dont add a button.
                    setGraphic(null);
                }else{
                    final Button cancelBtn = new Button("Cancel");
                    TableBookingInfoForUsers u = getTableView().getItems().get(getIndex()); //get table row info
                    cancelBtn.setOnAction(event -> {
                        cancelButtonAction(u,cDateInDate2, cancelBtn, cancelledDate);
                        updateInfo();
                    });
                    setGraphic(cancelBtn);
                }
                setText(null);
                }
            };
            return cell;
        };
        return cellFactory;
    }
    public Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> getRejectButton(String cancelledDate){
        Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> cellCheckIn = (param) -> {
            final TableCell<TableBookingInfoForUsers,String> cellCin = new TableCell<TableBookingInfoForUsers, String >(){
                @Override
                public void updateItem(String item, boolean empty){
                super.updateItem(item,empty);
                if(empty){
                    //if row is empty then dont add a button.
                    setGraphic(null);
                }else{
                    final Button checkIn = new Button("Check-In");
                    TableBookingInfoForUsers u = getTableView().getItems().get(getIndex());
                    checkIn.setOnAction(event -> {
                        try {
                            String stats = booking.getStatus(u.getTable());
                            if(stats.equals("Accepted")){
                                booking.updateTableStat(u.getTable(), "CheckedIn", cancelledDate);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        updateInfo();
                    });
                    setGraphic(checkIn);
                }
                setText(null);
                }
            };
            return cellCin;
        };
        return cellCheckIn;
    }
    public void cancelButtonAction(TableBookingInfoForUsers u, Date cDateInDate2, Button cancelBtn, String cancelledDate){
        try {
            String stats = booking.getStatus(u.getTable());
            //get the confirmed date and get the current date convert it to millisecond
            //and the to hour and if its over 48hr user cant cancel or make changes.
            String cDate = booking.getConfirmedDate(u.getTable());
            if (cDate != null && !cDate.isEmpty() && stats.equals("Accepted")) {
                Date cDateInDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cDate);
                long a = cDateInDate.getTime() / 1000 / 60 / 60; //converting to hours
                long b = cDateInDate2.getTime() / 1000 / 60 / 60; //converting to hours
                if ((b - a) > 48) {
                    System.out.println(a);
                    System.out.println(b);
                    cancelBtn.setDisable(true);
                } else {
                    booking.updateTableStat(u.getTable(), "Cancelled", cancelledDate);
                }
            } else {
                cancelBtn.setDisable(true);
            }
        }catch (ParseException | SQLException e){
            e.printStackTrace();
        }
    }
    public void updateInfo(){
        try {
            list = FXCollections.observableArrayList();
            userID.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableID.setCellValueFactory(new PropertyValueFactory<>("table"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            list = userTableViewModel.getSpecificRecord(ua.getName());
            userTable.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}