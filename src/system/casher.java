package system;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import sample.MainController;


public class casher {
    private final SimpleStringProperty id;
    private final SimpleStringProperty nama;
    private final SimpleStringProperty kategori;
    private final SimpleIntegerProperty harga;
    private final SimpleStringProperty status;
    Button tambah;

    public casher(String id, String nama, String kategori, int harga, String status, Button tambah) {
        this.id = new SimpleStringProperty(id) ;
        this.nama = new SimpleStringProperty(nama) ;
        this.kategori = new SimpleStringProperty(kategori) ;
        this.harga = new SimpleIntegerProperty(harga) ;
        this.status = new SimpleStringProperty(status) ;
        this.tambah = tambah;

        tambah.setOnAction(event -> {
            MainController mainController = new MainController();
            for (casher cas : MainController.getList()) {
                if (cas.getTambah() == tambah){
                    System.out.println("id_menu\t: " + cas.getId());
                    System.out.println("nama\t: " + cas.getNama());
                    System.out.println("kategori\t: " + cas.getKategori());
                    System.out.println("harga\t: " + cas.getHarga());
                    System.out.println("status\t: " + cas.getStatus());
                    try {
                        ObservableList<Order> list_order = MainController.getList_order();
                        int counter = 0;
                        int mark = 0;
                        for (int i = 0; i < list_order.size(); i++) {
                            if (list_order.get(i).getId() == cas.getId()){
                                counter = 1;
                                mark = i;
                            }
                        }
                        switch (counter){
                            case 0 :
                                System.out.println("falsemint");
                                list_order.add(
                                        new Order(
                                                cas.getId(),
                                                1,
                                                cas.getHarga(),
                                                null,
                                                new Button("Hapus"))
                                );
                                break;
                            case 1:
                                System.out.println("truemint");
                                int jumlah_list = list_order.get(mark).getJumlah() + 1;
                                Order order = new Order(
                                        cas.getId(),
                                        jumlah_list,
                                        (cas.getHarga() * jumlah_list),
                                        null,
                                        list_order.get(mark).getHapus()
                                );
                                list_order.set(mark, order);
                                break;
                        }
                        MainController.setList_order(list_order);
                        mainController.initTable_Pesanan();
                    }catch (NullPointerException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
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
