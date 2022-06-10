package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.util.Properties;

public class Configurations {
    private static Configurations instance;
    private static Properties properties = new Properties();
    private static InputStream inputStream;
    private OutputStream outputStream;

    public static Configurations getInstance(){
        if (instance == null){
            try{
                instance = new Configurations();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static AMazeGenerator getMazeGeneratorMethod() throws IOException {
        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        String generateMethod = properties.getProperty("mazeGeneratingAlgorithm");

        if (generateMethod.equals("EmptyMazeGenerator")){
            return new EmptyMazeGenerator();
        }

        if (generateMethod.equals("SimpleMazeGenerator")){
            return new SimpleMazeGenerator();
        }

        else return new MyMazeGenerator();
    }

    public static ASearchingAlgorithm getMazeSolverMethod() throws IOException {
        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        String generateMethod = properties.getProperty("mazeSearchingAlgorithm");

        if (generateMethod.equals("DepthFirstSearch")){
            return new DepthFirstSearch();
        }

        if (generateMethod.equals("BreadthFirstSearch")){
            return new BreadthFirstSearch();
        }

        else return new BestFirstSearch();
    }

    public static int numOfThreads() throws IOException{
        try{
            inputStream = new FileInputStream("resources/config.properties");
            properties.load(inputStream);
            String numOfThreads = properties.getProperty("threadPoolSize");
            return Integer.parseInt(numOfThreads);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return 5;
    }

    private Configurations() throws IOException {
        outputStream = new FileOutputStream("resources/config.properties");
        properties.setProperty(("threadPoolSize"), ("10"));
        properties.setProperty(("mazeGeneratingAlgorithm"), ("MyMazeGenerator"));
        properties.setProperty(("mazeSearchingAlgorithm"), ("BestFirstSearch"));
        properties.store(outputStream, null);
    }


}
