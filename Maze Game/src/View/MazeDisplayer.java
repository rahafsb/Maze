package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas{

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameGoal= new SimpleStringProperty();
    StringProperty imageFileNameTran = new SimpleStringProperty();


    private Maze maze;
    private Position player;
    private double cellHeight;
    private double cellWidth;



    //getters

    public double getCellWidth(){
        return cellWidth;
    }

    public double getCellHeight(){
        return cellHeight;
    }
    public Position getPlayer() {
        return player;
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public StringProperty imageFileNameWallProperty() {
        return imageFileNameWall;
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String getImageFileNameTran(){
        return imageFileNameTran.get();
    }

    public StringProperty imageFileNamePlayerProperty() {
        return imageFileNamePlayer;
    }

    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public StringProperty imageFileNameGoalProperty() {
        return imageFileNameGoal;
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNameGoal(String imageFileNameGoal){this.imageFileNameGoal.set(imageFileNameGoal);}

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void setPosition(int row, int col){
        player.setX(row);
        player.setY(col);
        draw();
    }

    public void drawMaze(Maze maze){
        if (maze == null){
            return;
        }
        this.maze = maze;
        this.player = new Position(this.maze.getStartPosition().getRowIndex(), this.maze.getStartPosition().getColumnIndex());

        this.imageFileNameWall.set("resources/images/sand.png");
        this.imageFileNamePlayer.set("resources/images/ship.jpeg");
        this.imageFileNameGoal.set("resources/images/chest.jpeg");
        this.imageFileNameTran.set("resources/images/water.jpeg");
        draw();
    }

    private void draw(){
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.get_rows_cols()[0];
            int cols = maze.get_rows_cols()[1];

            cellHeight = canvasHeight / rows;
            cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
            drawGoal(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.RED);

        Image wallImage = null;
        Image TranImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            TranImage = new Image(new FileInputStream(getImageFileNameTran()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(maze.get_value(i, j) == 1){
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
                else{
                    //if not a wall
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(TranImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(TranImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = player.getColumnIndex() * cellWidth;
        double y = player.getRowIndex() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

    private void drawGoal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        /*draw a goalImage by the sizes of the cell and the goal location */
        Image goalImage = null;
        try {
            goalImage = new Image(new FileInputStream(getImageFileNameGoal()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no goal image");
        }
        graphicsContext.setFill(Color.GREEN);
        double x = this.maze.getGoalPosition().getColumnIndex()*cellWidth;
        double y = this.maze.getGoalPosition().getRowIndex()*cellHeight;
        if(goalImage==null)
            graphicsContext.fillRect(x,y,cellWidth,cellHeight);
        else
            graphicsContext.drawImage(goalImage,x,y,cellWidth,cellHeight);
    }

    public void drawSolution(Solution solution) {
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        int rows = maze.get_rows_cols()[0];
        int cols = maze.get_rows_cols()[1];
        cellHeight = canvasHeight/rows;
        cellWidth = canvasWidth/cols;
        GraphicsContext graphicsContext =  getGraphicsContext2D();
        graphicsContext.clearRect(0,0,canvasWidth,canvasHeight);

        drawMazeWalls(graphicsContext,cellHeight,cellWidth,rows, cols);
        drawGoal(graphicsContext,cellHeight,cellWidth);
        drawPlayer(graphicsContext,cellHeight,cellWidth);

        graphicsContext.setFill(Color.BLACK);
        for(int i = 1; i < solution.getSolutionPath().size()-1; i++){
            double x = ((MazeState)solution.getSolutionPath().get(i)).getAt().getColumnIndex() * cellWidth;
            double y = ((MazeState)solution.getSolutionPath().get(i)).getAt().getRowIndex() * cellHeight;
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        }
    }
}
