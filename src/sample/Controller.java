package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

import static sample.KeyboardManager.keys;
import static sample.KeyboardManager.keysPressed;
import static sample.KeyboardManager.keysPressed;


public class Controller {

    @FXML
    Canvas canvas;

    private char lastDir;
    private int[][] map;
    private int snakeLength;
    private ArrayList<Pos> posMap;
    private GraphicsContext g;
    private Pos apple;
    private int mapsize;



    private KeyFrame key = new KeyFrame(Duration.millis(75), event -> {
        tick();
        render();
    });
    private Timeline time;

    private void tick() {
        if(keysPressed.contains("A") && lastDir != 'D') {
            lastDir = 'A';
        } else if (keysPressed.contains("D") && lastDir != 'A') {
            lastDir = 'D';
        } else if (keysPressed.contains("S") && lastDir != 'W') {
            lastDir = 'S';
        } else if (keysPressed.contains("W") && lastDir != 'S') {
            lastDir = 'W';
        }
        updateSnake();
        updateMap();
    }
    //optimal would be to render the position, and not check each cell to see if its one, to eliminate the map array entirely. someone add this
    private void render() {
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < map.length; i++) {
            for(int k = 0; k < map[0].length; k++) {
                if(map[i][k] == 1) {
                    g.setFill(Color.GREEN);
                    g.fillRect(i * 10, k * 10, 10, 10);
                } if(map[i][k] == 2) {
                    g.setFill(Color.RED);
                    g.fillRect(i*10, k*10, 10, 10);
                }
            }
        }
    }

    public void updateSnake() {
        Pos first = posMap.get(0);
        Pos last = new Pos(posMap.get(posMap.size()-1).getPos());
        if(lastDir == 'A') first.setPos(first.getPos()[0] -1, first.getPos()[1] );
        if(lastDir == 'D') first.setPos(first.getPos()[0] +1, first.getPos()[1] );
        if(lastDir == 'S') first.setPos(first.getPos()[0], first.getPos()[1] +1);
        if(lastDir == 'W') first.setPos(first.getPos()[0] , first.getPos()[1] -1);

        for(int i = posMap.size()-1; i > 0; i--) {
            posMap.set(i, new Pos(posMap.get(i -1).getPos()));
        }
        if(apple.getPos().equals(first.getPos())) {
            posMap.add(last);
            apple = new Pos(new int[]{(int)(Math.random() * mapsize), (int)(Math.random() * mapsize)});
            map[apple.getX()][apple.getY()] = 2;
        }
    }

    public void updateMap() {
        map = new int[mapsize][mapsize];
        for(int i = 0; i < posMap.size(); i++) {
            if(posMap.get(i).getX() >= map.length) throw new RuntimeException("posMap.get(" + i + ").getX() greater than map depth");
            if(posMap.get(i).getY() >= map[0].length) throw new RuntimeException("posMap.get(" + i + ").getY() greater than map length");
            if(posMap.get(i).getX() < 0) throw new RuntimeException("posMap.get(" + i + ").getX() less than 0");
            if(posMap.get(i).getY() < 0) throw new RuntimeException("posMap.get(" + i + ").getX() less than 0");
            map[posMap.get(i).getX()][posMap.get(i).getY()] = 1;
        }
    }

    public void onKeyPressed(KeyEvent event) {
        //System.out.println(event.getCode().toString());
        keysPressed.add(event.getCode().toString());

    }

    public void onKeyReleased(KeyEvent event) {
        keysPressed.remove(event.getCode().toString());
    }

    public void initialize() {
        //System.out.println("Init ran");
        mapsize = 50;
        g = canvas.getGraphicsContext2D();
        map = new int[mapsize][mapsize];
        time = new Timeline(key);
        time.setCycleCount(Timeline.INDEFINITE);
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        lastDir = 'W';
        snakeLength = 1;
        posMap = new ArrayList<Pos>();
        posMap.add(new Pos(24, 24));
        posMap.add(new Pos(24, 25));
        posMap.add(new Pos(24, 26));
        posMap.add(new Pos(24, 27));
        int[] c= new int[]{(int)(Math.random() * mapsize), (int)(Math.random() * mapsize)};
        apple = new Pos(c);
        map[c[0]][c[1]] = 2;
        time.play();
    }

}
