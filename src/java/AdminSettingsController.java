package com.example.myapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminSettingsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

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
}
