package system;

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
            JOptionPane.showMessageDialog(null,""+throwables.getMessage(),
                    "Database Connect Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    // Get Data from Database
    public ResultSet getData(String query){
        try {
            resultSet = statement.executeQuery(query);
        }catch (SQLException throwables){
            JOptionPane.showMessageDialog(null,""+throwables.getMessage(),
                    "\nFailed to Get Data", JOptionPane.WARNING_MESSAGE);
        }
        return resultSet;
    }

    // Execute query data
    public void setData(String query){
        try{
            statement.executeUpdate(query);
        }catch (SQLException throwables){
            JOptionPane.showMessageDialog(null,"Error" + throwables.getMessage(),
                    "\nFailed to upload Query! ", JOptionPane.WARNING_MESSAGE);
        }
    }

}
