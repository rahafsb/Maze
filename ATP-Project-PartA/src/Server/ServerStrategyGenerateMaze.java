package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import java.io.*;

//the server take from the client int[] with size of 2, while the first int describe how many rows in the maze and the second describe the columns of the maze
//the server generate a maze, then use MyCompressorOutputStream and compress the maze, then he sent it to the client again
public class ServerStrategyGenerateMaze implements IServerStrategy{
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            OutputStream os = new ByteArrayOutputStream();
            MyCompressorOutputStream myCompressorOutputStream = new MyCompressorOutputStream(os);

            int[] dimensions = (int[]) fromClient.readObject();
            AMazeGenerator gen = Configurations.getMazeGeneratorMethod();
            Maze maze = gen.generate(dimensions[0], dimensions[1]);

            byte[] maze_as_bytes = new byte[maze.toByteArray().length];
            myCompressorOutputStream.write(maze.toByteArray());
            toClient.writeObject(((ByteArrayOutputStream)myCompressorOutputStream.out).toByteArray());
            toClient.flush();

            os.close();
            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
