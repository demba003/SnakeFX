package com.demba.snake;

import javafx.scene.paint.Color;

import java.util.*;

class SnakeModel {
    private LinkedList<Point> body;
    private Color bodyColor;
    private int sizeX = 31, sizeY = 22;
    private CollisionModel collisionModel;
    private Direction currentDirection;
    Thread movementThread;
    int speed = 150;

    SnakeModel(Point initialPosition, Color bodyColor, CollisionModel collisionModel) {
        this.bodyColor = bodyColor;
        this.body = new LinkedList<>();
        this.body.add(initialPosition);
        this.collisionModel = collisionModel;
        movementThread = new Thread(new SnakeMovement());
        movementThread.start();
    }

    void setCurrentDirection(Direction direction) {
        currentDirection = direction;
    }

    private void move(Direction direction){
        int newX = body.getLast().getX();
        int newY = body.getLast().getY();

        switch (direction) {
            case UP:
                if (body.getLast().getY() - 1 < 0)
                    newY = sizeY - 1;
                else
                    newY = newY - 1;
                break;
            case DOWN:
                if (body.getLast().getY() + 1 > sizeY - 1)
                    newY = 0;
               else
                    newY = newY + 1;
                break;
            case LEFT:
                if (body.getLast().getX() - 1 < 0)
                    newX = sizeX - 1;
                else
                    newX = newX - 1;
                break;
            case RIGHT:
                if (body.getLast().getX() + 1 > sizeX - 1)
                    newX = 0;
                else
                    newX = newX + 1;
                break;
        }

        if (!collisionModel.isColliding(new Point(newX, newY))) {
            body.add(new Point(newX, newY));
            if(!(newX==5 && newY==0)) body.removeFirst();
        } else {
            System.out.println("KONIEC");
            movementThread.interrupt();
        }
    }

    LinkedList<Point> getBody(){
        return body;
    }

    Color getBodyColor() {
        return bodyColor;
    }

    private class SnakeMovement implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    break;
                }
                if(currentDirection != null) move(currentDirection);
            }
        }
    }
}

