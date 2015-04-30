package Foor;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Foor {
    private StackPane stack;
    private Scene scene;
    private Stage stage;
    private int sceneWidth = 800;
    private int sceneHeight = 800;
    private int foorWidth = 200;
    private Circle punane;
    private Circle kollane;
    private Circle roheline;
    private Color[] varvid = {Color.GRAY, Color.RED, Color.YELLOW, Color.SPRINGGREEN};
    private ArrayList<Integer> pausid = new ArrayList();
    private int pausideJarg = 0;
    private int pausideArv;


    public Foor() {
        setupStage();
        joonistaFoor();
    }

    public Foor(String suund) {
        setupStage();
        joonistaFoorSuunaga(suund);
    }

    public Foor(String suund, Stage primaryStage) {
        setupStage(primaryStage);
        joonistaFoorSuunaga(suund);
    }

    public void punane() {
        muudaVarvi(punane, varvid[1]);
    }

    public void kollane() {
        muudaVarvi(kollane, varvid[2]);
    }

    public void roheline() {
        muudaVarvi(roheline, varvid[3]);
    }

    private void muudaVarvi(Circle tuli, Color varv) {
        pausideArv++;

        // Iga muutuse kohta peab olema paus, kasvõi null sekundit kestev
        if (pausid.size() < pausideArv) {
            paus(0);
        }

        // Võta timeout ja arvuta järgmine
        int timeout = 0;
        for (int i = 0; i <= pausideJarg; i++) {
            timeout += pausid.get(i);
        }
        pausideJarg++;

        // Käivita timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Color tuleVarv = (Color) tuli.getFill();
                boolean p6leb = tuleVarv.equals(varv);
                if (p6leb) {
                    tuli.setFill(varvid[0]);
                } else {
                    tuli.setFill(varv);
                }
            }
        }, timeout);
    }

    private void joonistaFoor() {
        joonistaFoorSuunaga("");
    }

    private void joonistaFoorSuunaga(String suund) {
        int x = 0;
        int y = 0;

        if (!suund.isEmpty()) {
            foorWidth = foorWidth / 3;
            switch (suund) {
                case "vasak":
                    x = -300;
                    break;
                case "parem":
                    x = 300;
                    break;
                case "üleval":
                    y = -300;
                    break;
                case "all":
                    y = 300;
                    break;
            }
        }


        // Kast
        Rectangle kast = new Rectangle();
        kast.setWidth(foorWidth);
        kast.setHeight(foorWidth * 3);
        kast.setFill(Color.DIMGRAY);
        kast.setTranslateX(x);
        kast.setTranslateY(y);

        // Punane
        punane = new Circle();
        punane.setFill(varvid[0]);
        punane.setRadius(foorWidth / 2.5);
        punane.setTranslateX(x);
        punane.setTranslateY(-foorWidth + y);

        // Kollane
        kollane = new Circle();
        kollane.setFill(varvid[0]);
        kollane.setRadius(foorWidth / 2.5);
        kollane.setTranslateX(x);
        kollane.setTranslateY(y);

        // Roheline
        roheline = new Circle();
        roheline.setFill(varvid[0]);
        roheline.setRadius(foorWidth / 2.5);
        roheline.setTranslateX(x);
        roheline.setTranslateY(foorWidth + y);

        // Lisa elemendid StackPane sisse
        stack.getChildren().addAll(kast, punane, kollane, roheline);
    }

    private void setupStage() {
        stage = new Stage();
        stack = new StackPane();
        scene = new Scene(stack, sceneWidth, sceneHeight);
        stage.setScene(scene);
        stage.show();
    }

    private void setupStage(Stage stage) {

        if (stage.getScene() == null) {
            stack = new StackPane();
            stack.setId("stack");
            scene = new Scene(stack, sceneWidth, sceneHeight);
            stage.setScene(scene);
            stage.show();
        } else {
            scene = stage.getScene();
            stack = (StackPane) scene.lookup("#stack");
        }
    }

    public void paus(double i) {
        pausid.add((int)(i*1000));
    }
}
