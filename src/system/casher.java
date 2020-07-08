package system;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class casher {
    private final SimpleStringProperty id;
    private final SimpleStringProperty nama;
    private final SimpleStringProperty kategori;
    private final SimpleIntegerProperty harga;
    private final SimpleStringProperty status;

    public casher(String id, String nama, String kategori, int harga, String status) {
        this.id = new SimpleStringProperty(id) ;
        this.nama = new SimpleStringProperty(nama) ;
        this.kategori = new SimpleStringProperty(kategori) ;
        this.harga = new SimpleIntegerProperty(harga) ;
        this.status = new SimpleStringProperty(status) ;
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
