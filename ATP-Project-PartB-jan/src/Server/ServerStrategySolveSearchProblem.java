package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            Maze maze = (Maze) fromClient.readObject();
            String file_path = System.getProperty("java.io.tmpdir")+maze.toString().hashCode()+"MazeTest";
            File my_file = new File(file_path);
            System.out.println(my_file.getPath());
            if (!my_file.exists()){
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                ASearchingAlgorithm searchingAlgorithm = Configurations.getMazeSolverMethod();
                Solution solution = searchingAlgorithm.solve(searchableMaze);
                toClient.flush();
                toClient.writeObject(solution);
                FileOutputStream fileOutput = new FileOutputStream(file_path);
                ObjectOutputStream maze_solved = new ObjectOutputStream(fileOutput);
                maze_solved.writeObject(solution);
                maze_solved.close();
            }
            else {
                FileInputStream fileInput = new FileInputStream(file_path);
                ObjectInputStream maze_solved = new ObjectInputStream(fileInput);
                toClient.writeObject(maze_solved.readObject());
                maze_solved.close();
                fileInput.close();
            }

            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

