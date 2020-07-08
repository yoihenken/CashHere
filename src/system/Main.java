package system;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        try{
            ResultSet resultSet = database.getData("Select * from daftar_menu;");
            System.out.println("id_menu\t\tnama\t\tkategori\t\tharga\t\tstatus");
            while (resultSet.next()) {
                System.out.print(resultSet.getString(1) + "\t");
                System.out.print(resultSet.getString(2) + "\t");
                System.out.print(resultSet.getString(3) + "\t");
                System.out.print(resultSet.getString(4) + "\t");
                System.out.print(resultSet.getString(5) + "\t");
                System.out.println();
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
