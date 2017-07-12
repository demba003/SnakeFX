package com.demba.snake;


class CollisionModel {
    private boolean[][] board;
    private int sizeX = 31, sizeY = 22;

    CollisionModel() {
        board = new boolean[sizeY][sizeX];
        for (int row = 0; row < sizeY; row++) {
            for (int column = 0; column < sizeX; column++){
               board[row][column] = false;
            }
        }

        set(new Point(0,0), true);
        set(new Point(30,21), true);
    }

    void set(Point point, boolean present) {
        board[point.getY()][point.getX()] = present;
    }

    boolean isColliding(Point point){
        return board[point.getY()][point.getX()];
    }
}
