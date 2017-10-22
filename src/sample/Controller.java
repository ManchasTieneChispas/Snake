package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static sample.KeyboardManager.keys;
import static sample.KeyboardManager.keysPressed;
import static sample.KeyboardManager.keysPressed;


public class Controller {

    @FXML
    Canvas canvas;
    @FXML
    BorderPane pane;

    private char lastDir;
    private int[][] map;
    private ArrayList<Pos> posMap;
    private GraphicsContext g;
    private Pos apple;
    private int mapsize;
    private Label gg;
    private String score;

    public void fail() {
        time.stop();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.BLACK);
        g.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
        gg = new Label("You have failed :(");
        gg.setTextFill(Color.GREEN);
        pane.setCenter(gg);
        //canvas.setFocusTraversable(false);
        pane.requestFocus();
        System.out.println(pane.isFocused());
    }

    @FXML
    public void typed(KeyEvent event) {
        System.out.println(event.getCode().toString());
        if(pane.getCenter().equals(gg))
        reset();
    }

    public void reset() {
        initialize();
    }

    private KeyFrame key = new KeyFrame(Duration.millis(75), event -> {
        tick();
        render();
    });
    private Timeline time;

    public Controller() {
    }

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
        score = ("Score: " + (posMap.size()-4));
    }
    //optimal would be to render the position, and not check each cell to see if its one, to eliminate the map array entirely. someone add this
    private void render() {
        int counter = 0;
        g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < map.length; i++) {
            for(int k = 0; k < map[0].length; k++) {
                if(map[i][k] == 2) {
                    g.setFill(Color.RED);
                    g.fillRect(i*10, k*10, 10, 10);
                }

                if(map[i][k] == 1) {
                    g.setFill(Color.GREEN);
                    g.fillRect(i * 10, k * 10, 10, 10);
                    counter++;
                }
            }
        }
        g.setFill(Color.BLUE);
        g.fillText(score, 30, 20);
        if(counter != posMap.size()-1) fail();
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
        if(apple.equals(first)) {
            posMap.add(last);
            apple = new Pos(new int[]{(int)(Math.random() * mapsize), (int)(Math.random() * mapsize)});
        }
        //System.out.println(posMap.size());
    }

    public void updateMap() {
        map = new int[mapsize][mapsize];
        for(int i = 0; i < posMap.size(); i++) {
            if(posMap.get(i).getX() >= map.length) fail();
            if(posMap.get(i).getY() >= map[0].length) fail();
            if(posMap.get(i).getX() < 0) fail();
            if(posMap.get(i).getY() < 0) fail();
            map[posMap.get(i).getX()][posMap.get(i).getY()] = 1;
        }
        map[apple.getX()][apple.getY()] = 2;
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
        pane.setCenter(canvas);
        canvas.requestFocus();
        System.out.println(canvas.isFocused());
        System.out.println(pane.isFocused());
        mapsize = (int) (canvas.getHeight()/10);
        g = canvas.getGraphicsContext2D();
        map = new int[mapsize][mapsize];
        time = new Timeline(key);
        time.setCycleCount(Timeline.INDEFINITE);
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        lastDir = 'W';
        posMap = new ArrayList<Pos>();
        posMap.add(new Pos(24, 24));
        posMap.add(new Pos(24, 25));
        posMap.add(new Pos(24, 26));
        posMap.add(new Pos(24, 27));
        int[] c= new int[]{(int)(Math.random() * mapsize), (int)(Math.random() * mapsize)};
        apple = new Pos(c);
        map[c[0]][c[1]] = 2;
        score ="";
        time.play();

    }

}
