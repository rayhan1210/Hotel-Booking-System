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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class AdmMangaeBookController implements Initializable {

    @FXML
    private TableView<TableBookingInfoForUsers> userTable;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> userID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> tableID;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> status;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> bookDate;
    @FXML
    private TableColumn<TableBookingInfoForUsers, String> confirmDate;
    @FXML
    private TableColumn confirm;
    @FXML
    private TableColumn reject;
    private ObservableList<TableBookingInfoForUsers> list;
    public BookingModel booking = new BookingModel();
    public UserTableViewModel userTableViewModel = new UserTableViewModel();
    UserHolder holder = UserHolder.getInstance();
    User ua = holder.getUser();
    String Pending = "Pending";
    String Accepted = "Accepted";
    String dflt = "";//dflt => default
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date today = new Date();
        String confirmedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(today);
        getInfo();
        //generates a confirm and reject button for each row
        confirm.setCellFactory(getAcceptButton(confirmedDate, today));
        reject.setCellFactory(getRejectButton(confirmedDate, today));
    }
    public void getInfo(){
        try {
            list = FXCollections.observableArrayList();
            userID.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableID.setCellValueFactory(new PropertyValueFactory<>("table"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            bookDate.setCellValueFactory(new PropertyValueFactory<>("dateOfBook"));
            confirmDate.setCellValueFactory(new PropertyValueFactory<>("confirmDate"));
            //ads a confirm button for each row that being updated.
            list = userTableViewModel.getAllRecords(Pending, Accepted, dflt, dflt, dflt, dflt,dflt);
            userTable.setItems(list);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> getAcceptButton(String confirmedDate, Date today){
        Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> cellFactory = (param) -> {
            final TableCell<TableBookingInfoForUsers,String> cell = new TableCell<TableBookingInfoForUsers, String >(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        //if row is empty then dont add a button.
                        setGraphic(null);
                    }else{
                        final Button confirm = new Button("Confirm");
                        TableBookingInfoForUsers u = getTableView().getItems().get(getIndex()); //get table row info
                        String stat = u.getStatus();
                        String ts_1 = LocalDate.now().toString();
                        String ts_2 = u.getDateOfBook();
                        conditionChecker(u.getStatus(), u, today, confirm,1);
                        confirm.setOnAction(event -> {
                            try {
                                if (stat.equals("Accepted") && u.getName().equals(ua.getName())
                                        && checkTimeFrameAccept(u.getConfirmDate(), today) == true) {
                                    booking.updateTableStat(u.getTable(), "Cancelled", confirmedDate);
                                }
                                if (u.getStatus().equals("Pending") && ts_1.equals(ts_2)) {
                                    booking.updateTableStat(u.getTable(), "Accepted", confirmedDate);
                                }
                            }catch (SQLException e){
                                e.printStackTrace();
                            }
                            getInfo();
                        });
                        setGraphic(confirm);
                    }
                    setText(null);
                }
            };
            return cell;
        };
        return cellFactory;
    }
    public Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> getRejectButton(String confirmedDate, Date today){
        Callback<TableColumn<TableBookingInfoForUsers, String>, TableCell<TableBookingInfoForUsers, String>> cellReject = (param) -> {
            final TableCell<TableBookingInfoForUsers,String> cellRej = new TableCell<TableBookingInfoForUsers, String >(){
                @Override
                public void updateItem(String item, boolean empty){
                super.updateItem(item,empty);
                if(empty){
                    //if row is empty then dont add a button.
                    setGraphic(null);
                }else{
                    final Button reject = new Button("Reject");
                    TableBookingInfoForUsers u = getTableView().getItems().get(getIndex());
                    conditionChecker(u.getStatus(), u, today, reject, 2);
                    reject.setOnAction(event -> {
                        String stats = u.getStatus();
                        try {
                            if (stats.equals("Accepted") && u.getName().equals(ua.getName())
                                    && checkTimeFrameAccept(u.getConfirmDate(), today) == true) {
                                booking.updateTableStat(u.getTable(), "CheckedIn", confirmedDate);
                            } else if (stats.equals("Pending") || stats.equals("Accepted")) {
                                booking.updateTableStat(u.getTable(), "Cancelled", confirmedDate);
                            } else {
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                        getInfo();
                    });
                    setGraphic(reject);
                }
                setText(null);
                }
            };
            return cellRej;
        };
        return cellReject;
    }

    public Boolean checkTimeFrameAccept(String date, Date today){
        try {
            if (date != null && !date.isEmpty()) {
                Date stringToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                long date1 = stringToDate.getTime() / 1000 / 60 / 60;
                long date2 = today.getTime() / 1000 / 60 / 60;
                if ((date2 - date1) > 48) {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void conditionChecker(String stat, TableBookingInfoForUsers u, Date today, Button c, int b){
        String a = b == 1 ? "Cancel" : "CheckIn";
        if(stat.equals("Accepted") && u.getName().equals(ua.getName())
                && checkTimeFrameAccept(u.getConfirmDate(), today) == false){
            c.setText(a);
            c.setDisable(true);
            c.setStyle("-fx-opacity: 1;");

        }else if(stat.equals("Accepted") && u.getName().equals(ua.getName())){
            c.setText(a);
        }else if(stat.equals("Accepted")){
            c.setText(a);
        }
    }

}