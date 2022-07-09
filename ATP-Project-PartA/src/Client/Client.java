package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    public void communicateWithServer() {
        try(Socket CSocket = new Socket(serverIP, serverPort)){
            strategy.clientStrategy(CSocket.getInputStream(), CSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}