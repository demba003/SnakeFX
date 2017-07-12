package com.demba.snake;


import java.util.Random;

class Fruit {
    private int sizeX = 31, sizeY = 22;
    private Point position;
    private CollisionModel collisionModel;

    Fruit(CollisionModel collisionModel){
        this.collisionModel = collisionModel;
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
}
