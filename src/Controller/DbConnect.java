package Controller;

import java.sql.*;

public class DbConnect {

    private static DbConnect connect;
    private static String userName = "root";
    private static String password = "namanemo1812_";

    private DbConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore",
                    userName, password);
            statement = connection.createStatement();

        } catch (Exception ex) {

            System.out.println("Error : " + ex);
        }

    }

    private Connection connection;

    private Statement statement;
    private ResultSet resultSet;

    public static DbConnect getInstance() {

        if (connect == null)
            connect = new DbConnect();
        return connect;

    }

    public Connection getConnection() {
        return connection;
    }

    public void excuteQuery(String query) throws SQLException {

        resultSet = statement.executeQuery(query);

    }


    public ResultSet getData(String query) {

        try {
            resultSet = statement.executeQuery(query);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return resultSet;

    }

    public ResultSet getDataProcedure(CallableStatement statement) {
        try {

            resultSet = statement.executeQuery();

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
