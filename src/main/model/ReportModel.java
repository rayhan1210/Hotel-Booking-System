package main.model;
import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.*;

public class ReportModel{
    Connection connection;
    public ReportModel(){
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

    public void getBookReport(String stat, String stat2,String stat3, String stat4, String file) throws SQLException {
        String filename = file;
        String sql = "select id, username, role, TablePosition, Status, BookDate," +
                " dateOfBookDay, ConfirmDate, EndLockdownDate from Book";
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            //new FileWriter(filename)
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));
            writer.write("id, username, role, TablePosition, Status, BookDate," +
                    " dateOfBookDay, ConfirmDate");
            while (rs.next()) {
                if(stat.equals(rs.getString("Status"))
                        || stat2.equals(rs.getString("Status")) ||
                        stat3.equals(rs.getString("Status"))
                        || stat4.equals(rs.getString("Status"))) {
                    String cDate = rs.getString("ConfirmDate");
                    if (cDate == null) {
                        cDate = "";
                    }
                    String line = String.format("\"%d\",%s,%s,%s,%s,%s,%s,%s",
                            rs.getInt("id"), rs.getString("username"),
                            rs.getString("role"), rs.getString("TablePosition"),
                            rs.getString("Status"), rs.getString("BookDate"),
                            rs.getString("dateOfBookDay"), cDate);
                    writer.newLine();
                    writer.write(line);
                }
            }
            writer.close();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
    }

    public void getEmpReport(String role, String file) throws SQLException {
        String filename = file;
        String sql = "select id, name, surname, username, password, " +
                "role, secretQs, secretAns from Employee";
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            //new FileWriter(filename)
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));
            writer.write("id, name, surname, username, password, role," +
                    " secretQs, secretAns");
            while (rs.next()) {
                if(role.equals(rs.getString("role"))) {
                    String line = String.format("\"%d\",%s,%s,%s,%s,%s,%s,%s",
                            rs.getInt("id"), rs.getString("name"),
                            rs.getString("surname"), rs.getString("username"),
                            rs.getString("password"), rs.getString("role"),
                            rs.getString("secretQs"), rs.getString("secretAns"));
                    writer.newLine();
                    writer.write(line);
                }
            }
            writer.close();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }finally {
            rs.close();
        }
    }
}