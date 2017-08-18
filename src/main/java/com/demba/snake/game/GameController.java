package com.demba.snake.game;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable, EventHandler<KeyEvent>{
    @FXML
    private Pane boardPaneHolder;
    @FXML
    private HBox scoresBox;

    private Thread rendererThread;
    private Thread networkThread;
    private ArrayList<SnakeModel> snakes;
    private int snakeCount;
    private ArrayList<SnakeParams> snakeParams;
    private boolean networkMode = false;
    private boolean isServer = false;
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private String serverIP = "192.168.100.4";
    private KeyCode keyToSend = null;

    public GameController(int snakeCount, ArrayList<SnakeParams> snakeParams) {
        this.snakeCount = snakeCount;
        this.snakeParams = snakeParams;
    }

    public GameController(int snakeCount, ArrayList<SnakeParams> snakeParams, boolean isServer) {
        this.snakeCount = snakeCount;
        this.snakeParams = snakeParams;
        this.networkMode = true;
        this.isServer = isServer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BoardPane board = new BoardPane();
        boardPaneHolder.getChildren().add(board);
        boardPaneHolder.setFocusTraversable(true);
        boardPaneHolder.setOnKeyPressed(this);

        snakes = new ArrayList<>();
        ScoreLabels scoreLabels = new ScoreLabels(snakeCount);
        scoresBox.getChildren().add(scoreLabels);

        CollisionModel collisionModel = new CollisionModel();
        Level level = new Level(collisionModel);
        Fruit fruit = new Fruit(collisionModel, Color.RED);
        Fruit[] fruits = {fruit};

        if (!networkMode) {
            for (int i=0; i < snakeCount; i++) {
                snakes.add(new SnakeModel(null, snakeParams.get(i).COLOR, collisionModel, fruit, snakeParams.get(i).KEYS));
            }
        } else {
            if (isServer) {
                snakes.add(new SnakeModel(new Point(0,0), Color.BLUE, collisionModel, fruit, new Keys(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT)));
                snakes.add(new SnakeModel(new Point(10,10), Color.GRAY, collisionModel, fruit, null));
            } else {
                snakes.add(new SnakeModel(new Point(10,10), Color.BLUE, collisionModel, fruit, new Keys(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT)));
                snakes.add(new SnakeModel(new Point(0,0), Color.GRAY, collisionModel, fruit, null));
            }

        }


        rendererThread = new Thread(new Renderer(board, fruits, snakes, level));
        rendererThread.start();

        for (int i = 0; i < snakeCount; i++)
            scoreLabels.getScoreLabels().get(i).textProperty().bind(snakes.get(i).messageProperty());

        if(networkMode) {
            networkThread = new Thread(() -> handleNetwork());
            networkThread.start();
        }
    }

    @Override
    public void handle(KeyEvent event) {
        for (SnakeModel snakeModel : snakes) {
            snakeModel.handleKeys(event.getCode());
        }
    }

    private void handleNetwork() {
        System.out.println("handler started");
        if (isServer) {
            System.out.println("inside if");
            try {
                serverSocket = new ServerSocket(12345);
                System.out.println("server started");
                while (true) {
                    Socket connection = null;
                    try {
                        //System.out.println("int: " + Thread.interrupted());
                        int code = snakes.get(0).getCurrentDirectionCode();
                        //System.out.println("code: " + code);
                        Thread.sleep(50);
                        //System.out.println("available: " + data.available());

                        if(code != 0) {
                            connection = serverSocket.accept();
                            Writer out = new OutputStreamWriter(connection.getOutputStream());
                            System.out.println("data: " + code);
                            out.write(code);
                            out.flush();

                            /*InputStream in = connection.getInputStream();
                            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
                            System.out.println("read: " + (char)reader.read());*/

                            connection.close();
                        }

                    } catch (IOException | InterruptedException ex) {
                        if (connection != null) {
                            connection.close();
                            serverSocket.close();
                        }
                        break;

                    }
                }
            } catch (IOException ex) {
                //ex.printStackTrace();
            }

        } else {
            try {
                while (true) {
                    Thread.sleep(10);
                    clientSocket = new Socket(serverIP, 12345);
                    InputStream in = clientSocket.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in, "UTF-8");

                    int received = reader.read();

                    snakes.get(1).handleNetworkCode(received);

                    System.out.println(received);
                }
            } catch (InterruptedException | IOException e) {
                //e.printStackTrace();
            }
        }
    }

    public void onWindowClose() {
        for (SnakeModel snake : snakes) {
            snake.endMovement();
        }
        try {
            clientSocket.close();
        } catch (NullPointerException | IOException e) {
            //e.printStackTrace();
        }
        rendererThread.interrupt();
        if (networkThread != null) networkThread.interrupt();
    }
}
