package com.example.myapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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

public class CommandsController implements Initializable {
    @FXML
    public ComboBox<String> cbCountry;
    @FXML
    public ComboBox<String> cbStatus;
    @FXML
    public TextField searchField;
    @FXML
    public Label idField;
    @FXML
    public TextField cityField;
    @FXML
    public Label priceField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField weightField;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public TableView<Command> TvCommand;
    @FXML
    public TableColumn<Command, Integer> colid;
    @FXML
    public TableColumn<Command, Integer> colclientid;
    @FXML
    public TableColumn<Command, Integer> colrecid;
    @FXML
    public TableColumn<Command, Integer> colcountry;
    @FXML
    public TableColumn<Command, String> colcity;
    @FXML
    public TableColumn<Command, String> colprice;
    @FXML
    public TableColumn<Command, String> coladdress;
    @FXML
    public TableColumn<Command, String> colweight;
    @FXML
    public TableColumn<Command, String> colstatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillCommand();
        FillCombo();
        Search();
    }
    @FXML
    private void FillCombo() {
        //Status ComboBox
        List<String> cb2 = Arrays.asList("In progress..","Cancelled.","Well delivered.");
        for (String colName : cb2) {cbStatus.getItems().add(colName);}
        cbStatus.setValue("Select Status..");
        //Country ComboBox
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
    public ObservableList<Command> getCommandlist() {
        ObservableList<Command> commandlist = FXCollections.observableArrayList();
        try {
            Connection myConn = DB_Connection.getConn();
            ResultSet rs = myConn.createStatement().executeQuery("Select * from command");
            Command command;
            while (rs.next()) {
                command = new Command(rs.getInt("idCommand"), rs.getInt("idClient"),rs.getInt("idRec"),rs.getInt("idCountry"),rs.getString("City"),rs.getString("Address"),rs.getString("QteCommand"),rs.getString("Price"), rs.getString("Status"));
                commandlist.add(command);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commandlist;
    }

    @FXML
    public void FillCommand() {
        ObservableList<Command> commandlist = getCommandlist();
        colid.setCellValueFactory(new PropertyValueFactory<Command, Integer>("idCommand"));
        colclientid.setCellValueFactory(new PropertyValueFactory<Command, Integer>("idClient"));
        colrecid.setCellValueFactory(new PropertyValueFactory<Command, Integer>("idRec"));
        colcountry.setCellValueFactory(new PropertyValueFactory<Command, Integer>("idCountry"));
        colcity.setCellValueFactory(new PropertyValueFactory <Command, String>("City"));
        coladdress.setCellValueFactory(new PropertyValueFactory<Command, String>("Address"));
        colweight.setCellValueFactory(new PropertyValueFactory<Command, String>("QteCommand"));
        colprice.setCellValueFactory(new PropertyValueFactory<Command, String>("Price"));
        colstatus.setCellValueFactory(new PropertyValueFactory<Command, String>("Status"));
        TvCommand.setItems(commandlist);
        TvCommand.setVisible(true);
    }

    @FXML
    public void Search(){
        ObservableList<Command> commandlist = getCommandlist();
        FilteredList<Command> filterData =new FilteredList<>(commandlist, b ->true);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->{
            filterData.setPredicate(Command ->{
                if(newValue.isEmpty() || newValue.isBlank() || newValue==null){
                    return true;
                }
                String searchkey= newValue.toLowerCase();
                if (Command.getCity().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                } else if (Command.getAddress().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                } else if (Command.getPrice().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                }else if (Command.getStatus().toLowerCase().indexOf(searchkey) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<Command> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(TvCommand.comparatorProperty());
        TvCommand.setItems(sortedData);

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
        if (cityField.getText().isBlank() && weightField.getText().isBlank() && addressField.getText().isBlank() && cbCountry.getValue()==null && cbStatus.getValue()==null) {
            System.out.println("All textFields requires information !");
        } else {
            String query = "update command set idCountry= (select idCountry from countryprice where Country = '"+cbCountry.getValue()+"'), Status = '"+cbStatus.getValue()+"', city= '"+cityField.getText()+"', Address= '"+addressField.getText()+"', QteCommand= '"+weightField.getText()+"', Price=(select PricePerKilo*'"+weightField.getText()+"' from countryprice where Country='Morocco') where idCommand = '"+idField.getText()+"';";
            excuteQuery(query);
            FillCommand();
        }
    }

    @FXML
    public void DeleteRec() {
        String query = "Delete from command where idCommand = '" + idField.getText() + "'";
        excuteQuery(query);
        FillCommand();
    }

    @FXML
    public void AddScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCommand.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root));
        stage2.show();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnSave) {
            updateRec();
        } else if (event.getSource() == btnDelete) {
            DeleteRec();
        }else if (event.getSource() == btnAdd) {
            AddScene();
        } else if (event.getSource() == btnClear) {
            idField.setText("");
            priceField.setText("");
            cbCountry.setValue(null);
            cbStatus.setValue(null);
            cityField.clear();
            addressField.clear();
            weightField.clear();
            FillCommand();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Command command = TvCommand.getSelectionModel().getSelectedItem();
        idField.setText("" + command.getIdCommand());
        cityField.setText(command.getCity());
        addressField.setText(command.getAddress());
        priceField.setText(command.getPrice());
        weightField.setText(command.getQteCommand());
        cbStatus.setValue(command.getStatus());
        cbCountry.getSelectionModel().select(command.getIdCountry()-1);
    }
}
