package main.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.controller.TableBookingInfoForUsers;

import java.sql.*;

public class RetrieveUserDetailModel {
    Connection connection;
    public RetrieveUserDetailModel(){
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
    public String getSecretQs(String username) throws SQLException {
        String sql = "select username, secretQs from Employee";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(username.equals(rs.getString("username"))) {
                    return rs.getString("secretQs");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }

    public String getPassword(String username) throws SQLException {
        String sql = "select username, password from Employee";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs   = stmt.executeQuery(sql);
            while(rs.next()) {
                if (username.equals(rs.getString("username"))) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }
    public String getRole(String username) throws SQLException {
        String sql = "select username, role from Employee";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs   = stmt.executeQuery(sql);
            while(rs.next()) {
                if (username.equals(rs.getString("username"))) {
//                    System.out.println("in getRole");
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }
    public String checkName(String username) throws SQLException{
        String sql = "select username, password from Employee";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()) {
                if(username.equals(rs.getString("username"))) {
                    return rs.getString("username");
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }

    public int getPrimaryKey(String a) throws SQLException {
        String sql = "select username, id from Employee";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(a.equals(rs.getString("username"))) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return 0;
    }
    public ObservableList<TableBookingInfoForUsers> getAllRecords(){
        String sql = "select id, name, surname, username, password, role, secretQs, secretAns, Whitelist from Employee";
        ResultSet rs = null;
        ObservableList<TableBookingInfoForUsers> userList = FXCollections.observableArrayList();;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                TableBookingInfoForUsers users = new TableBookingInfoForUsers();
                users.setId(rs.getInt("id"));
                users.setFirstName(rs.getString("name"));
                users.setLastName(rs.getString("surname"));
                users.setName(rs.getString("username"));
                users.setPassWrd(rs.getString("password"));
                users.setRole(rs.getString("role"));
                users.setSecretQs(rs.getString("secretQs"));
                users.setSecretAns(rs.getString("secretAns"));
                users.setWhiteList(rs.getString("Whitelist"));
                userList.add(users);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
    public ObservableList<TableBookingInfoForUsers> getSpecificRecords(String username){
        String sql = "select id, name, surname, username, password, role, secretQs, secretAns from Employee";
        ResultSet rs = null;
        ObservableList<TableBookingInfoForUsers> userList = FXCollections.observableArrayList();;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()){
                if(username.equals(rs.getString("username"))) {
                    TableBookingInfoForUsers users = new TableBookingInfoForUsers();
                    users.setId(rs.getInt("id"));
                    users.setFirstName(rs.getString("name"));
                    users.setLastName(rs.getString("surname"));
                    users.setName(rs.getString("username"));
                    users.setPassWrd(rs.getString("password"));
                    users.setRole(rs.getString("role"));
                    users.setSecretQs(rs.getString("secretQs"));
                    users.setSecretAns(rs.getString("secretAns"));
                    userList.add(users);
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
    public void updateUsers(String name,String surname,String username,String password,String role,
                            String secretAns, int id, String whiteList){
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "UPDATE Employee SET name=?, surname=?, username=?, password=?, role=?, secretAns=?, Whitelist=?" +
                "WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, role);
            preparedStatement.setString(6, secretAns);
            preparedStatement.setString(7, whiteList);
            preparedStatement.setInt(8, id);
            resultSet = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteUser(int id){
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "DELETE FROM Employee " +
                "WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getWhiteList(String user) throws SQLException {
        String sql = "select username, Whitelist from Employee";
        ResultSet rs = null;
        try {
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            while(rs.next()) {
                if(user.equals(rs.getString("username")) ){
                    return rs.getString("Whitelist");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return null;
    }
}
