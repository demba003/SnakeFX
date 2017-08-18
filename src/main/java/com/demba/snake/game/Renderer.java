package com.demba.snake.game;


import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;

class Renderer implements Runnable {
    private ArrayList<SnakeModel> snakes;
    private BoardPane board;
    private int sleep;
    private Fruit[] fruits;

    Renderer(BoardPane board, Fruit[] fruits, ArrayList<SnakeModel> snakes, Level level) {
        this.board = board;
        this.sleep = (int)(1.0/300*1000);
        this.fruits = fruits;
        this.snakes = snakes;
        renderLevel(level);
    }

    private void renderLevel(Level level){
        for (Point point : level.getMap()) {
            board.draw(point, Color.BLACK);
        }
    }

    private void render(){
        for (Fruit fruit : fruits) {
            board.draw(fruit.getPosition(), fruit.getColor());
        }

        for (SnakeModel snake : snakes) {
            LinkedList<Point> body = snake.getBody();
            board.draw(body.getLast(), snake.getBodyColor());
            if(snake.getRemovedBlock() != null) board.remove(snake.getRemovedBlock());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                break;
            }
            this.render();
        }
    }
}
