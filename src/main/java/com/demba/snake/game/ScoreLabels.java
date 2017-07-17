package com.demba.snake.game;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

class ScoreLabels extends HBox{
    private ArrayList<Label> labels;

    ScoreLabels(int count) {
        labels = new ArrayList<>();

        for (int i = 0; i< count; i++) {
            Label player = new Label("Player " + (i + 1) + " score: ");
            player.setFont(new Font(14.0));
            Label score = new Label("1");
            score.setFont(new Font(14.0));
            score.setPadding(new Insets(0,20,0,0));
            labels.add(score);
            this.getChildren().addAll(player, score);
        }
    }

    ArrayList<Label> getScoreLabels() {
        return labels;
    }
}
