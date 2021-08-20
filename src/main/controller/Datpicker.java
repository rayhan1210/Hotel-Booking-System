package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import main.model.BookingModel;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Datpicker implements Initializable {
    public BookingModel booking = new BookingModel();
    @FXML
    private DatePicker date;
    @FXML
    private Button book;

    UserHolder holder = UserHolder.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setValue(LocalDate.now());
        date.setDayCellFactory(picker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty); // to change body of generated method
                LocalDate d = LocalDate.now();
                setDisable(date.compareTo(d) < 0); //disable all the past dates.
            }
        });
    }

    public void DatePicker(ActionEvent event) {
//        LocalDate i = date.getValue();
//        long day = i.toEpochDay();
//        UserHolder holder = UserHolder.getInstance();
//        User u = new User();
//        u.setDate(day);
//        holder.setUser(u);
//        System.out.println(day);
    }
    public void Book(ActionEvent event){
        if(date.getValue() == null){
            System.out.println("Please choose a date");
        }else {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            String dateToday = formatter.format(today).toString();
            DateFormat confirmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date confirmDate = new Date();
            String cDate = confirmFormat.format(confirmDate);
            LocalDate l = date.getValue();
            long i = l.toEpochDay();
            String bookDate = l.toString();
            User u = holder.getUser();
            String user = u.getName();
            u.setDate(i);
            holder.setUser(u);
            String table = u.getTable();
            try {
                if(u.getRole().equals("admin")) {
                    booking.addBooking(user, u.getRole(), bookDate,
                            u.getTablePosIndex(), table, "Accepted", dateToday, cDate, "N/A");
                }else{
                    booking.addBooking(user, u.getRole(), bookDate, u.getTablePosIndex(),
                            table, "Pending", dateToday, "N/A", "N/A");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Stage stage = (Stage) book.getScene().getWindow();
            stage.close();
        }
    }
}
