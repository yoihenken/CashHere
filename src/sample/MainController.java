package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import system.Database;
import system.casher;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    //Declare
    //Dashboard
    @FXML private TableView<casher> table;
    @FXML private TableColumn<casher, String> id;
    @FXML private TableColumn<casher, String> nama;
    @FXML private TableColumn<casher, String> kategori;
    @FXML private TableColumn<casher, Integer> harga;
    @FXML private TableColumn<casher, String> status;
    @FXML private TableColumn<casher, String> jumlah;
    @FXML private TableColumn<casher, Button> tambah;

    //Table Pesanan
    @FXML private TableView<> tablePesanan;

    @FXML private Button buttonSemua;
    @FXML private Button buttonMakan;
    @FXML private Button buttonMinum;
    @FXML private Button buttonPaket;

    private int counter = 0;
    private Database database = new Database();
    private ResultSet resultSet = database.getData("Select * from daftar_menu;");;
    public ObservableList<casher> list = FXCollections.observableArrayList();

    //Fill Data
    private void getDataSemua(){
        try{
            ResultSet resultSet = database.getData("Select * from daftar_menu;");
            list.clear();
            while (resultSet.next()) {
                list.add(
                        new casher(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                resultSet.getString(5),
                                null,
                                new Button("Tambah"))
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void getDataMakan(){
        try{
            ResultSet resultSet = database.getData("Select * from daftar_menu where kategori = 'Makanan';");
            list.clear();
            while (resultSet.next()) {
                list.add(
                        new casher(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                resultSet.getString(5),
                                null,
                                new Button("Tambah"))

                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void getDataMinum(){
        try{
            ResultSet resultSet = database.getData("Select * from daftar_menu where kategori = 'Minuman';");
            list.clear();
            while (resultSet.next()) {
                list.add(
                        new casher(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                resultSet.getString(5),
                                null,
                                new Button("Tambah"))
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void getDataPaket(){
        try{
            ResultSet resultSet = database.getData("Select * from daftar_menu where kategori = 'Paket';");
            list.clear();
            while (resultSet.next()) {
                list.add(
                        new casher(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                resultSet.getString(5),
                                null,
                                new Button("Tambah"))
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //Event handler
    public void btnSemua(ActionEvent event){
        counter = 0;
        initTable();
    }

    public void btnMakan(ActionEvent event){
        counter = 1;
        initTable();
    }

    public void btnMinum(ActionEvent event){
        counter = 2;
        initTable();
    }

    public void btnPaket(ActionEvent event){
        counter = 3;
        initTable();
    }

    public void initTable(){
        if(counter == 0){
            getDataSemua();
        }else if(counter == 1){
            getDataMakan();
        }else if(counter == 2){
            getDataMinum();
        }else if(counter == 3){
            getDataPaket();
        }
        id.setCellValueFactory(new PropertyValueFactory<casher, String>("Id"));
        nama.setCellValueFactory(new PropertyValueFactory<casher, String>("Nama"));
        kategori.setCellValueFactory(new PropertyValueFactory<casher, String>("Kategori"));
        harga.setCellValueFactory(new PropertyValueFactory<casher, Integer>("Harga"));
        status.setCellValueFactory(new PropertyValueFactory<casher, String>("Status"));
        jumlah.setCellValueFactory(new PropertyValueFactory<casher, String>("Jumlah"));
        tambah.setCellValueFactory(new PropertyValueFactory<casher,Button>("Tambah"));

        editCols();
        table.setItems(list);
    }

    private void editCols(){
        jumlah.setCellFactory(TextFieldTableCell.forTableColumn());

        jumlah.setOnEditCommit(event ->
        {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setJumlah(event.getNewValue());
        });

        table.setEditable(true);
    }

    //Default
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}
