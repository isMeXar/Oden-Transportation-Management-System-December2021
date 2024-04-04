package com.example.myapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button exitBtn;
    @FXML
    public Pane forgotPane;
    @FXML
    public Button forgotPassBtn;
    @FXML
    public Button submitBtn;
    @FXML
    public ImageView img;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        forgotPane.setVisible(false);
    }
    @FXML
    public void submit(){
        forgotPane.setVisible(false);
        errorText.setStyle("-fx-text-fill: #0b83f3");
        errorText.setText("Check your email!");
    }
    @FXML
    public void forgotPassword(){
        forgotPane.setVisible(true);
        errorText.setText("");
    }
    @FXML
    public void CloseButtonAction() {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public Button minimizeBtn;
    @FXML
    private void minimizeBut() {
        Stage stage=(Stage) minimizeBtn.getScene().getWindow();
        stage.setIconified(true);
    }
    public static String username;
    Connection myConn;
    PreparedStatement preState;
    ResultSet stResult;
    @FXML
    private Label errorText;
    @FXML
    public Button connectBtn;
    @FXML
    public void LoginButOnAction(ActionEvent event){
        errorText.setStyle("-fx-text-fill: #f51212");
        username = usernameField.getText();
        String password = passwordField.getText();
        if(!username.isBlank() && !password.isBlank()){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_javaprojectapp","root","2507");
                String sqlCommand = "select * from admin where aName= ? and aPassword= ?";
                preState = myConn.prepareStatement(sqlCommand);
                preState.setString(1,username);
                preState.setString(2,password);
                stResult = preState.executeQuery();
                if(!stResult.next()){
                    errorText.setText("Username or password is incorrect! Try again");
                    usernameField.setText("");
                    passwordField.setText("");
                }else{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainStage.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage2 = new Stage();
                    stage2.initStyle(StageStyle.UNDECORATED);
                    stage2.setScene(new Scene(root));
                    stage2.show();
                    Stage stage1 = (Stage) exitBtn.getScene().getWindow();
                    stage1.hide();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }else{
            errorText.setText("Enter username and password!");
        }
    }
    @FXML
    public static String getName(){
        return username;
    }



}