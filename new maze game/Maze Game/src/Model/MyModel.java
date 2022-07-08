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

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;


public class MyModel extends Observable implements IModel{

    int[] dimensions;
    Client client;
    Maze maze;
    Solution solution;
    Server mazeGeneratingServer;
    Server solveSearchProblemServer;
    Position player;

    @Override
    public void assignObserver(Observer o){
        this.addObserver(o);
    }

    public MyModel(){
        dimensions = new int[2];
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();

        player = new Position(1, 1);
    }

    public void startClientStrategyGenerate(){
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateMaze(int x, int y){
        dimensions[0] = x;
        dimensions[1] = y;
        startClientStrategyGenerate();
        player.setX(this.getStartPosition().getRowIndex());
        player.setY(this.getStartPosition().getColumnIndex());
    }

    public void startClientStrategySolver(){
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        Solution mazeSolution = (Solution)fromServer.readObject();
                        solution = mazeSolution;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void solveMaze() {
        if (maze != null){
            startClientStrategySolver();
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

        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void serversStop() {
        this.mazeGeneratingServer.stop();
        this.solveSearchProblemServer.stop();
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
