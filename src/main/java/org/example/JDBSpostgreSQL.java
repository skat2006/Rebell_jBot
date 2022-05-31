package org.example;

import java.sql.*;

public class JDBSpostgreSQL {

    GetProperties properties = new GetProperties();

    public Connection connectDB() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(properties.getDB(), properties.getUser(), properties.getPass());
        } catch (SQLException e) {
            System.out.println("DB connection Failed");
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    private static final String INSERT_USERS_SQL = "INSERT INTO users" +
            "  (id, person, score) VALUES " +
            " (?, ?, ?);";

    public void insertUserRecord(long userID, String userName) {
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
    }

    public void insertUserRecord(long userID, String userName, int score) {
        if (!getUserByID(userID)) {
            try (PreparedStatement preparedStatement = connectDB().prepareStatement(INSERT_USERS_SQL)) {
                preparedStatement.setInt(1, (int) userID);
                preparedStatement.setString(2, userName);
                preparedStatement.setInt(3, score);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    private static final String SET_SCORE_SQL = "UPDATE users " +
            "SET score =? " +
            "WHERE id =?;";

    public void increaseScoreByID(long userID, String userName) {
        if (getUserByID(userID)) {
            try (PreparedStatement preparedStatement = connectDB().prepareStatement(SET_SCORE_SQL)) {
                preparedStatement.setInt(1, getScoreByID(userID) + 1);
                preparedStatement.setInt(2, (int) userID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                printSQLException(e);
            }
        } else {
            insertUserRecord(userID, userName, 1);
        }
    }

    private static final String QUERY_SCORE = "select score from Users where id =?";

    public int getScoreByID(long userID) {
        try (PreparedStatement preparedStatement = connectDB().prepareStatement(QUERY_SCORE)) {
            preparedStatement.setInt(1, (int) userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                return rs.getInt("score");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return -1;
    }

    private static final String QUERY_USER = "select id, person, score from Users where id =?";

    public boolean getUserByID(long userID) {
        try (PreparedStatement preparedStatement = connectDB().prepareStatement(QUERY_USER)) {
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

    private static final String SELECT_ALL_QUERY = "select * from users";

    public void getAllUsers() {
        try (PreparedStatement preparedStatement = connectDB().prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();

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
