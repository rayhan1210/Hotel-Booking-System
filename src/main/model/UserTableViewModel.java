package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.controller.TableBookingInfoForUsers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTableViewModel {
    Connection connection;
    public UserTableViewModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }
    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }
    //used for retrieving all the record from book given certain status'
    public ObservableList<TableBookingInfoForUsers> getAllRecords(String stat, String stat2, String stat3, String stat4,
                                                                  String stat5, String stat6, String stat7) throws SQLException {
        String sql = "select username, TablePosition, Status, dateOfBookDay, ConfirmDate from Book";
        ResultSet rs = null;
        ObservableList<TableBookingInfoForUsers> userList = FXCollections.observableArrayList();;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(stat.equalsIgnoreCase(rs.getString("Status")) || stat2.equalsIgnoreCase(rs.getString("Status"))
                        || stat3.equalsIgnoreCase(rs.getString("Status")) || stat4.equalsIgnoreCase(rs.getString("Status"))
                        || stat5.equalsIgnoreCase(rs.getString("Status"))|| stat6.equalsIgnoreCase(rs.getString("Status"))
                        || stat7.equalsIgnoreCase(rs.getString("Status"))) {
                    TableBookingInfoForUsers users = new TableBookingInfoForUsers();
                    users.setName(rs.getString("username"));
                    users.setTable(rs.getString("TablePosition"));
                    users.setStatus(rs.getString("Status"));
                    users.setDateOfBook(rs.getString("dateOfBookDay"));
                    users.setConfirmDate(rs.getString("ConfirmDate"));
                    userList.add(users);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return userList;
    }
    //used for retrieving  the record from book given certain user, used for employee
    public ObservableList<TableBookingInfoForUsers> getSpecificRecord(String username) throws SQLException {
        String sql = "select username, TablePosition, Status from Book";
        ResultSet rs = null;
        ObservableList<TableBookingInfoForUsers> userList = FXCollections.observableArrayList();;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(username.equalsIgnoreCase(rs.getString("username"))) {
                    TableBookingInfoForUsers users = new TableBookingInfoForUsers();
                    users.setName(rs.getString("username"));
                    users.setTable(rs.getString("TablePosition"));
                    users.setStatus(rs.getString("Status"));
                    userList.add(users);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return userList;
    }
}
