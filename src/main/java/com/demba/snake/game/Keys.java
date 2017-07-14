package com.demba.snake.game;


import javafx.scene.input.KeyCode;

public class Keys {
    KeyCode UP;
    KeyCode DOWN;
    KeyCode LEFT;
    KeyCode RIGHT;

    public Keys(KeyCode up, KeyCode down, KeyCode left, KeyCode right){
        this.UP = up;
        this.DOWN = down;
        this.LEFT = left;
        this.RIGHT = right;
    }

    @Override
    public String toString() {
        return UP.toString() + " " + DOWN.toString() + " " + LEFT.toString() + " " + RIGHT.toString();
    }
}
