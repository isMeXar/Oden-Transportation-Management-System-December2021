package com.example.myapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientsController implements Initializable {
    @FXML
    public TextField searchField;
    @FXML
    public Label idField;
    @FXML
    public TextField nomField;
    @FXML
    public TextField cinField;
    @FXML
    public TextField telField;
    @FXML
    public TextField emailField;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnClear;
    @FXML
    public TableView<Client> TvClient;
    @FXML
    public TableColumn<Client, Integer> colid;
    @FXML
    public TableColumn<Client, String> colcin;
    @FXML
    public TableColumn<Client, String> colname;
    @FXML
    public TableColumn<Client, String> colphone;
    @FXML
    public TableColumn<Client, String> colemail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillClient();
        Search();
    }
    @FXML
    public void Search(){
        ObservableList<Client> clientlist = getClientlist();
        FilteredList<Client> filterData =new FilteredList<>(clientlist,b ->true);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filterData.setPredicate(Client ->{
                if(newValue.isEmpty() || newValue.isBlank() || newValue==null){
                    return true;
                }
                String searchkey= newValue.toLowerCase();
                if (Client.getCinClient().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                } else if (Client.getNomClient().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                }else if (Client.getTelClient().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                }else if (Client.getEmailClient().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(TvClient.comparatorProperty());
        TvClient.setItems(sortedData);

    }


    @FXML
    public ObservableList<Client> getClientlist() {
        ObservableList<Client> clientlist = FXCollections.observableArrayList();
        try {
            Connection myConn = DB_Connection.getConn();
            ResultSet rs = myConn.createStatement().executeQuery("select * from client");
            Client client;
            while (rs.next()) {
                client = new Client(rs.getInt("idClient"), rs.getString("cinClient"), rs.getString("nomClient"), rs.getString("telClient"), rs.getString("emailClient"));
                clientlist.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientlist;
    }

    @FXML
    public void FillClient() {
        ObservableList<Client> clientlist = getClientlist();
        colid.setCellValueFactory(new PropertyValueFactory<Client, Integer>("idClient"));
        colcin.setCellValueFactory(new PropertyValueFactory <Client, String>("cinClient"));
        colname.setCellValueFactory(new PropertyValueFactory <Client, String>("nomClient"));
        colphone.setCellValueFactory(new PropertyValueFactory<Client, String>("telClient"));
        colemail.setCellValueFactory(new PropertyValueFactory<Client, String>("emailClient"));
        TvClient.setItems(clientlist);
        TvClient.setVisible(true);
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
    public void updateRec() {
        if (!idField.getText().isBlank() && nomField.getText().isBlank() && cinField.getText().isBlank() && telField.getText().isBlank() && emailField.getText().isBlank()) {
            System.out.println("3mer awa 3mer");
        } else {
            String query = "update client set cinClient= '" + cinField.getText() + "', nomClient= '" + nomField.getText() + "', " +
                    "telClient= '" + telField.getText() + "', emailClient= '" + emailField.getText() +"' where idClient = '" + idField.getText() + "'";
            excuteQuery(query);
            FillClient();
        }
    }

    @FXML
    public void DeleteRec() {
        String query = "Delete from client where idClient = '" + idField.getText() + "'";
        excuteQuery(query);
        FillClient();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnUpdate) {
            updateRec();
        } else if (event.getSource() == btnDelete) {
            DeleteRec();
            idField.setText("");
            nomField.clear();
            cinField.clear();
            telField.clear();
            emailField.clear();
        } else if (event.getSource() == btnClear) {
            idField.setText("");
            nomField.clear();
            cinField.clear();
            telField.clear();
            emailField.clear();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Client client = TvClient.getSelectionModel().getSelectedItem();
        idField.setText("" + client.getIdClient());
        cinField.setText(client.getCinClient());
        nomField.setText(client.getNomClient());
        telField.setText(client.getTelClient());
        emailField.setText(client.getEmailClient());
    }
}
