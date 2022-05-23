package org.example;

import java.sql.*;

public class JDBSpostgreSQL {

    GetProperties properties = new GetProperties();

    public Connection connectDB() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return null;
        }

        Connection connection;
        try {
            connection = DriverManager
                    .getConnection(properties.getDB(), properties.getUser(), properties.getPass());
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    private static final String createTableSQL = "CREATE TABLE users " +
            "(ID INT PRIMARY KEY ," +
            " PERSON VARCHAR(50), " +
            " SCORE NUMERIC)";

    public void createTable() throws SQLException {

        try (Statement statement = connectDB().createStatement();) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private static final String INSERT_USERS_SQL = "INSERT INTO users" +
            "  (id, person, score) VALUES " +
            " (?, ?, ?);";

    public void insertUserRecord(long userID, String userName) throws SQLException {
        if (!getUserByID(userID)) {
            try (PreparedStatement preparedStatement = connectDB().prepareStatement(INSERT_USERS_SQL)) {
                preparedStatement.setInt(1, (int) userID);
                preparedStatement.setString(2, userName);
                preparedStatement.setInt(3, 0);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
        // Step 4: try-with-resource statement will auto close the connection.
    }

    private static final String QUERY = "select id, person, score from Users where id =?";
    private static final String SELECT_ALL_QUERY = "select * from users";

    public boolean getUserByID(long userID) {
        try (PreparedStatement preparedStatement = connectDB().prepareStatement(QUERY);) {
            preparedStatement.setInt(1, (int) userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String person = rs.getString("person");
                int score = rs.getInt("score");
                System.out.println(id + "," + person + "," + score);
                return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public void getAllUsers() {
        // using try-with-resources to avoid closing resources (boiler plate
        // code)

        // Step 1: Establishing a Connection
        try (PreparedStatement preparedStatement = connectDB().prepareStatement(SELECT_ALL_QUERY);) {
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String person = rs.getString("person");
                int score = rs.getInt("score");
                System.out.println(id + "," + person + "," + score);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
