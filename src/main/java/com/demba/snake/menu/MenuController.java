package com.demba.snake.menu;


import com.demba.snake.game.GameController;
import com.demba.snake.game.Keys;
import com.demba.snake.game.SnakeParams;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private RadioButton singleButton, multiButton, networkButton;
    @FXML
    private Button startButton;
    @FXML
    private VBox options;

    private int snakeCount = 1;
    private ArrayList<SnakeParams> snakeParams;
    private OptionsLine optionsLine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //drawControls(1);

        singleButton.setOnAction(e -> singleButtonClicked());
        multiButton.setOnAction(e -> multiButtonClicked());
        startButton.setOnAction(e -> startButtonClicked());
    }

    private void startButtonClicked() {
        for (int i=0; i<snakeCount; i++){
            snakeParams.add(new SnakeParams((Keys)optionsLine.getChoiceBoxes().get(i).getValue(), optionsLine.getColorPickers().get(i).getValue()));
        }

        Stage stage = (Stage) options.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("game.fxml"));
        GameController gameController = new GameController(snakeCount, snakeParams);
        fxmlLoader.setController(gameController);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root, 800, 600));
        stage.setOnCloseRequest(e -> gameController.onWindowClose());
        stage.show();
    }

    public void onWindowClose() {}

    private void singleButtonClicked(){
        startButton.setDisable(false);
        snakeCount = 1;
        snakeParams = new ArrayList<>();
        options.getChildren().removeAll(options.getChildren());
        optionsLine = new OptionsLine(1);
        drawControls(1);
    }

    private void multiButtonClicked(){
        startButton.setDisable(false);
        snakeCount = 2;
        snakeParams = new ArrayList<>();
        options.getChildren().removeAll(options.getChildren());
        optionsLine = new OptionsLine(2);
        drawControls(2);
    }

    private void drawControls(int count) {
        options.getChildren().add(optionsLine);
    }
}
