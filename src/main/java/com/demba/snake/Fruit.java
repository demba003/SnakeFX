package com.demba.snake;


import javafx.scene.paint.Color;

import java.util.Random;

class Fruit {
    private int sizeX = 31, sizeY = 22;
    private Point position;
    private CollisionModel collisionModel;
    private Color color;

    Fruit(CollisionModel collisionModel, Color color){
        this.collisionModel = collisionModel;
        this.color = color;
        eat();
    }

    void eat() {
        Random generator = new Random();
        while (true){
            Point p = new Point(generator.nextInt(sizeX - 1), generator.nextInt(sizeY - 1));
            if (!collisionModel.isColliding(p)){
                position = p;
                break;
            }

        }
    }

    Point getPosition() {
        return position;
    }

    Color getColor() {
        return color;
    }
}
