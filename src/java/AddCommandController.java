package com.example.myapp;

import com.example.myapp.DB_Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddCommandController implements Initializable {
    @FXML
    public ComboBox<String> cbCountry;
    @FXML
    public TextField cityField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField weightField;
    @FXML
    public TextField cinClientField;
    @FXML
    public TextField nomClientField;
    @FXML
    public TextField telClientField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField cinRecField;
    @FXML
    public TextField nomRecField;
    @FXML
    public TextField telRecField;
    @FXML
    public Button btnBack;
    @FXML
    public Button btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillCombo();
    }

    @FXML
    private void FillCombo() {
        try {
            Connection myConn = DB_Connection.getConn();
            ResultSet rs = myConn.createStatement().executeQuery("Select country from countryprice");
            while (rs.next()) {
                cbCountry.getItems().addAll(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void excuteQuery(String query) {
        Statement st;
        try {
            Connection myConn = DB_Connection.getConn();
            st = myConn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void SaveRec() {
        if (telRecField.getText().isBlank() && nomRecField.getText().isBlank() && cinRecField.getText().isBlank() && emailField.getText().isBlank() && telClientField.getText().isBlank() && cinClientField.getText().isBlank() && nomClientField.getText().isBlank() && cityField.getText().isBlank() && weightField.getText().isBlank() && addressField.getText().isBlank() && cbCountry.getValue()==null) {
            System.out.println("All textFields requires information !");
        } else {
            String query1 ="insert IGNORE into client(cinClient,nomClient,telClient,emailClient) values('"+cinClientField.getText()+"','"+nomClientField.getText()+"','"+telClientField.getText()+"','"+emailField.getText()+"')";
            String query2 ="insert IGNORE into receiver(cinRec,nomRec,telRec) values('"+ cinRecField.getText() +"','"+ nomRecField.getText() +"','"+ telRecField.getText() +"')";
            String query3="insert IGNORE into command(idClient,idRec,idCountry,City,Address,QteCommand,Price,Status) values ((select idClient from client where cinClient='"+cinClientField.getText()+"'),(select idRec from receiver where cinRec='"+cinRecField.getText()+"'),(select idCountry from countryprice where Country ='"+cbCountry.getValue()+"'),'"+cityField.getText()+"','"+addressField.getText()+"',"+weightField.getText()+",(select PricePerKilo*'"+weightField.getText()+"' from countryprice where Country='"+cbCountry.getValue()+"'),'in progress..');";
            excuteQuery(query1);
            excuteQuery(query2);
            excuteQuery(query3);
        }
    }

    @FXML
    public void clearText(){
        cityField.clear();
        addressField.clear();
        weightField.clear();
        cinClientField.clear();
        nomClientField.clear();
        telClientField.clear();
        emailField.clear();
        cinRecField.clear();
        nomRecField.clear();
        telRecField.clear();
        cbCountry.setValue(null);
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnSave) {
            SaveRec();
            clearText();
        }else if (event.getSource() == btnBack) {
            Stage stage1 = (Stage) btnBack.getScene().getWindow();
            stage1.hide();
        }
    }






}
