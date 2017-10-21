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


public class Controller {

    @FXML
    Canvas canvas;

    char lastDir;
    private int[][] map;
    private int snakeLength;
    private ArrayList<Pos> posMap;
    private GraphicsContext g = canvas.getGraphicsContext2D();


    KeyFrame key = new KeyFrame(Duration.millis(100), event -> {
        tick();
        render();
    });
    Timeline time = new Timeline(key);

    public void tick() {
        if(keys['A']) {
            lastDir = 'A';
        } else if (keys['D']) {
            lastDir = 'D';
        } else if (keys['S']) {
            lastDir = 'S';
        } else if (keys['W']) {
            lastDir = 'W';
        }
        updateSnake();
        updateMap();
    }

    public void render() {
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < map.length; i++) {
            for(int k = 0; i < map[0].length; k++) {
                g.setFill(Color.RED);
                if(map[i][k] ==1) {
                    g.fillRect(i * 10, k * 10, 10, 10);
                }
            }
        }
    }

    public void updateSnake() {
        for(int i = posMap.size()-1; i > 0; i--) {
            posMap.set(i, new Pos(posMap.get(i -1).getPos()));
        }
        Pos first = posMap.get(0);
        if(lastDir == 'A') first.setPos(first.getPos()[0] , first.getPos()[1] -1);
        if(lastDir == 'D') first.setPos(first.getPos()[0], first.getPos()[1] + 1);
        if(lastDir == 'S') first.setPos(first.getPos()[0] +1, first.getPos()[1]);
        if(lastDir == 'W') first.setPos(first.getPos()[0] -1, first.getPos()[1]);
    }

    public void updateMap() {
        map = new int[map.length][map[0].length];
        for(int i = 0; i < posMap.size(); i++) {
            map[posMap.get(i).getX()][posMap.get(i).getY()] = 1;
        }
    }

    public void onKeyPressed(KeyEvent event) {
        int keyCode = (int) event.getCode().toString().toCharArray()[0]; //key code
        keys[keyCode] = true;
    }

    public void onKeyReleased(KeyEvent event) {
        int keyCode = (int) event.getCode().toString().toCharArray()[0]; //key code
        keys[keyCode] = false;
    }

    public void initialize() {
        map = new int[50][50];
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        lastDir = 'W';
        snakeLength = 1;
        posMap = new ArrayList<Pos>();
        posMap.add(new Pos(250, 250));
    }

}
