package system;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import sample.MainController;

import java.util.ConcurrentModificationException;

public class Order {
        private String id;
        private int jumlah;
        private int harga;
        private String catatan;
        private Button hapus;

        public Order(String id, int jumlah, int harga, String catatan, Button hapus) {
                this.id = id;
                this.jumlah = jumlah;
                this.harga = harga;
                this.catatan = catatan;
                this.hapus = hapus;

                hapus.setOnAction(event -> {
                        MainController mainController = new MainController();
                        try {
                                for (Order order : MainController.getList_order()){
                                   if(order.getHapus() == hapus){

                                                ObservableList<Order> list_order = MainController.getList_order();
                                                int mark = 0;
                                                for (int i = 0; i < list_order.size(); i++) {
                                                        if (list_order.get(i).getId() == order.getId()){
                                                                mark = i;
                                                        }
                                                }
                                                list_order.remove(mark);
                                                mainController.setList_order(list_order);
                                        }
                                   }
                        }catch (ConcurrentModificationException e) {
                                System.out.println(e.getMessage());
                        }
                });
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public int getJumlah() {
                return jumlah;
        }

        public void setJumlah(int jumlah) {
                this.jumlah = jumlah;
        }

        public int getHarga() {
                return harga;
        }

        public void setHarga(int harga) {
                this.harga = harga;
        }

        public String getCatatan() {
                return catatan;
        }

        public void setCatatan(String catatan) {
                this.catatan = catatan;
        }

        public Button getHapus() {
                return hapus;
        }

        public void setHapus(Button hapus) {
                this.hapus = hapus;
        }
}
