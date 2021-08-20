package main.model;

import javafx.scene.control.ChoiceBox;
import main.SQLConnection;
import org.sqlite.SQLiteConnection;

import java.sql.*;

public class RegisterModel {
    Connection connection;
    public RegisterModel(){
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

    public Boolean isRegister(String name, String surname, String username, String password, String role,
                              String secretQs, String secretAns, String whiteList) throws SQLException {
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String query =
        "INSERT INTO employee (name, surname, username, password, role, secretQs, secretAns, Whitelist)" +
                " VALUES(?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, username);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, role);
                preparedStatement.setString(6, secretQs);
                preparedStatement.setString(7, secretAns);
            preparedStatement.setString(8, whiteList);
                resultSet = preparedStatement.executeUpdate();
            if (resultSet == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
        }
    }
}
