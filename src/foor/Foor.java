package foor;

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
    private int sceneSize = 700;
    private int foorWidth = sceneSize / 4;
    private Circle punane;
    private Circle kollane;
    private Circle roheline;
    private Color[] varvid = {Color.GRAY, Color.RED, Color.YELLOW, Color.SPRINGGREEN};
    public ArrayList<Integer> pausid = new ArrayList();
    public int pausideJarg = 0;
    public int fooriMuutumisi = 0;


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
        fooriMuutumisi++;

        // Iga muutuse kohta peab olema paus, et loend oleks õige.
        // 0 toob mingi bugi sisse, kus tuled vahel ei reageeri
        if (pausid.size() < fooriMuutumisi) {
            paus(0.01);
        }

        // Leia timeout
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
        int rotate = 0;
        double scale = 1;

        if (!suund.isEmpty()) {
            sceneSize = sceneSize / 3;
            scale = 0.3;
            switch (suund) {
                case "vasakul":
                    x = -sceneSize;
                    rotate = 90;
                    break;
                case "paremal":
                    x = sceneSize;
                    rotate = -90;
                    break;
                case "üleval":
                    y = -sceneSize;
                    rotate = 180;
                    break;
                case "all":
                    y = sceneSize;
                    break;
            }
        }

        // Kast
        Rectangle kast = new Rectangle();
        kast.setWidth(foorWidth);
        kast.setHeight(foorWidth * 3);
        kast.setFill(Color.DIMGRAY);

        // Punane
        punane = new Circle();
        punane.setFill(varvid[0]);
        punane.setRadius(foorWidth / 2.5);
        punane.setTranslateY(-foorWidth);
        punane.setId(suund);

        // Kollane
        kollane = new Circle();
        kollane.setFill(varvid[0]);
        kollane.setRadius(foorWidth / 2.5);
        kollane.setId(suund);

        // Roheline
        roheline = new Circle();
        roheline.setFill(varvid[0]);
        roheline.setRadius(foorWidth / 2.5);
        roheline.setTranslateY(foorWidth);
        roheline.setId(suund);

        // Lisa elemendid StackPane sisse
        stack.getChildren().addAll(kast, punane, kollane, roheline);

        // Ja liiguta StackPane paika
        stack.setTranslateX(x);
        stack.setTranslateY(y);
        stack.setScaleX(scale);
        stack.setScaleY(scale);
        stack.setRotate(rotate);
    }

    private void setupStage() {
        stage = new Stage();
        stack = new StackPane();
        scene = new Scene(stack, sceneSize, sceneSize);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(
                event -> System.exit(0)
        );
    }

    private void setupStage(Stage stage) {
        StackPane rootStack;

        if (stage.getScene() == null) {
            rootStack = new StackPane();
            rootStack.setId("root");
            scene = new Scene(rootStack, sceneSize, sceneSize);
            stage.setScene(scene);
            stage.show();
        } else {
            scene = stage.getScene();
            rootStack = (StackPane) scene.lookup("#root");
        }
        stack = new StackPane();
        rootStack.getChildren().add(stack);

        stage.setOnCloseRequest(
                event -> System.exit(0)
        );
    }

    public void paus(double sek) {
        int pausideKogus = pausid.size();
        int pausiKestvus = (int) (sek * 1000);

        // Kui kasutatakse kahte pausi järjest (fooruMuutust vahepeal ei ole),
        // siis kirjuta viimane kestvus üle, mitte ei lisa uut.
        if (fooriMuutumisi < pausideKogus) {
            pausiKestvus += pausid.get(pausideKogus - 1);
            pausid.set(pausideKogus - 1, pausiKestvus);
        } else {
            pausid.add(pausiKestvus);
        }
    }
}
