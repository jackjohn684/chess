package ui;

import exception.ResponseException;
import model.AuthToken;
import model.Game;
import model.GameInfo;
import model.User;
import server.Server;
import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private final Server chessServer;
    private State state = State.SIGNEDOUT;
    private String boardString;
    private AuthToken authToken;
    public ChessClient(String serverUrl) {
        chessServer = new Server();
        chessServer.run(8080);
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> signIn(params);
                case "logout" -> signOut();
                case "list" -> listGames();
                case "create" -> createGame(params);
                case "register" -> register(params);
                case "observe" -> observe(params);
                case "join" -> join(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String signIn(String... params) throws ResponseException {
        if (params.length == 2) {
            var username = params[0];
            var password = params[1];
            visitorName = username;
            authToken =  server.login(username, password);
            state = State.SIGNEDIN;
            return String.format("You signed in as %s.", username);
        }
        throw new ResponseException(400, "Expected: <yourname>");
    }

    public String observe(String... params) throws ResponseException {
        if (params.length == 1) {
            var gameID = (params[0]);
            var gameinfo = new GameInfo( gameID, null, null);
            server.joinGame(authToken,  gameinfo);
           // ChessBoard.doBoard();
            return "";
        }
        return "Incorrect number of parameters";
    }
    public String join(String... params) throws ResponseException {
        if(params.length == 2) {
            String whiteUsername = null;
            String blackUsername = null;
            var gameID = (params[0]);
            String whiteOrBlack = (params[1]);

            if (!whiteOrBlack.equals("white") && !whiteOrBlack.equals("black"))
            {
                throw new ResponseException(400, "You have to say white or black");
            }
            var gameinfo = new GameInfo(gameID, whiteOrBlack, null);
            server.joinGame(authToken, gameinfo);
            //ChessBoard.doBoard();
            return "";
        }
        return "yee";
    }
    public String register(String... params) throws ResponseException {
        if(params.length == 3) {
                var myName = String.join("-", params);
                var username = params[0];
                var password = params[1];
                var email = params[2];
                visitorName = username;
                var user1 = new User(username, password, email);
                authToken = server.register(user1);
                state = State.SIGNEDIN;
                return String.format("Epic, you signed in as %s.", username);

        }
        throw new ResponseException (400, "Incorrect credentials");
    }

    public String listGames(String ... params) throws ResponseException {
        Game[] games =  server.listGames(authToken);
       String returnString = "";
        for (Game game : games) {
            returnString += ("(GameName: " + game.gameName() + ",  whiteUsername: " + game.whiteUsername() + ",  blackUsername: " + game.blackUsername() + ",  gameID: " + game.gameID() + ")");
            returnString += "\n";
        }
       return returnString;
    }
    public String createGame(String ... params) throws ResponseException {
        assertSignedIn();
        if(params.length >= 1){
            var gameName = String.join("-", params);
            var gameID = server.createGame(authToken, gameName);
            return ("Awesome, you created a game. GameID = " + gameID);
        }
        throw new ResponseException(400, "You need to provide a name for the game");
    }
    public String signOut() throws ResponseException {
        assertSignedIn();
        String logOutName = visitorName;
        server.logout(authToken);
        state = State.SIGNEDOUT;
        return String.format("%s left the game", visitorName);
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - login <yourname>
                    -register <username, password, email>
                    - quit
                    """;
        }
        return """
                - Create <NAME> - a game
                - list - games
                - join<ID> [WHITE|BLACK|<empty>] - a game
                - observe <ID> a game
                - logout - when you are done
                - quit - playing chess
                - help - with possible commands
                """;
    }
}
