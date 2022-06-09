package Server;

import java.io.*;

public class ServerStrategyStringReverser implements IServerStrategy{
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inFromClient));
        BufferedWriter toClient = new BufferedWriter(new PrintWriter(outToClient));

        try {
//            Thread.sleep(2000);
            String reversed = new StringBuilder(fromClient.readLine()).reverse().toString();
            toClient.write(reversed + "\n");
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
