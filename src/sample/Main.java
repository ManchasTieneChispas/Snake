package sample;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        /*
        scene.setOnKeyPressed(event -> {
            int keyCode = (int) event.getCode().toString().toCharArray()[0]; //key code
            KeyboardManager.keys[keyCode] = true;
        });
        scene.setOnKeyReleased(event -> {
            int keyCode = (int) event.getCode().toString().toCharArray()[0]; //key code
            KeyboardManager.keys[keyCode] = false;
        });
        */
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
