package com.example.myapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    public Label aFullName;
    @FXML
    public Label aUserName;
    @FXML
    public Label aEmail;
    @FXML
    public ImageView aImage;
    @FXML
    public TextField passField;
    @FXML
    public TextField confirmPassField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField confirmEmailField;
    @FXML
    public Button UpdateImg;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnClear;
    @FXML
    public Label idField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAdminInfo();
        urlim.setVisible(false);
        idField.setVisible(false);
    }
    String name = LoginController.getName();
    @FXML
    public void getAdminInfo(){
        try{
            Connection myConn = DB_Connection.getConn();
            String query="select * from admin where aName='"+name+"';";
            Statement stmt = myConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                int id = rs.getInt("idAdmin");
                String username = rs.getString("aName");
                String fullName = rs.getString("fullName");
                String email = rs.getString("aEmail");
                String image = rs.getString("imageUrl");
                idField.setText(Integer.toString(id));
                aUserName.setText(username);
                aFullName.setText(fullName);
                aEmail.setText(email);
                aImage.setImage(new Image(""+image+""));
            }
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
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
    public TextField newUsername;
    @FXML
    public TextField newFullName;
    @FXML
    public void updateRec() {
        //--------------------------------------Update admin's password-----------------------------------------------------------------
        if (!passField.getText().isBlank() && !confirmPassField.getText().isBlank()) {
            if(passField.getText().equals(confirmPassField.getText())){
                String query = "update admin set aPassword= '" + passField.getText() + "' where idAdmin = "+idField.getText()+";";
                excuteQuery(query);
                errorText.setStyle("-fx-text-fill: #0b83f3");
                errorText.setText("Your changes are committed successfully!");
            }else{
                errorText.setStyle("-fx-text-fill: red");
                errorText.setText("Unmatched passwords!");
            }
        }//--------------------------------------Update admin's email--------------------------------------------------------------------
        if (!emailField.getText().isBlank() && !confirmEmailField.getText().isBlank()) {
            if(emailField.getText().equals(confirmEmailField.getText())){
                String query = "update admin set aEmail= '" + emailField.getText() + "' where idAdmin = "+idField.getText()+";";
                excuteQuery(query);
                errorText.setStyle("-fx-text-fill: #0b83f3");
                errorText.setText("Your changes are committed successfully!");
            }else{
                errorText.setStyle("-fx-text-fill: red");
                errorText.setText("Unmatched email address!");
            }
        }
        if (!newUsername.getText().isBlank()) {
             String query = "update admin set aName= '" + newUsername.getText() + "' where idAdmin = "+idField.getText()+";";
             excuteQuery(query);
             errorText.setStyle("-fx-text-fill: #0b83f3");
             errorText.setText("Your changes are committed successfully!");
             name=newUsername.getText();
        }
        if (!newFullName.getText().isBlank()) {
            String query = "update admin set fullName= '" + newFullName.getText() + "' where idAdmin = "+idField.getText()+";";
            excuteQuery(query);
            errorText.setStyle("-fx-text-fill: #0b83f3");
            errorText.setText("Your changes are committed successfully!");
        }

        getAdminInfo();

    }
    @FXML
    public Label errorText;
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnSave) {
            updateRec();
            UpdateProfile();
            getAdminInfo();
            refresh();

        } else if (event.getSource() == btnClear) {
            refresh();
        }
    }
    @FXML
    public void refresh(){
        passField.clear();
        confirmPassField.clear();
        emailField.clear();
        confirmEmailField.clear();
        errorText.setText("");
        newUsername.clear();
        newFullName.clear();
        urlim.clear();
    }
    @FXML
    public TextField urlim;
    @FXML
    public void loadImage(ActionEvent t) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            aImage.setImage(image);
            String imageUrl= file.getAbsolutePath();
            urlim.setText(imageUrl);
        }
    }
    @FXML
    public void UpdateProfile(){
        if(!urlim.getText().isBlank()){
            String image = urlim.getText();
            image = image.replace("\\","\\\\");
            String query = "update admin set imageUrl='"+ image +"' where idAdmin = "+idField.getText()+";";
            excuteQuery(query);
            aImage.setImage(new Image(""+image+""));
            errorText.setStyle("-fx-text-fill: #0b83f3");
            errorText.setText("Your changes are committed successfully!!");
        }
    }
}
