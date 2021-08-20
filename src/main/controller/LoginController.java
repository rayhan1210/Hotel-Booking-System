package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.model.LoginModel;
import main.model.RetrieveUserDetailModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    public RetrieveUserDetailModel rtrvUserModel = new RetrieveUserDetailModel();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    Button signUpBtn, loginBtn;

    UserHolder holder = UserHolder.getInstance();
    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }

    }

    /**
     *register scene
     */
    public void SignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ui/register.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }
    public void ForgotPW(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ui/forgottenPass.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }
    /* login Action method
       check if user input is the same as database.
     */
    double x, y;
    public void Login(ActionEvent event) throws SQLException {
        User u = new User();
        String a = txtUsername.getText();
        String whiteList = rtrvUserModel.getWhiteList(a);
        u.setWhiteList(whiteList);
        try {
            String b = rtrvUserModel.getRole(a);
            u.setRole(b);
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){
                u.setName(a);
                holder.setUser(u);
                if("employee".equals(rtrvUserModel.getRole(txtUsername.getText()))){
                    Parent root = FXMLLoader.load(getClass().getResource("../ui/employee.fxml"));
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(new Scene(root));
                    window.show();
                }else if("admin".equals(rtrvUserModel.getRole(txtUsername.getText()))){
                    Parent root = FXMLLoader.load(getClass().getResource("../ui/admin.fxml"));
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(new Scene(root));
                    window.show();
                }else{
                    System.out.println("Error logging in");
                }
                isConnected.setText("Logged in successfully");
            }else{
                isConnected.setText("username and password is incorrect");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
