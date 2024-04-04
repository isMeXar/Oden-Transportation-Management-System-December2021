package com.example.myapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainStageController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowClients();
    }
    @FXML
    public Button btnClient;
    @FXML
    public Button btnCommand;
    @FXML
    public Button btnReciever;
    @FXML
    public Button btnCountry;
    @FXML
    public Button btnAdmin;
    @FXML
    public Button btnDisconnect;
    @FXML
    public Button btnexit;
    @FXML
    public void CloseButtonAction() {
        Stage stage = (Stage) btnexit.getScene().getWindow();
        stage.close();
    }
    @FXML
    public Button btnminimize;
    @FXML
    private void minimizeBut() {
        Stage stage=(Stage) btnminimize.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public Pane PaneMain;
    @FXML
    public void ShowClients() {
        Pane newPane = null;
        try {
            newPane = FXMLLoader.load(getClass().getResource("Clients.fxml"));
        } catch (IOException ex) {
            System.out.println("Error pane clients");
        }
        PaneMain.getChildren().add(newPane);
        btnClient.setStyle("");
    }
    @FXML
    public void ShowCommands(){
        Pane newPane = null;
        try {
            newPane = FXMLLoader.load(getClass().getResource("Command.fxml"));
        } catch (IOException ex) {
            System.out.println("Error pane scene commands");
        }
        PaneMain.getChildren().add(newPane);
    }
    @FXML
    public void ShowRecievers(){
        Pane newPane = null;
        try {
            newPane = FXMLLoader.load(getClass().getResource("Recievers.fxml"));
        } catch (IOException ex) {
            System.out.println("Error pane scene recievers");
        }
        PaneMain.getChildren().add(newPane);
    }
    @FXML
    public void ShowCountries(){
        Pane newPane = null;
        try {
            newPane = FXMLLoader.load(getClass().getResource("Countries.fxml"));
        } catch (IOException ex) {
            System.out.println("Error pane scene country");
        }
        PaneMain.getChildren().add(newPane);
    }
    @FXML
    public void ShowAdmin(){
        Pane newPane = null;
        try {
            newPane = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        } catch (IOException ex) {
            System.out.println("Error pane scene admin");
        }
        PaneMain.getChildren().add(newPane);
    }
    @FXML
    public void Disconnect() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.initStyle(StageStyle.UNDECORATED);
        stage2.setScene(new Scene(root));
        stage2.show();
        Stage stage1 = (Stage) btnexit.getScene().getWindow();
        stage1.close();
    }
}
