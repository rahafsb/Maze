package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import Server.ServerStrategySolveSearchProblem;
import Server.Configurations;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public class MyModel extends Observable implements IModel{

    int[] dimensions;
    Client client;
    Maze maze;
    Solution solution;
    Server mazeGeneratingServer;
    Server solveSearchProblemServer;
    Position player;

    private static final Logger logger = LogManager.getLogger(MyModel.class);

    @Override
    public void assignObserver(Observer o){
        this.addObserver(o);
    }

    public MyModel(){
        dimensions = new int[2];
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        logger.info("server for generating maze started at port 5400 and is ready to accept clients");
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        logger.info("server for solving maze started at port 5401 and is ready to accept clients");

        player = new Position(1, 1);


    }

    public void startClientStrategyGenerate(){
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        logger.info("client connected to server - IP " + InetAddress.getLocalHost() + ", Port = " + 5400);
                        logger.info("client is asking for generating maze with the algorithm: " + Configurations.getMazeGeneratorMethod());
                        logger.info("Client is asking for generating a maze with rows " + dimensions[0] + " and columns " + dimensions[1]);
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{dimensions[0], dimensions[1]};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[dimensions[0]*dimensions[1]+24];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                    } catch (IOException e) {
                        logger.error("IOException", e);
                    } catch (ClassNotFoundException e){
                        logger.error("ClassNotFoundException", e);
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            logger.error("UnknownHostException", e);
        }
    }

    @Override
    public void generateMaze(int x, int y){
        dimensions[0] = x;
        dimensions[1] = y;
        startClientStrategyGenerate();
        logger.info("Client accepted a maze from the server successfully");
        player.setX(this.getStartPosition().getRowIndex());
        player.setY(this.getStartPosition().getColumnIndex());
    }

    public void startClientStrategySolver(){
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        logger.info("client connected to server - IP " + InetAddress.getLocalHost() + ", Port = " + 5401);
                        logger.info("Client is asking for solving a maze with the algorithm: " + Configurations.getMazeSolverMethod());
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        Solution mazeSolution = (Solution)fromServer.readObject();
                        solution = mazeSolution;
                    } catch (IOException e) {
                        logger.error("IOException", e);
                    } catch (ClassNotFoundException e){
                        logger.error("ClassNotFoundException", e);
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            logger.error("UnknownHostException", e);
        }
    }

    @Override
    public void solveMaze() {
        if (maze != null){
            startClientStrategySolver();
            logger.info("Client accepted a solution for the maze from the server");
            setChanged();
            notifyObservers("maze solved");
        }
    }

    @Override
    public Solution returnSolution() {
        return this.solution;
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    @Override
    public void saveMaze(File file) {
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(file));
            maze.getStartPosition().setX(player.getRowIndex());
            maze.getStartPosition().setY(player.getColumnIndex());
            objectOutput.writeObject(maze.toByteArray());
            objectOutput.flush();
            objectOutput.close();
            logger.info("Maze is saved successfully in the following file path: " + file.getPath());
        } catch (IOException e) {
            logger.error("IOException e");
        }
    }

    @Override
    public void loadMaze(File file) {
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(file));
            byte[] loadedMaze = (byte[]) objectIn.readObject();
            objectIn.close();
            maze = new Maze(loadedMaze);
            player.setX(maze.getStartPosition().getRowIndex());
            player.setY(maze.getStartPosition().getColumnIndex());
            logger.info("Maze is loaded successfully from the file path: " + file.getPath());
        } catch (IOException e) {
            logger.error("IOException", e);
        } catch (ClassNotFoundException e){
            logger.error("ClassNotFoundException", e);
        }

    }

    @Override
    public void serversStop() {
        this.mazeGeneratingServer.stop();
        this.solveSearchProblemServer.stop();
        logger.info("servers who was working is stopped from working, and the game has been exited successfully");
    }

    @Override
    public Position getStartPosition() {
        return this.maze.getStartPosition();
    }

    @Override
    public Position getGoalPosition() {
        return this.maze.getGoalPosition();
    }

    @Override
    public Position getPlayerPosition() {
        return this.player;
    }

    public void updatePlayerPosition(String s){
        int tempRow = this.player.getRowIndex();
        int tempCol = this.player.getColumnIndex();

        switch(s){
            case "DOWNLEFT" -> {
                if (maze.get_value(player.getRowIndex(), player.getColumnIndex()-1) == 0 || maze.get_value(player.getRowIndex() + 1, player.getColumnIndex()) == 0){
                    tempRow++;
                    tempCol--;
                }
            }
            case "DOWN" ->{
                tempRow++;
            }
            case "DOWNRIGHT" -> {
                if (maze.get_value(player.getRowIndex(), player.getColumnIndex()+1) == 0 || maze.get_value(player.getRowIndex() + 1, player.getColumnIndex()) == 0){
                    tempRow++;
                    tempCol++;
                }
            }
            case "LEFT" ->{
                tempCol--;
            }
            case "RIGHT" -> {
                tempCol++;
            }
            case "UPLEFT" -> {
                if (maze.get_value(player.getRowIndex(), player.getColumnIndex()-1) == 0 || maze.get_value(player.getRowIndex() - 1, player.getColumnIndex()) == 0){
                    tempRow--;
                    tempCol--;
                }
            }
            case "UP" -> {
                tempRow--;
            }
            case "UPRIGHT" -> {
                if (maze.get_value(player.getRowIndex(), player.getColumnIndex()+1) == 0 || maze.get_value(player.getRowIndex() - 1, player.getColumnIndex()) == 0){
                    tempRow--;
                    tempCol++;
                }
            }
        }

        if (maze.get_value(tempRow, tempCol) ==0){
            this.player.setX(tempRow);
            this.player.setY(tempCol);

            this.maze.getStartPosition().setX(tempRow);
            this.maze.getStartPosition().setY(tempCol);

            setChanged();
            notifyObservers("player moved");
            if (tempRow == this.getGoalPosition().getRowIndex() && tempCol == this.getGoalPosition().getColumnIndex()){
                setChanged();
                notifyObservers("goal reached");
            }
        }
    }

    @Override
    public void exit() {
        serversStop();
    }
}
