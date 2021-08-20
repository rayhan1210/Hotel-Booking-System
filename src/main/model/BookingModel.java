package main.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.controller.TableBookingInfoForUsers;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class BookingModel {
    Connection connection;
    public BookingModel(){
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
    //using
    public Boolean addBooking(String username, String role, String bookDate, int tableIndexNo,
                     String tablePos, String status, String dateToday, String confirmDate, String LockdownDate) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO book(username, role, BookDate, TablePosIndex, TablePosition, Status," +
                "dateOfBookDay, ConfirmDate, EndLockdownDate) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        int reasultSetBook;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, role);
            preparedStatement.setString(3, bookDate);
            preparedStatement.setInt(4, tableIndexNo);
            preparedStatement.setString(5, tablePos);
            preparedStatement.setString(6, status);
            preparedStatement.setString(7, dateToday);
            preparedStatement.setString(8, confirmDate);
            preparedStatement.setString(9, LockdownDate);
            reasultSetBook = preparedStatement.executeUpdate();
            if(reasultSetBook >= 1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            preparedStatement.close();
        }
        return false;
    }
    public ArrayList<String> getTableInArray() throws SQLException {
        String sql = "select TablePosition, Status from Book";
        ResultSet rs = null;
        ArrayList<String> users = new ArrayList<>();
        try {
            Statement stmt  = connection.createStatement();
            rs   = stmt.executeQuery(sql);
            String stat;
            while(rs.next()) {
                String ac = "Accepted";
                stat = rs.getString("Status");
                if(ac.equals(stat) || ("Pending".equals(stat))) {
                    users.add(rs.getString("TablePosition"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return  users;
    }
    public ArrayList<String> getUserTables(String username) throws SQLException {
        String sql = "select username, TablePosition from Book";
        ResultSet rs = null;
        ArrayList<String> users = new ArrayList<String>();
        try {
            Statement stmt  = connection.createStatement();
            rs   = stmt.executeQuery(sql);
            while(rs.next()) {
                if (username.equals(rs.getString("username"))) {
                    users.add(rs.getString("TablePosition"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return users;
    }
    //for lockdown status finder.
    public ArrayList<String> getUserTablesByStat(String stat) throws SQLException {
        String sql = "select Status, TablePosition from Book";
        ResultSet rs = null;
        ArrayList<String> users = new ArrayList<String>();
        try {
            Statement stmt  = connection.createStatement();
            rs   = stmt.executeQuery(sql);
            while(rs.next()) {
                if (stat.equals(rs.getString("Status"))) {
                    users.add(rs.getString("TablePosition"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return users;
    }
    //so user can only book once
    public Boolean userBookOnce(String user) throws SQLException {
        String sql = "select username, Status from Book";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs   = stmt.executeQuery(sql);
            String stat = "";
            while(rs.next()) {
                if(user.equals(rs.getString("username")) &&
                        (rs.getString("Status").equals("Accepted") ||
                                rs.getString("Status").equals("Pending"))){
                       return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return false;
    }//allows user to get status by a table
    public String getStatus(String table) throws SQLException {
        String sql = "select TablePosition, Status from Book";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(table.equals(rs.getString("TablePosition"))) {
                    return rs.getString("Status");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }
    //retrieve the day the user booked the table, need for time frame calculation
    public String getDateOfBookDay(String table) throws SQLException {
        String sql = "select TablePosition, dateOfBookDay from Book";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(table.equals(rs.getString("TablePosition"))) {
//                    System.out.println(rs.getString("Status"));
                    return rs.getString("dateOfBookDay");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }
    //retrieve the date admin confirmed the booking, needed for time frame calcualltion
    public String getConfirmedDate(String table) throws SQLException {
        String sql = "select TablePosition, ConfirmDate from Book";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(table.equals(rs.getString("TablePosition"))) {
//                    System.out.println(rs.getString("Status"));
                    return rs.getString("ConfirmDate");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }
    //Update a table status by the table position
    public Boolean updateTableStat(String table, String stat, String confirmedDate) throws SQLException {
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "UPDATE Book SET Status = ? , ConfirmDate = ?"
                + "WHERE TablePosition = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, stat);
            preparedStatement.setString(2, confirmedDate);
            preparedStatement.setString(3, table);
            resultSet = preparedStatement.executeUpdate();
            if(resultSet == 1){
//                System.out.println("true");
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
        return false;
    }
    //retrieve a table pos which corresponds to the position in panel.getChildren().get(i).
    public int getTablePos(String table) throws SQLException {
        String sql = "select TablePosition, TablePosIndex from Book";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(table.equalsIgnoreCase(rs.getString("TablePosition"))) {
                    return rs.getInt("TablePosIndex");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return 0;
    }
    //Lockdown/Social distancing condition updater
    public Boolean updateSameUserCond(String bookDate, String table, String stat, String date, String cDate,
                                  String eDate, String user, String stat2, String stat3) throws SQLException {
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "UPDATE Book SET TablePosition = ?, Status=?, BookDate=?, dateOfBookDay=?," +
                "ConfirmDate=?, EndLockdownDate=? WHERE username =? AND Status =? OR Status =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(3, bookDate);
            preparedStatement.setString(1, table);
            preparedStatement.setString(2, stat);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, cDate);
            preparedStatement.setString(6, eDate);
            preparedStatement.setString(7, user);
            preparedStatement.setString(8, stat2);
            preparedStatement.setString(9, stat3);
            resultSet = preparedStatement.executeUpdate();
            if(resultSet >= 1){ //if it changes 1 row then resultset is 1 and if 2 then 2 and so on.
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
        return false;
    }
    public Boolean updateOtherAdminCond(String bookDate, String table, String stat, String date,
                                        String cDate, String eDate, int a, String stat2, String stat3) throws SQLException {
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "UPDATE Book SET TablePosition = ?, Status=?, BookDate=?, dateOfBookDay=?," +
                "ConfirmDate=?, EndLockdownDate=? WHERE TablePosIndex =? AND Status =? OR Status =?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(3, bookDate);
            preparedStatement.setString(1, table);
            preparedStatement.setString(2, stat);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, cDate);
            preparedStatement.setString(6, eDate);
            preparedStatement.setInt(7, a);
            preparedStatement.setString(8, stat2);
            preparedStatement.setString(9, stat3);
            resultSet = preparedStatement.executeUpdate();
            if(resultSet >= 1){ //if it changes 1 row then resultset is 1 and if 2 then 2 and so on.
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
        return false;
    }
    //Check if the tables are in whihc condition as they can only be one condition at a time
    // -> normal, lockdown, social distance, hence only need to look for either these 3 and if exist return true
    public Boolean checkStat(String stat) throws SQLException {
        String sql = "select Status from Book";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(stat.equalsIgnoreCase(rs.getString("Status"))) {
//                    System.out.println(rs.getString("Status"));
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return false;
    }
    //used for updating a row in table, given a admin  user and covid condition
    public Boolean checkName(String username, String stat) throws SQLException {
        String sql = "select username, Status from Book";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()) {
                if(username.equals(rs.getString("username")) &&
                        stat.equals(rs.getString("Status"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return false;
    }
    //used for updating all pending or accepted status booked by emp or admin when a covid condition is set.
    public Boolean updateStat(String stat, String stat2) throws SQLException {
        String sql = "UPDATE Book SET Status = ? WHERE Status = ?";
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, stat);
            preparedStatement.setString(2, stat2);
            resultSet = preparedStatement.executeUpdate();
            if(resultSet >= 1){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
        return false;
    }

}
