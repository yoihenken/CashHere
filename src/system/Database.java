package system;

import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;

public class Database {
    private String host = "jdbc:mysql://localhost:3306/casher_geprek";
    private String user = "root";
    private String pass = "";
    private Statement statement;
    private Connection connection;
    private ResultSet resultSet;

    //Constructor and Connecting to Database
    public Database(){
        try {
            connection = DriverManager.getConnection(host, user, pass);
            statement = connection.createStatement();
            System.out.println("Database Connected");
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Database !");
            alert.setHeaderText("Failed to Connect Database !");
            alert.setContentText(throwables.getMessage());

            alert.showAndWait();
        }
    }
    // Get Data from Database
    public ResultSet getData(String query){
        try {
            resultSet = statement.executeQuery(query);
        }catch (SQLException throwables){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Database !");
            alert.setHeaderText("Failed to Get Data from Database !");
            alert.setContentText(throwables.getMessage());

            alert.showAndWait();
        }
        return resultSet;
    }

    // Execute query
    public void setData(String query){
        try{
            statement.executeUpdate(query);
        }catch (SQLException throwables){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Database !");
            alert.setHeaderText("Failed to Upload Query to Database !");
            alert.setContentText(throwables.getMessage());

            alert.showAndWait();
        }
    }

}
