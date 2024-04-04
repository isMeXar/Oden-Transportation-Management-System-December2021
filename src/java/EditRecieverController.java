package com.example.myapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class EditRecieverController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    public Button btnSave;
    @FXML
    public Button btnBack;
    @FXML
    public Button btnRefresh;
    @FXML
    public TextField idRecField;
    @FXML
    public TextField nomRecField;
    @FXML
    public TextField cinRecField;
    @FXML
    public TextField telRecField;
    @FXML
    public void SaveRecEdit(){
        String cin = cinRecField.getText();
        String nom = nomRecField.getText();
        String id = idRecField.getText();
        String tel = telRecField.getText();
        if(!cin.isBlank() && !nom.isBlank()&& !tel.isBlank()){
            try{
                Connection myConn = DB_Connection.getConn();
                String query = "Update receiver set nomRec=?,cinRec=?,telRec=?,telRec=? where idRec=?";
                PreparedStatement preState = myConn.prepareStatement(query);
                preState.setString(1,nom);
                preState.setString(2,cin);
                preState.setString(3,tel);
                preState.setString(4,id);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
