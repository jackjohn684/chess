package dataAccess;

import chess.ChessGame;
import model.AuthToken;
import model.Game;
import model.GameInfo;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
public class MemoryDataAccess implements DataAccess {

    final private HashMap<String, User> users = new HashMap<>();
    public User addUser(User user) throws SQLException, DataAccessException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, user.username());
            preparedStatement.setString(2, user.password());
            preparedStatement.setString(3, user.email());
            preparedStatement.executeUpdate();
        }
        conn.close();
        return user;
    }
    public AuthToken addAuthToken(String username) throws SQLException, DataAccessException {
        Connection conn = DatabaseManager.getConnection();
        var auth = new AuthToken(username);
        try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES(?, ?)")) {
            preparedStatement.setString(1, auth.getAuth());
            preparedStatement.setString(2, auth.getUsername());
            preparedStatement.executeUpdate();
        }
        conn.close();
        return auth;
    }

    public int createGame(String auth, String gameName) throws DataAccessException, SQLException {
        Random rnd = new Random();
        int randomNumber = 1000 + rnd.nextInt(9000);
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName, gameID) VALUES(?,?)")){
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, String.valueOf(randomNumber));
            preparedStatement.executeUpdate();
        }
        conn.close();
        return randomNumber;
    }
    public String makeGame(Game game) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName, whiteUsername, blackUsername, gameID) VALUES(?,?,?,?)")){
            preparedStatement.setString(1, game.gameName());
            preparedStatement.setString(2, game.whiteUsername());
            preparedStatement.setString(3, game.blackUsername());
            preparedStatement.setString(4, game.gameID());
            preparedStatement.executeUpdate();
        }
        conn.close();
        return "yee";
    }
    public Collection<User> listUsers() throws DataAccessException, SQLException {
        ArrayList<User> users = new ArrayList<User>();
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT username, password, user from user")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var username = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");
                    User user = new User(username, password, email);
                    users.add(user);
                }
            }
        }
        conn.close();
        return users;
    }
    public Game[] listGames() throws DataAccessException, SQLException {
        ArrayList<Game> games = new ArrayList<Game>();
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName from game")){
            try(var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var gameID = rs.getString("gameID");
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                   Game game = new Game(gameID, whiteUsername, blackUsername, gameName, new ChessGame());
                   games.add(game);
                }
            }
        }
        conn.close();
        if(games.isEmpty()) {
            return null;
        }
        Game [] array = new Game[games.size()];
        array = games.toArray(array);
        return  array;
    }

    public void deleteUser(String username) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE from user where username = ?")){
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }
        conn.close();
    }
    public void deleteAuthToken(String auth) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE from auth where authToken = ?")){
            preparedStatement.setString(1, auth);
            preparedStatement.executeUpdate();
        }
        conn.close();
    }

    public void deleteGame(String gameID) throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE from game where gameID = ?")){
            preparedStatement.setString(1, gameID);
            preparedStatement.executeUpdate();
        }
        conn.close();
    }
    public User getUser(String username) throws DataAccessException, SQLException {
        User user = null;
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT username, password, email from user WHERE username = ?")) {
            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    var otherUsername = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");
                    user = new User(otherUsername, password, email);
                }
            }
        }
        conn.close();
        return user;
    }
    public AuthToken getAuthToken(String auth) throws DataAccessException, SQLException {
        AuthToken auth1 = null;
        Connection conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username from auth WHERE authToken=?")) {
            preparedStatement.setString(1, auth);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var authToken = rs.getString("authToken");
                    var username = rs.getString("username");
                    auth1 = new AuthToken(username, authToken);
                }
            }
        }
        conn.close();
        return auth1;
    }
    public void clearUsers() throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("DROP TABLE auth;")){
            preparedStatement.executeUpdate();
        }
        try(var preparedStatement = conn.prepareStatement("DROP TABLE user;")){
            preparedStatement.executeUpdate();
        }
        conn.close();
    }
    public void clearGames() throws DataAccessException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("DROP TABLE game")) {
            preparedStatement.executeUpdate();
        }
        conn.close();
    }

    public void clear() throws SQLException, DataAccessException {
        clearUsers();
        clearGames();
    }
    public Game getGame(String gameID) throws DataAccessException, SQLException {
        Game game = null;
        Connection conn = DatabaseManager.getConnection();
        try(var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName from game WHERE gameID = ?")){
            preparedStatement.setString(1, gameID);
            try(var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                   game = new Game(gameID, whiteUsername, blackUsername, gameName, new ChessGame());
                }
            }
        }
        conn.close();
        return game;
    }


    public String joinGame(GameInfo gameInfo, String username) throws SQLException, DataAccessException {
        var oldGame = getGame(gameInfo.gameID());
        String playerColor = gameInfo.playerColor();
        if (oldGame == null || oldGame.gameID() != gameInfo.gameID()){
            return  "400";
        }
        Game newGame = null;
        if (gameInfo.playerColor() == null) {
            return "";
        } else if (playerColor.equals("white")) {
            if (oldGame.whiteUsername() == null) {
                newGame = new Game(oldGame.gameID(), username, oldGame.blackUsername(), oldGame.gameName(), oldGame.game());
            }
            else {
                return "403";
            }
        } else if (playerColor.equals("black")) {
            if (oldGame.blackUsername() == null) {
                newGame = new Game(oldGame.gameID(), oldGame.whiteUsername(), username, oldGame.gameName(), oldGame.game());
            }
            else {
                return "403";
            }
        }
        else {
            return "403";
        }
        deleteGame(gameInfo.gameID());
        assert newGame != null;
        makeGame(newGame);
        return "";

    }
}
