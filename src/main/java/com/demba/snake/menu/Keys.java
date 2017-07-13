package com.demba.snake.menu;


import javafx.scene.input.KeyCode;

public class Keys {
    public KeyCode UP;
    public KeyCode DOWN;
    public KeyCode LEFT;
    public KeyCode RIGHT;

    Keys(KeyCode up, KeyCode down, KeyCode left, KeyCode right){
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
