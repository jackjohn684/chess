package server;

public class ServerMain {
    static Server chessServer;
    public static void main(String[] args) {
        chessServer = new Server();
        chessServer.run(8080);
    }

}
