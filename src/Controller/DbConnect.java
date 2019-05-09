package Controller;

import java.sql.*;

public class DbConnect {

    private static DbConnect connect;
    private static String userName = "root";
    private static String password = "";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    private DbConnect(String dataBaseName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dataBaseName,
                    userName, password);
            statement = connection.createStatement();

        } catch (Exception ex) {

            System.out.println("Error : " + ex);
        }

    }

    public static DbConnect getInstance(String dataBaseName) {

        if (connect == null)
            connect = new DbConnect(dataBaseName);
        return connect;

    }

    public ResultSet getData(String query) {

        try {
            resultSet = statement.executeQuery(query);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return resultSet;

    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
