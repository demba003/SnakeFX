package com.demba.snake;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("game.fxml"));
        primaryStage.setTitle("Snake");
        primaryStage.setScene(new Scene(root, 790, 590));
        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
