package main.model;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import main.SQLConnection;
import org.sqlite.SQLiteConnection;
import java.security.SecureRandom;
import java.sql.*;

public class ResetModel {
    Connection connection;
//    public RetrieveUserDetailModel rtusrModel = new RetrieveUserDetailModel();
    public ResetModel(){
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

    public static String generateRandomPassword(int len) {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
    public Boolean isReset(String name, String secretAns) throws SQLException{
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        String sql = "UPDATE Employee SET password = ? "
                + "WHERE username = ? and secretAns = ?";
        try {
            String newPass = generateRandomPassword(10);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, secretAns);
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
