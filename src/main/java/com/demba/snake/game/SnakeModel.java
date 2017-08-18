package com.demba.snake.game;


import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;


class SnakeModel extends Task<Void> {
    private LinkedList<Point> body;
    private Color bodyColor;
    private int sizeX = 31, sizeY = 22;
    private CollisionModel collisionModel;
    private Direction currentDirection;
    public int directionCode = 0;
    private Thread movementThread;
    private int speed = 110;
    private Fruit fruit;
    private Point removedBlock;
    private Keys keys;
    private boolean pendingDirectionChange = false;
    private boolean cheatmode = false;

    SnakeModel(Point initialPosition, Color bodyColor, CollisionModel collisionModel, Fruit fruit, Keys keys){
        this.bodyColor = bodyColor;
        this.body = new LinkedList<>();
        if (keys != null)
            this.keys = keys;

        if (initialPosition != null) {
            this.body.add(initialPosition);
            collisionModel.set(initialPosition, true);
        }
        else {
            Random generator = new Random();
            while (true){
                Point p = new Point(generator.nextInt(sizeX - 1), generator.nextInt(sizeY - 1));
                if (!collisionModel.isColliding(p)){
                    this.body.add(p);
                    collisionModel.set(p, true);
                    break;
                }
            }
        }


        this.collisionModel = collisionModel;
        this.fruit = fruit;
        movementThread = new Thread(this);
        movementThread.start();
    }

    synchronized private void move(Direction direction){
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
            collisionModel.set(new Point(newX, newY), true);
            if(!(newX == fruit.getPosition().getX() && newY == fruit.getPosition().getY())) {
                collisionModel.set(body.getFirst(), false);
                removedBlock = body.getFirst();
                body.removeFirst();
            } else {
                fruit.eat();
            }
       } else {

            System.out.println("KONIEC");

            removedBlock = null;
            if (!cheatmode) movementThread.interrupt();
       }
    }

    synchronized LinkedList<Point> getBody(){
        return body;
    }

    Color getBodyColor() {
        return bodyColor;
    }

    Point getRemovedBlock() {
        return removedBlock;
    }

    int getCurrentDirectionCode() {
        int value = directionCode;
        directionCode = 0;
        return value;
    }

    void endMovement() {
        movementThread.interrupt();
    }

    @Override
    public Void call() {
        while (true) {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                break;
            }

            if(currentDirection != null) move(currentDirection);
            pendingDirectionChange = false;
            updateMessage(String.valueOf(body.size()));
        }
        return null;
    }

    void handleKeys(KeyCode key) {
        if (keys != null) {
            if(key == keys.UP
                    && !pendingDirectionChange
                    && currentDirection != Direction.DOWN)
            {
                currentDirection = Direction.UP;
                directionCode = 1;
            }

            else if(key == keys.DOWN
                    && !pendingDirectionChange
                    && currentDirection != Direction.UP)
            {
                currentDirection = Direction.DOWN;
                directionCode = 2;
            }
            else if(key == keys.LEFT
                    && !pendingDirectionChange
                    && currentDirection != Direction.RIGHT)
            {
                currentDirection = Direction.LEFT;
                directionCode = 3;
            }
            else if(key == keys.RIGHT
                    && !pendingDirectionChange
                    && currentDirection != Direction.LEFT)
            {
                currentDirection = Direction.RIGHT;
                directionCode = 4;
            }
            pendingDirectionChange = true;
        }
    }

    public void handleNetworkCode(int value) {
        if(value == 1
                && !pendingDirectionChange
                && currentDirection != Direction.DOWN)
            currentDirection = Direction.UP;
        else if(value == 2
                && !pendingDirectionChange
                && currentDirection != Direction.UP)
            currentDirection = Direction.DOWN;
        else if(value == 3
                && !pendingDirectionChange
                && currentDirection != Direction.RIGHT)
            currentDirection = Direction.LEFT;
        else if(value == 4
                && !pendingDirectionChange
                && currentDirection != Direction.LEFT)
            currentDirection = Direction.RIGHT;
        pendingDirectionChange = true;
    }
}

