package com.demba.snake;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


class BoardPane extends Pane {
    private int sizeX = 31, sizeY = 22;
    private Rectangle[][] rectangles = new Rectangle[sizeY][sizeX];

    BoardPane() {
        for (int row = 0; row < sizeY; row++) {
            for (int column = 0; column < sizeX; column++){
                rectangles[row][column] = new Rectangle(25,25);
                rectangles[row][column].setFill(Color.TRANSPARENT);
                rectangles[row][column].setStroke(Color.TRANSPARENT);
                rectangles[row][column].setStrokeType(StrokeType.CENTERED);
                rectangles[row][column].setSmooth(false);
                rectangles[row][column].setX(column * 25);
                rectangles[row][column].setY(row * 25);
            }
        }

        for (int row = 0; row < sizeY; row++){
            this.getChildren().addAll(rectangles[row]);
        }
    }

    void draw(Point point, Color color) {
        rectangles[point.getY()][point.getX()].setFill(color);
        rectangles[point.getY()][point.getX()].setStroke(Color.BLACK);
    }

    void remove(Point point) {
        rectangles[point.getY()][point.getX()].setFill(Color.TRANSPARENT);
        rectangles[point.getY()][point.getX()].setStroke(Color.TRANSPARENT);
    }

    int getSizeX() {
        return sizeX;
    }

    int getSizeY() {
        return sizeY;
    }
}