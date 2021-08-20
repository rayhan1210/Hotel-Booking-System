package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.model.BookingModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserBookController implements Initializable {
    public BookingModel booking = new BookingModel();
    @FXML
    private Label status;
    @FXML
    private Button covidCondition;
    @FXML
    private Button covidLockdown;
    @FXML
    private Pane panel;
    @FXML
    private BorderPane bp;
    @FXML
    private Button normCond;

    UserHolder holder = UserHolder.getInstance();
    final String Normal = "Normal";
    final String Cancel = "Cancelled Due to Lockdown";
    final String Lockdown = "Lockdown";
    final String SocialDistance = "Social Distance";
    final String NA = "N/A";
    int tableIndex = -1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User u = holder.getUser();
        if(u.getRole().equals("employee")){
            covidCondition.setVisible(false);
            covidLockdown.setVisible(false);
            normCond.setVisible(false);
        }
        try {
            LocalDate today = LocalDate.now();
            String currentDate = today.toString();
            if(booking.checkStat(SocialDistance)){
                lockdownStat(booking.getUserTablesByStat(SocialDistance).get(0));
            }else if(booking.checkStat(Lockdown)){
                lockdownStat(booking.getUserTablesByStat(Lockdown).get(0));
            }else{
                //System.out.println("nothing");
            }
            doThis(booking.getTableInArray(), u.getRole(), currentDate);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void BookTable(ActionEvent event) throws IOException, SQLException {
        final Node srce = (Node) event.getSource();
        User u = holder.getUser();
        String id = srce.getId();
        String user = u.getName();
        u.setTable(id);
        int pos = getTablePos(id);
        u.setTablePosIndex(pos);
        //Checks if whitelist is yes and checks for the same user if they booked
        //that same table before or not and if so then they cant book the same table again.
        if(whiteList(user, id) && u.getWhiteList().equalsIgnoreCase("Yes")){
            status.setText("please choose a different table");
        }else if(booking.userBookOnce(user)){//make sure each user can only book once, until thet book has been
            // accepted, or cancelled
            status.setText("you have already booked " + user);
        }else {
            bookPopUpWindow();
            reupadateScene();//for updated table view.
        }

    }
    //getting all the table for covid condition/lockdown/normal.
    public ArrayList<String> addCOVID(String stat){
        ArrayList<String> users = new ArrayList<String>();
        int counter = stat.equals("Social Distance") ? 2 : 1;
        int start = stat.equals("Social Distance") ? 1 : 0;
        for(int i = start; i < panel.getChildren().size(); i += counter){ //skip the first table
            //Only need the specific tableids and rest not important for booking.
            if(!panel.getChildren().get(i).getId().equals("info") &&
                    !panel.getChildren().get(i).getId().equals("booked") &&
                    !panel.getChildren().get(i).getId().equals("pending") &&
                    !panel.getChildren().get(i).getId().equals("available") &&
                    !panel.getChildren().get(i).getId().equals("label1") &&
                    !panel.getChildren().get(i).getId().equals("label2") &&
                    !panel.getChildren().get(i).getId().equals("label3") &&
                    !panel.getChildren().get(i).getId().equals("covidCondition") &&
                    !panel.getChildren().get(i).getId().equals("covidLockdown") &&
                    !panel.getChildren().get(i).getId().equals("normCond") &&
                    !panel.getChildren().get(i).getId().equals("status")) {
                users.add(panel.getChildren().get(i).getId());
            }
        }
        return users;
    }
    public void covidCond(ActionEvent event) throws SQLException, IOException {
        User u = holder.getUser();
        LocalDate date = LocalDate.now();
        String setTextForLabel = "Already in Social Distance";
        String covidStat = "Cancelled Due to Social Distancing";
        setConditionForCOVID(SocialDistance, u, date,setTextForLabel,covidStat,Lockdown,Normal);
    }
    public void covidLock(ActionEvent event) throws SQLException, IOException {
        User u = holder.getUser();
        LocalDate date = LocalDate.now();
        String setTextForLabel = "Already in Lockdown";
        String covidStat = "Cancelled Due to Lockdown";
        setConditionForCOVID(Lockdown, u, date,setTextForLabel,covidStat,SocialDistance,Normal);
    }

    public void normCond(ActionEvent event) throws IOException, SQLException {
        User u = holder.getUser();
        LocalDate date = LocalDate.now();
        if(booking.checkStat(Normal)){
            status.setText("Already in Normal");
        }else if((booking.checkStat(SocialDistance) || booking.checkStat(Lockdown)) &&
                (booking.checkName(u.getName(),SocialDistance) || booking.checkName(u.getName(),Lockdown))){
            booking.updateSameUserCond(date.toString(), addString(addCOVID(Normal)), Normal, date.toString(),
                    NA, NA, u.getName(),SocialDistance,Lockdown);
            reupadateScene(); //updating the scene to see updated table view.
        } else{
            booking.updateOtherAdminCond(date.toString(), addString(addCOVID(Normal)),Cancel,
                    date.toString(), NA, NA, tableIndex,SocialDistance,Lockdown);
            booking.addBooking(u.getName(), u.getRole(), date.toString(), -1,
                    addString(addCOVID(Normal)), Normal, NA, NA, NA);
            reupadateScene();//updating the scene to see updated table view.
        }
    }

    //A technique to check the previous tables that a user have booked
    public Boolean whiteList(String username, String table) throws SQLException {
        int length = booking.getUserTables(username).size();
//        System.out.println("length: " + length);
        for(int i = 0; i < length; i++){
            if(booking.getUserTables(username).get(i).equals(table)){
//                System.out.println("true: " + panel.getChildren().get(i));
                return true;
            }
        }
        return false;
    }
    public void bookPopUpWindow() throws IOException {
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Booking");
        Parent root = FXMLLoader.load(getClass().getResource("../ui/datpicker.fxml"));
        popupwindow.setScene(new Scene(root));
        popupwindow.showAndWait();
    }
    //Gets the position of the table
    public int getTablePos(String table){
        for(int i = 0; i < panel.getChildren().size(); i++){
            if(panel.getChildren().get(i).getId().equals(table)){
                return i;
            }
        }
        return 0;
    }
    //Used for checking and updating table condition for admin and employee
    public void doThis(ArrayList<String> arrayList, String user, String currentDate) throws SQLException {
        for (String tble : arrayList) {
            int tablePos = booking.getTablePos(tble);
            String stats = booking.getStatus(tble);
            //System.out.println("tablePOs: " + tablePos + " : " + stats);
            if ("Pending".equals(stats) && user.equalsIgnoreCase("Admin")
                    && !booking.getDateOfBookDay(tble).equalsIgnoreCase(currentDate)) {
                booking.updateTableStat(panel.getChildren().get(tablePos).getId(), "Cancelled", currentDate);
            }
            if ("Accepted".equals(stats)) {
                panel.getChildren().get(tablePos).setDisable(true);
                panel.getChildren().get(tablePos).setStyle("-fx-background-color: #2477e4; -fx-opacity: 1;");
            }else {
                panel.getChildren().get(tablePos).setDisable(true);
                panel.getChildren().get(tablePos).setStyle("-fx-background-color: #fc0202; -fx-opacity: 1;");
            }
        }
    }

    public void lockdownStat(String a){
//        String[] c = addString(addCOVID("Normal"));
        String[] b = a.split(",");
        for (int i = 0; i < b.length; i++) {
            int pos = getTablePos(b[i]);
            if(b[i].equals(panel.getChildren().get(pos).getId())){
                panel.getChildren().get(pos).setDisable(true);
                panel.getChildren().get(pos).setStyle("-fx-background-color: #ff6f00; -fx-opacity: 1;");
            }
        }
    }
    //method for choosing the lockdown/social distance condition
    public void setConditionForCOVID(String stat, User u, LocalDate date, String setText,
                                     String covidStat,String covidCond2, String covidCond3) throws SQLException, IOException {
        if(booking.checkStat(stat)){
            status.setText(setText);
        }else if((booking.checkName(u.getName(),covidCond2)|| booking.checkName(u.getName(),covidCond3))
                && (booking.checkName(u.getName(),covidCond2) || booking.checkName(u.getName(),covidCond3))){
            booking.updateSameUserCond(date.toString(), addString(addCOVID(stat)), stat,
                    date.toString(), NA, NA, u.getName(),covidCond2,covidCond3);
            booking.updateStat(covidStat, "Pending");
            booking.updateStat(covidStat, "Accepted");
            reupadateScene();
        }
        else{
            booking.updateOtherAdminCond(date.toString(), addString(addCOVID(stat)), Cancel,
                    date.toString(), NA, NA, tableIndex,covidCond2,covidCond3);
            booking.addBooking(u.getName(), u.getRole(), date.toString(), -1,
                    addString(addCOVID(stat)), stat, date.toString(), NA, NA);
            booking.updateStat(covidStat, "Pending");
            booking.updateStat(covidStat, "Accepted");
            reupadateScene();
        }

    }
    //converts the string arraylist to string using string.join()
    public String addString(ArrayList<String> a){
        String list = String.join(",", a);
        return list;
    }
    public void reupadateScene() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("../ui/emp_admin_Book.fxml"));
        bp.setCenter(fxml);
    }
}
