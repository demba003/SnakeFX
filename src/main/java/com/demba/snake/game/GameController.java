package com.demba.snake.game;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable, EventHandler<KeyEvent>{
    @FXML
    private Pane boardPaneHolder;
    @FXML
    private Label score;

    private Thread rendererThread;
    private ArrayList<SnakeModel> snakes;
    private int snakeCount;
    private ArrayList<SnakeParams> snakeParams;

    public GameController(int snakeCount, ArrayList<SnakeParams> snakeParams){
        this.snakeCount = snakeCount;
        this.snakeParams = snakeParams;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BoardPane board = new BoardPane();
        boardPaneHolder.getChildren().add(board);
        boardPaneHolder.setFocusTraversable(true);
        boardPaneHolder.setOnKeyPressed(this);
        snakes = new ArrayList<>();

        CollisionModel collisionModel = new CollisionModel();
        Level level = new Level(collisionModel);
        Fruit fruit = new Fruit(collisionModel, Color.RED);
        Fruit[] fruits = {fruit};

        for (int i=0; i < snakeCount; i++) {
            snakes.add(new SnakeModel(null, snakeParams.get(i).COLOR, collisionModel, fruit, snakeParams.get(i).KEYS));
        }

        rendererThread = new Thread(new Renderer(board, fruits, snakes, level));
        rendererThread.start();

        score.textProperty().bind(snakes.get(0).messageProperty());
    }

    @Override
    public void handle(KeyEvent event) {
        for (SnakeModel snakeModel : snakes) {
            snakeModel.handleKeys(event.getCode());
        }
    }

    public void onWindowClose() {
        for (SnakeModel snake : snakes) {
            snake.endMovement();
        }
        rendererThread.interrupt();
    }
}
