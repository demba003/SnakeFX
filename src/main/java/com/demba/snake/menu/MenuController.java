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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
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
    private OptionsPane optionsLine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //drawControls(1);

        singleButton.setOnAction(e -> singleButtonClicked());
        multiButton.setOnAction(e -> multiButtonClicked());
        networkButton.setOnAction(e -> networkButtonClicked());
        startButton.setOnAction(e -> startButtonClicked());
    }

    private void startButtonClicked() {
        startGame(false);
    }

    private void startGame(boolean isServer) {
        for (int i=0; i<snakeCount; i++){
            snakeParams.add(new SnakeParams((Keys)optionsLine.getChoiceBoxes().get(i).getValue(), optionsLine.getColorPickers().get(i).getValue()));
        }

        Stage stage = (Stage) options.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("game.fxml"));
        GameController gameController;

        if (networkButton.isSelected()) {
            gameController = new GameController(snakeCount, snakeParams, isServer );
        }
        else
            gameController = new GameController(snakeCount, snakeParams);

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
        drawControls(1);
    }

    private void multiButtonClicked(){
        startButton.setDisable(false);
        drawControls(2);
    }

    private void networkButtonClicked() {
        //startButton.setDisable(false);
        options.getChildren().removeAll(options.getChildren());
        drawControls(1);
        String myIP = "";
        /*try {
            myIP = Inet4Address.getLocalHost().getHostAddress();
        } catch (Exception ex) {}*/

        Button server = new Button("Start server with IP: " + myIP);
        server.setOnAction(event -> startServerClicked());
        HBox clientBox = new HBox();
        TextField textField = new TextField();
        textField.setPromptText("Server's IP address");
        Button client = new Button("Connect to server");
        client.setOnAction(event -> startClientClicked());
        clientBox.getChildren().addAll(textField, client);
        options.getChildren().addAll(server, clientBox);
    }

    private void startServerClicked() {
        startGame(true);
    }

    private void startClientClicked() {
        startGame(false);
    }

    private void drawControls(int count) {
        startButton.setDisable(false);
        snakeCount = count;
        snakeParams = new ArrayList<>();
        options.getChildren().removeAll(options.getChildren());
        optionsLine = new OptionsPane(count);
        options.getChildren().add(optionsLine);
    }
}
