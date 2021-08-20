package main.model;
import main.SQLConnection;

import java.sql.*;

public class EmployeeModel {
    Connection connection;
    public EmployeeModel(){
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

    public Boolean updateUser(String name, int id) throws SQLException{
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "UPDATE Employee SET username = ? "
                + "WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            resultSet = preparedStatement.executeUpdate();
            System.out.println("reasultset: " + resultSet);
            if(resultSet == 1){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
        return false;
    }
    public Boolean updatePw(String pw, int id) throws SQLException{
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "UPDATE Employee SET password = ? "
                + "WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pw);
            preparedStatement.setInt(2, id);
            resultSet = preparedStatement.executeUpdate();
            if(resultSet == 1){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
        return false;
    }
}
