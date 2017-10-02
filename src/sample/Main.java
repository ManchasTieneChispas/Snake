package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.BLACK);
        primaryStage.setScene(scene);

        primaryStage.show();
        Group snake = new Group();
        snake.getChildren().add(new Circle
    }


    public static void main(String[] args) {
        launch(args);
    }
}
