package com.demba.snake.game;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.*;


class SnakeModel extends Task<Void> {
    private LinkedList<Point> body;
    private Color bodyColor;
    private int sizeX = 31, sizeY = 22;
    private CollisionModel collisionModel;
    private Direction currentDirection;
    private Thread movementThread;
    private int speed = 110;
    private Fruit fruit;
    private Point removedBlock;
    private Keys keys;
    private boolean pendingDirectionChange = false;
    private boolean cheatmode = false;
    //private InputStreamReader reader;

    SnakeModel(Point initialPosition, Color bodyColor, CollisionModel collisionModel, Fruit fruit, Keys keys){
        this.bodyColor = bodyColor;
        this.body = new LinkedList<>();
        this.keys = keys;

        /*try {
            reader = new InputStreamReader(new FileInputStream("file.txt"), "ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

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

    private Direction byte2Direction(int value){
        Direction direction = null;
        switch (value){
            case 48:
                direction = Direction.UP;
                break;
            case 49:
                direction = Direction.DOWN;
                break;
            case 50:
                direction = Direction.LEFT;
                break;
            case 51:
                direction = Direction.RIGHT;
                break;
        }
        return direction;
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

            /*try {
                Direction direction = byte2Direction(reader.read());
                if (direction != null) currentDirection = direction;
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            if(currentDirection != null) move(currentDirection);
            pendingDirectionChange = false;
            updateMessage(String.valueOf(body.size()));
        }
        return null;
    }

    void handleKeys(KeyCode key) {
        if(key == keys.UP
                && !pendingDirectionChange
                && currentDirection != Direction.DOWN)
            currentDirection = Direction.UP;
        else if(key == keys.DOWN
                && !pendingDirectionChange
                && currentDirection != Direction.UP)
            currentDirection = Direction.DOWN;
        else if(key == keys.LEFT
                && !pendingDirectionChange
                && currentDirection != Direction.RIGHT)
            currentDirection = Direction.LEFT;
        else if(key == keys.RIGHT
                && !pendingDirectionChange
                && currentDirection != Direction.LEFT)
            currentDirection = Direction.RIGHT;
        pendingDirectionChange = true;
    }
}

