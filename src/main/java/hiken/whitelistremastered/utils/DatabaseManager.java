package hiken.whitelistremastered.utils;

import hiken.whitelistremastered.WhitelistRemastered;
import lombok.Setter;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    WhitelistRemastered instance = WhitelistRemastered.getInstance();
    @Getter @Setter Connection connection;

    public void setupConnection() {
        String dbHost = instance.getConfig().getString("storage.host");
        int dbPort = instance.getConfig().getInt("storage.port");
        String dbUser = instance.getConfig().getString("storage.user");
        String dbPassword = instance.getConfig().getString("storage.password");
        String dbName = instance.getConfig().getString("storage.database-name");
        try {
            synchronized (this) {
                if(getConnection() != null && !getConnection().isClosed()) return;
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?charactedEncoding=latin1";
                setConnection(DriverManager.getConnection(url, dbUser, dbPassword));
                setupUsersTable();
                instance.log("Hooked to database");
            }
        } catch(SQLException | ClassNotFoundException e) {
            instance.error("An error occurred");
            e.printStackTrace();
        }
    }

    void setupUsersTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS users (Name VARCHAR(16));";
        getConnection().prepareStatement(query).execute();
    }

    public void addPlayerToWhitelist(String playerName) {
        String query = "INSERT INTO users VALUES ('" + playerName + "');";
        try {
            getConnection().prepareStatement(query).execute();
        } catch(SQLException e) {
            instance.error("An error occurred");
            e.printStackTrace();
        }
    }

    public void remPlayerFromWhitelist(String playerName) {
        String query = "DELETE FROM users WHERE Name = '" + playerName + "';";
        try {
            getConnection().prepareStatement(query).execute();
        } catch(SQLException e) {
            instance.error("An error occurred");
            e.printStackTrace();
        }
    }

    public boolean isPlayerInWhitelist(String playerName) {
        String query = "SELECT Name FROM users WHERE Name = '" + playerName + "';";
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            return resultSet.next();
        } catch(SQLException e) {
            instance.error("An error occurred");
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getWhitelistedUsers() {
        List<User> whitelistedUsers = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            while(resultSet.next()) {
                whitelistedUsers.add(new User(resultSet.getString("Name")));
            }
        } catch(SQLException e) {
            instance.error("An error occurred");
            e.printStackTrace();
        }
        return whitelistedUsers;
    }

}
