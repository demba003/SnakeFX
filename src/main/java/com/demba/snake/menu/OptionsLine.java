package com.demba.snake.menu;

import com.demba.snake.game.Keys;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;


public class OptionsLine extends VBox {
    private ArrayList<ChoiceBox> choiceBoxes;
    private ArrayList<ColorPicker> colorPickers;

    OptionsLine(int count) {
        choiceBoxes = new ArrayList<>();
        colorPickers = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(0,0,5,0));

            Label label = new Label("Player " + (i + 1) + " color: ");
            label.setFont(new Font(14.0));
            label.setPadding(new Insets(0,5,0,0));

            ColorPicker colorPicker = new ColorPicker();
            colorPickers.add(colorPicker);
            colorPicker.setPadding(new Insets(0,10,0,0));

            Label label2 = new Label(" controls:");
            label2.setFont(new Font(14.0));
            label2.setPadding(new Insets(0,5,0,0));

            ChoiceBox<Keys> choice = new ChoiceBox<>();
            Keys defaultkeys = new Keys(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);
            choice.getItems().addAll(
                    defaultkeys,
                    new Keys(KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D),
                    new Keys(KeyCode.I, KeyCode.K, KeyCode.J, KeyCode.L));
            choice.setValue(defaultkeys);
            choiceBoxes.add(choice);

            hBox.getChildren().addAll(label, colorPicker, label2, choice);
            this.getChildren().add(hBox);
        }
    }

    public ArrayList<ChoiceBox> getChoiceBoxes() {
        return choiceBoxes;
    }

    public ArrayList<ColorPicker> getColorPickers() {
        return colorPickers;
    }
}
