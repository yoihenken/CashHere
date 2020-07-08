package system;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;


public class casher {
    private final SimpleStringProperty id;
    private final SimpleStringProperty nama;
    private final SimpleStringProperty kategori;
    private final SimpleIntegerProperty harga;
    private final SimpleStringProperty status;
    private String jumlah = "";
    Button tambah;

    public casher(String id, String nama, String kategori, int harga, String status, String jumlah, Button tambah) {
        this.id = new SimpleStringProperty(id) ;
        this.nama = new SimpleStringProperty(nama) ;
        this.kategori = new SimpleStringProperty(kategori) ;
        this.harga = new SimpleIntegerProperty(harga) ;
        this.status = new SimpleStringProperty(status) ;
        this.jumlah = jumlah;
        this.tambah = tambah;
    }

    public void setJumlah(String jumlah){
        this.jumlah = jumlah;
    }

    public String getJumlah(){
        return jumlah;
    }

    public Button getTambah() {
        return tambah;
    }

    public void setTambah(Button tambah) {
        this.tambah = tambah;
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getNama() {
        return nama.get();
    }

    public SimpleStringProperty namaProperty() {
        return nama;
    }

    public String getKategori() {
        return kategori.get();
    }

    public SimpleStringProperty kategoriProperty() {
        return kategori;
    }

    public int getHarga() {
        return harga.get();
    }

    public SimpleIntegerProperty hargaProperty() {
        return harga;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}
