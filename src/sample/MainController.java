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
import system.Order;
import system.casher;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    //Declaration
    //Dashboard
    @FXML private TableView<casher> table;
    @FXML private TableColumn<casher, String> id;
    @FXML private TableColumn<casher, String> nama;
    @FXML private TableColumn<casher, String> kategori;
    @FXML private TableColumn<casher, Integer> harga;
    @FXML private TableColumn<casher, String> status;
    @FXML private TableColumn<casher, Button> tambah;

    //Table Pesanan
    @FXML private TableView<Order> tablePesanan;
    @FXML private TableColumn<Order, String> id_pesan;
    @FXML private TableColumn<Order, String> jumlah_pesan;
    @FXML private TableColumn<Order, Integer> harga_pesan;
    @FXML private TableColumn<Order, String> catatan_pesan;
    @FXML private TableColumn<Order, Button> hapus_pesan;

    //Payment
    @FXML private Label totalHarga;
    @FXML private TextField txMeja;
    @FXML private Button buttonBayar;
    @FXML private Button buttonHitung;

    //Category Button
    @FXML private Button buttonSemua;
    @FXML private Button buttonMakan;
    @FXML private Button buttonMinum;
    @FXML private Button buttonPaket;

    //Database
    private int counter = 0;
    private Database database = new Database();
    private ResultSet resultSet = database.getData("Select * from daftar_menu;");;
    private static ObservableList<casher> list = FXCollections.observableArrayList();

    //Fill Data table List Menu based button category
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
                                new Button("Tambah"))
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //Event handler Button
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

    public void btnHitung(ActionEvent event){
        totalPrice();
        buttonBayar.setDisable(false);
    }

    public void btnBayar(ActionEvent event){
        try{
            if (list_order.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Belum Terisi !");
                alert.setHeaderText("Pilih pesanan terlebih dahulu !");
                alert.setContentText("Tanyakan Pelanggan pesanannya dan pastikan tidak salah memasukkan pesanan !");

                alert.showAndWait();
                buttonBayar.setDisable(true);
                return;
            }
            totalPrice();
            //Declaration Database
            //id_Pesanan
            ResultSet rs_idPesanan = database.getData("SELECT id_pesanan FROM `pesanan` WHERE LAST_VALUE(id_pesanan) ORDER BY id_pesanan DESC LIMIT 1"); //Get id_Pesanan from table Pesanan (Database)
            rs_idPesanan.next(); // Important
            System.out.println("RS: " + rs_idPesanan.getString(1)); //print return
            int idPesanan = (rs_idPesanan.getInt(1) + 1 ); //Get new id_Pesanan for new Order
            //tanggal_order
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Format for Date Database
            Date date = new Date();
            String dateData = formatter.format(date); //Var for query
            System.out.println(formatter.format(date)); // print return

            //Start Insert Data
            for (int i = 0; i < list_order.size(); i++) {
                //Check if no.meja field is null
                if (txMeja.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Belum terisi !");
                    alert.setHeaderText("Nomor Meja belum terisi !");
                    alert.setContentText("Isi terlebih dahulu nomor meja pelanggan!");

                    alert.showAndWait();
                }else{ // if false, then
                    String query = "" +
                            "INSERT INTO `pesanan`(`id_pesanan`, `id_menu`, `jumlah`, `jumlah_harga`, `catatan`, `no_meja`, `tanggal_order`) " +
                            "VALUES (" + idPesanan + ", '"+ list_order.get(i).getId() + "', '" +
                                list_order.get(i).getJumlah() + "', '" + list_order.get(i).getHarga() + "', '" +
                                list_order.get(i).getCatatan() + "', '" + txMeja.getText() + "', '" + dateData + "');";

                    System.out.println(query); //print return
                    database.setData(query);
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        //After Click Button
        list_order.clear();
        txMeja.setText("");
        totalHarga.setText("Rp. 0");
        buttonBayar.setDisable(true);
    }

    //Decision of category Button
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
        tambah.setCellValueFactory(new PropertyValueFactory<casher,Button>("Tambah"));

        table.setItems(list);

    }

    //Declare List Order
    private static ObservableList<Order> list_order = FXCollections.observableArrayList();


    //Fill data table Order
    public void initTable_Pesanan(){
        //Set cell TableView
        id_pesan.setCellValueFactory(new PropertyValueFactory<Order, String>("Id"));
        jumlah_pesan.setCellValueFactory(new PropertyValueFactory<Order, String>("Jumlah"));
        harga_pesan.setCellValueFactory(new PropertyValueFactory<Order, Integer>("Harga"));
        catatan_pesan.setCellValueFactory(new PropertyValueFactory<Order, String>("Catatan"));
        hapus_pesan.setCellValueFactory(new PropertyValueFactory<Order, Button>("Hapus"));

        //Editable
        catatan_pesan.setCellFactory(TextFieldTableCell.forTableColumn());
        catatan_pesan.setOnEditCommit(event ->
        {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCatatan(event.getNewValue());
        });
        tablePesanan.setEditable(true);
        //Set TableView
        tablePesanan.setItems(list_order);
    }

    //Payment
    //Method count total price
    public void totalPrice(){
        int total = 0;

        if (list_order.isEmpty()) {
            totalHarga.setText("Rp. " + 0);
        }
        else {
            for (int i = 0; i < list_order.size(); i++) {
                total += list_order.get(i).getHarga();
            }
            totalHarga.setText("Rp. " + total);
        }
    }

    //Default
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initTable_Pesanan();
        buttonBayar.setDisable(true);
    }

    public static ObservableList<casher> getList() {
        return list;
    }

    public void setList(ObservableList<casher> list) {
        this.list = list;
    }

    public static ObservableList<Order> getList_order() {
        return list_order;
    }

    public static void setList_order(ObservableList<Order> list_order) {
        MainController.list_order = list_order;
    }
}
