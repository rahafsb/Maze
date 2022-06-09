package Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private boolean stop;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
    }

    public void start(){
       Thread t = new Thread(this::start_with_thread);
       t.start();
    }


    public void start_with_thread(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
//            System.out.println("Starting server at port = " + port);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Configurations.numOfThreads());//////

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
//                    System.out.println("Client accepted: " + clientSocket.toString());
                    executor.execute(new clientThread(clientSocket, strategy));
                } catch (SocketTimeoutException e){
//                    System.out.println("Socket timeout");
                }
            }

            serverSocket.close();
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stop(){
        stop = true;
    }}

class clientThread extends Thread {
    private Socket cs;
    private IServerStrategy st;

    public clientThread(Socket c, IServerStrategy s) {
        this.cs = c;
        this.st = s;
    }

    public void run() {
        try {
            st.applyStrategy(cs.getInputStream(), cs.getOutputStream());
            cs.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
