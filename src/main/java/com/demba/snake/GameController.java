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

    private SnakeModel snake;
    private CollisionModel collisionModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BoardPane board = new BoardPane();
        boardPaneHolder.getChildren().add(board);
        boardPaneHolder.setFocusTraversable(true);
        boardPaneHolder.setOnKeyPressed(this);

        collisionModel = new CollisionModel();

        Fruit fruit = new Fruit(collisionModel);
        snake = new SnakeModel(new Point(5,15), Color.ORANGE, collisionModel, fruit);
        Thread rendererThread = new Thread(new Renderer(board, 25, collisionModel, fruit, snake));
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
        }
    }
}
