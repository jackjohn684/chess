package ui;

import exception.ResponseException;
import model.User;
import org.junit.jupiter.api.Assertions;
import server.ServerFacade;

import java.net.HttpURLConnection;
import java.util.Arrays;
public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "signin" -> signIn(params);
                case "signout" -> signOut();
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String signIn(String... params) throws ResponseException {
        if (params.length >= 2) {
            var username = params[0];
            var password = params[1];
            state = State.SIGNEDIN;
            visitorName = String.join("-", params);
            return String.format("You signed in as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <yourname>");
    }
    public String register(String... params) throws ResponseException {
        if(params.length >= 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, server.register(new User(username, password, email)),
                    "Server response code was not 200 OK");
            return String.format("Epic, you signed in as %s.", username);
        }
        throw new ResponseException (400, "Incorrect credentials??");
    }

    public String signOut() throws ResponseException {
        assertSignedIn();
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
                    -register <username, password, email> I hope this works
                    - signIn <yourname>
                    - quit
                    """;
        }
        return """
                - list
                Ok, I'm going to set these to the things you'd actually do in the chess game""";
    }
}
