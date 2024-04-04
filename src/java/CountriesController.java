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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountriesController implements Initializable {
    @FXML
    public TextField searchField;
    @FXML
    public Label idField;
    @FXML
    public TextField countryField;
    @FXML
    public TextField priceField;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public TableView<Country> TvCountry;
    @FXML
    public TableColumn<Country, Integer> colid;
    @FXML
    public TableColumn<Country, String> colcountry;
    @FXML
    public TableColumn<Country, String> colprice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillCountry();
        Search();
    }
    @FXML
    public ObservableList<Country> getCountrylist() {
        ObservableList<Country> countrylist = FXCollections.observableArrayList();
        try {
            Connection myConn = DB_Connection.getConn();
            ResultSet rs = myConn.createStatement().executeQuery("Select * from countryprice");
            Country country;
            while (rs.next()) {
                country = new Country(rs.getInt("idCountry"), rs.getString("Country"), rs.getString("PricePerKilo"));
                countrylist.add(country);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CountriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return countrylist;
    }

    @FXML
    public void Search(){
        ObservableList<Country> countrylist = getCountrylist();
        FilteredList<Country> filterData =new FilteredList<>(countrylist, b ->true);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filterData.setPredicate(Country ->{
                if(newValue.isEmpty() || newValue.isBlank() || newValue==null){
                    return true;
                }
                String searchkey= newValue.toLowerCase();
                if (Country.getCountry().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                } else if (Country.getPricePerKilo().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                } else{
                    return false;
                }
            });
        });
        SortedList<Country> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(TvCountry.comparatorProperty());
        TvCountry.setItems(sortedData);
    }

    @FXML
    public void FillCountry() {
        ObservableList<Country> countrylist = getCountrylist();
        colid.setCellValueFactory(new PropertyValueFactory<Country, Integer>("idCountry"));
        colcountry.setCellValueFactory(new PropertyValueFactory<Country, String>("Country"));
        colprice.setCellValueFactory(new PropertyValueFactory<Country, String>("PricePerKilo"));
        TvCountry.setItems(countrylist);
        TvCountry.setVisible(true);
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
    public void insertRec() {
        if (!idField.getText().isBlank() && countryField.getText().isBlank() && priceField.getText().isBlank()) {
            System.out.println("All textFields requires information !");
        } else {
            String query = "INSERT INTO countryprice(Country,PricePerKilo) VALUES('"+ countryField.getText() +"',"+ priceField.getText() +")";
            excuteQuery(query);
            FillCountry();
        }
    }

    @FXML
    public void updateRec() {
        if (!idField.getText().isBlank() && countryField.getText().isBlank() && priceField.getText().isBlank()) {
            System.out.println("All textFields requires information !");
        } else {
            String query = "update countryprice set Country= '" + countryField.getText() + "', PricePerKilo= '" + priceField.getText() + "' where idCountry = '" + idField.getText() + "'";
            excuteQuery(query);
            FillCountry();
        }
    }

    @FXML
    public void DeleteRec() {
        String query = "Delete from countryprice where idCountry = '" + idField.getText() + "'";
        excuteQuery(query);
        FillCountry();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnUpdate) {
            updateRec();
        } else if (event.getSource() == btnDelete) {
            DeleteRec();
        }else if (event.getSource() == btnAdd) {
            insertRec();
        } else if (event.getSource() == btnClear) {
            idField.setText("");
            countryField.clear();
            priceField.clear();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Country country = TvCountry.getSelectionModel().getSelectedItem();
        idField.setText("" + country.getIdCountry());
        countryField.setText(country.getCountry());
        priceField.setText(country.getPricePerKilo());
    }
}
