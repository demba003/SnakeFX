package com.demba.snake;


import com.demba.snake.game.GameController;
import com.demba.snake.menu.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("menu.fxml"));
        MenuController menuController = new MenuController();
        fxmlLoader.setController(menuController);
        Parent root = fxmlLoader.load();

        primaryStage.setScene(new Scene(root, 470, 210));
        primaryStage.setOnCloseRequest(e -> menuController.onWindowClose());

        primaryStage.show();
    }
}
