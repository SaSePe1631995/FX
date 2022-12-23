package com.example.fx;
import java.sql.*;

public class DBConn {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DBNAME = "diploma";
    private final String USERNAME = "root";
    private final String USERPASS = "root";
    private Connection dbConn = null;

    private Connection getDBConnection () throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://"+HOST+":"+PORT+"/"+DBNAME;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connStr,USERNAME,USERPASS);
        return dbConn;
    }

    public void isConnected () throws ClassNotFoundException, SQLException {
        dbConn = getDBConnection();
        System.out.println(dbConn.isValid(2000));
    }

    public boolean isExistLink (String shortL) {

        String query = "SELECT `id` FROM `link` WHERE `shortl` LIKE ?";
        try {
            PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setString(1, shortL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addLink(String longL, String shortL) {

        String query = "INSERT INTO `link` (`id`, `longl`, `shortl`) VALUES (NULL, ?, ?)";
        try {
            PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
            preparedStatement.setString(1, longL);
            preparedStatement.setString(2, shortL);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getLinks(){

        String query = "SELECT `shortl` FROM `link`";
        Statement statement = null;
        try {
            statement = getDBConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
