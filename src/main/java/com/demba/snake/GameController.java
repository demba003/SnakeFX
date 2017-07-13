package com.demba.snake;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, EventHandler<KeyEvent>{
    @FXML
    private Pane boardPaneHolder;
    @FXML
    private Label score;

    private SnakeModel snake, snake2;
    private Thread rendererThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BoardPane board = new BoardPane();
        boardPaneHolder.getChildren().add(board);
        boardPaneHolder.setFocusTraversable(true);
        boardPaneHolder.setOnKeyPressed(this);

        CollisionModel collisionModel = new CollisionModel();
        Level level = new Level(collisionModel);
        Fruit fruit = new Fruit(collisionModel, Color.RED);

        snake = new SnakeModel(new Point(4,4), Color.ORANGE, collisionModel, fruit);
        snake2 = new SnakeModel(new Point(10,10), Color.GREEN, collisionModel, fruit);

        SnakeModel snakes[] = {snake, snake2};
        Fruit[] fruits = {fruit};

        rendererThread = new Thread(new Renderer(board, fruits, snakes, level));
        rendererThread.start();
        score.textProperty().bind(snake.messageProperty());
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                snake.setCurrentDirection(Direction.UP);
                break;
            case DOWN:
                snake.setCurrentDirection(Direction.DOWN);
                break;
            case LEFT:
                snake.setCurrentDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setCurrentDirection(Direction.RIGHT);
                break;

            case W:
                snake2.setCurrentDirection(Direction.UP);
                break;
            case S:
                snake2.setCurrentDirection(Direction.DOWN);
                break;
            case A:
                snake2.setCurrentDirection(Direction.LEFT);
                break;
            case D:
                snake2.setCurrentDirection(Direction.RIGHT);
                break;
        }
    }

    void onWindowClose() {
        System.out.println("On close");
        snake.endMovement();
        snake2.endMovement();
        rendererThread.interrupt();
    }
}
