package main.model;

import javafx.scene.control.ChoiceBox;
import main.SQLConnection;
import org.sqlite.SQLiteConnection;

import java.sql.*;

public class ForgottenPassModel {
    Connection connection;
    public ForgottenPassModel(){
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
    public boolean isUser(String username) throws SQLException{
        String sql = "select username, secretQs from Employee";
        System.out.println("in getSecretQs");
        ResultSet rs = null;
        try{
            Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(sql);
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
        return false;
    }

}
