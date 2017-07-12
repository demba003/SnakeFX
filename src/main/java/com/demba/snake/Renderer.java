package com.demba.snake;


import javafx.scene.paint.Color;

class Renderer implements Runnable {
    private SnakeModel[] snakes;
    private BoardPane board;
    private int sleep;
    private CollisionModel collisionModel;

    Renderer(BoardPane board, int fps, CollisionModel collisionModel, SnakeModel...snakes) {
        this.board = board;
        this.snakes = snakes;
        this.sleep = (int)(1.0/fps*1000);
        this.collisionModel = collisionModel;
    }

    private void render(){
        for (int row = 0; row < board.getSizeY(); row++) {
            for (int column = 0; column < board.getSizeX(); column++){
                board.remove(new Point(column, row));
            }
        }
        for (SnakeModel snake : snakes){
            for (Point point : snake.getBody()) {
                board.draw(point, snake.getBodyColor());
            }
        }

        /*for (int row = 0; row < board.getSizeY(); row++) {
            for (int column = 0; column < board.getSizeX(); column++){
                if (collisionModel.isColliding(new Point(column, row))) board.draw(new Point(column, row), Color.BLACK);
            }
        }*/
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.render();
        }
    }
}
