package com.demba.snake;


import java.util.ArrayList;

class Level{
    private ArrayList<Point> map;
    private CollisionModel collisionModel;

    Level(CollisionModel collisionModel){
        this.collisionModel = collisionModel;
        map = new ArrayList<>();
        load();
    }

    void load(){
        for (int i = 0; i < 10; i++) {
            map.add(new Point(0,i));
            map.add(new Point(i,0));
        }
        for (Point point : map) {
            collisionModel.set(point, true);
        }
    }

    ArrayList<Point> getMap() {
        return map;
    }
}
