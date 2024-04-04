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
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecieversController implements Initializable {
    @FXML
    public ComboBox<String> ComboRec;
    @FXML
    public TextField searchField;
    @FXML
    public Label idRecField;
    @FXML
    public TextField nomRecField;
    @FXML
    public TextField cinRecField;
    @FXML
    public TextField telRecField;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnClear;
    @FXML
    public TableView<Reciever> TvReciever;
    @FXML
    public TableColumn<Reciever, Integer> colid;
    @FXML
    public TableColumn<Reciever, String> colcin;
    @FXML
    public TableColumn<Reciever, String> colname;
    @FXML
    public TableColumn<Reciever, String> colphone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillReciever();
        Search();
    }
    @FXML
    public ObservableList<Reciever> getRecieverlist() {
        ObservableList<Reciever> recieverlist = FXCollections.observableArrayList();
        try {
            Connection myConn = DB_Connection.getConn();
            ResultSet rs = myConn.createStatement().executeQuery("Select * from Receiver");
            Reciever reciever;
            while (rs.next()) {
                reciever = new Reciever(rs.getInt("idRec"), rs.getString("cinRec"), rs.getString("nomRec"), rs.getString("telRec"));
                recieverlist.add(reciever);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecieversController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recieverlist;
    }

    @FXML
    public void FillReciever() {
        ObservableList<Reciever> recieverlist = getRecieverlist();
        colid.setCellValueFactory(new PropertyValueFactory<Reciever, Integer>("idRec"));
        colcin.setCellValueFactory(new PropertyValueFactory<Reciever, String>("cinRec"));
        colname.setCellValueFactory(new PropertyValueFactory<Reciever, String>("nomRec"));
        colphone.setCellValueFactory(new PropertyValueFactory<Reciever, String>("telRec"));
        TvReciever.setItems(recieverlist);
        TvReciever.setVisible(true);
    }

    @FXML
    public void Search(){
        ObservableList<Reciever> reclist = getRecieverlist();
        FilteredList<Reciever> filterData =new FilteredList<>(reclist, b ->true);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filterData.setPredicate(Reciever ->{
                if(newValue.isEmpty() || newValue.isBlank() || newValue==null){
                    return true;
                }
                String searchkey= newValue.toLowerCase();
                if (Reciever.getCinRec().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                } else if (Reciever.getNomRec().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                } else if (Reciever.getTelRec().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<Reciever> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(TvReciever.comparatorProperty());
        TvReciever.setItems(sortedData);

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
        if (!idRecField.getText().isBlank() && nomRecField.getText().isBlank() && cinRecField.getText().isBlank() && telRecField.getText().isBlank()) {
            System.out.println("3mer awa 3mer");
        } else {
            String query = "update Receiver set cinRec= '" + cinRecField.getText() + "', nomRec= '" + nomRecField.getText() + "', " +
                    "telRec= '" + telRecField.getText() + "' where idRec = '" + idRecField.getText() + "'";
            excuteQuery(query);
            FillReciever();
        }
    }

    @FXML
    public void DeleteRec() {
        String query = "Delete from receiver where idRec = '" + idRecField.getText() + "'";
        excuteQuery(query);
        FillReciever();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnUpdate) {
            updateRec();
        } else if (event.getSource() == btnDelete) {
            DeleteRec();
            idRecField.setText("");
            nomRecField.clear();
            cinRecField.clear();
            telRecField.clear();

        } else if (event.getSource() == btnClear) {
            idRecField.setText("");
            nomRecField.clear();
            cinRecField.clear();
            telRecField.clear();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Reciever reciever = TvReciever.getSelectionModel().getSelectedItem();
        idRecField.setText("" + reciever.getIdRec());
        cinRecField.setText(reciever.getCinRec());
        nomRecField.setText(reciever.getNomRec());
        telRecField.setText(reciever.getTelRec());
    }
}
