package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import system.Database;
import system.casher;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TableView<casher> table;
    @FXML private TableColumn<casher, String> id;
    @FXML private TableColumn<casher, String> nama;
    @FXML private TableColumn<casher, String> kategori;
    @FXML private TableColumn<casher, Integer> harga;
    @FXML private TableColumn<casher, String> status;

    @FXML private Button buttonSemua;
    @FXML private Button buttonMakan;
    @FXML private Button buttonMinum;
    @FXML private Button buttonPaket;

    private int counter = 0;
    private Database database = new Database();
    private ResultSet resultSet = database.getData("Select * from daftar_menu;");;
    public ObservableList<casher> list = FXCollections.observableArrayList();
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
                                resultSet.getString(5))
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
                                resultSet.getString(5))
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
                                resultSet.getString(5))
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
                                resultSet.getString(5))
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void btnSemua(ActionEvent event){
        counter = 0;
        btnKategori();
    }

    public void btnMakan(ActionEvent event){
        counter = 1;
        btnKategori();
    }

    public void btnMinum(ActionEvent event){
        counter = 2;
        btnKategori();
    }

    public void btnPaket(ActionEvent event){
        counter = 3;
        btnKategori();
    }

    public void btnKategori(){
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
        table.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<casher, String>("Id"));
        nama.setCellValueFactory(new PropertyValueFactory<casher, String>("Nama"));
        kategori.setCellValueFactory(new PropertyValueFactory<casher, String>("Kategori"));
        harga.setCellValueFactory(new PropertyValueFactory<casher, Integer>("Harga"));
        status.setCellValueFactory(new PropertyValueFactory<casher, String>("Status"));
        btnKategori();
        table.setItems(list);
    }
}
