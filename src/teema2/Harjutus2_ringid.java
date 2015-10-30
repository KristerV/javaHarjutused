package teema2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * 1. Joonista kast kasutades jooni
 * 2. Joonista spiraal kasutades jooni (vaata ringi meetodi)
 * 3. Joonista propeller (nagu propeller.png) kasutades jooni
 */
public class Harjutus2_ringid extends Application {
    int aknaLaius = 500;
    int aknaKorgus = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        Pane pane = new Pane();

        // joonistaKast(pane);
        // joonistaRing(pane);
        // joonistaSpiraal(pane);
        joonistaPropeller(pane);

        Scene stseen = new Scene(pane, 500, 500);
        primaryStage.setScene(stseen);
    }

    private void joonistaKast(Pane pane) {
        Line joon1 = new Line(100, 100, 400, 100); // üleval vasakult paremale
        pane.getChildren().add(joon1);

        Line joon2 = new Line(400, 100, 400, 400); // üleval paremalt alla
        pane.getChildren().add(joon2);

        Line joon3 = new Line(400, 400, 100, 400); // alt paremalt vasakule
        pane.getChildren().add(joon3);

        Line joon4 = new Line(100, 400, 100, 100); // alt vasakult üles
        pane.getChildren().add(joon4);
    }

    private void joonistaRing(Pane pane) {
        double keskkohtX = aknaLaius / 2;
        double keskkohtY = aknaKorgus / 2;
        int raadius = 200;

        for (double nurk = 0; nurk <= Math.PI * 2; nurk = nurk + 0.3) {
            int algX = (int) (raadius * Math.cos(nurk));
            int algY = (int) (raadius * Math.sin(nurk));
            int loppX = (int) (raadius * Math.cos(nurk + 0.1));
            int loppY = (int) (raadius * Math.sin(nurk + 0.1));
            Line joon = new Line(keskkohtX + algX, keskkohtY + algY, keskkohtX + loppX, keskkohtY + loppY);
            pane.getChildren().add(joon);
        }
    }

    private void joonistaSpiraal(Pane pane) {
        double keskkohtX = aknaLaius / 2;
        double keskkohtY = aknaKorgus / 2;
        double raadius = 400;

        for (double nurk = 0; nurk <= Math.PI * 18; nurk = nurk + 0.1) {
            int algX = (int) (raadius * Math.cos(nurk));
            int algY = (int) (raadius * Math.sin(nurk));
            raadius = raadius * 0.99;
            int loppX = (int) (raadius * Math.cos(nurk + 0.1));
            int loppY = (int) (raadius * Math.sin(nurk + 0.1));
            Line joon = new Line(keskkohtX + algX, keskkohtY + algY, keskkohtX + loppX, keskkohtY + loppY);
            pane.getChildren().add(joon);
        }
    }

    private void joonistaPropeller(Pane pane) {
        double keskkohtX = aknaLaius / 2;
        double keskkohtY = aknaKorgus / 2;
        double raadius = 200;
        int count = 0;

        for (double nurk = 0; nurk <= Math.PI * 2; nurk = nurk + 0.1) {
            int algX = (int) (raadius * Math.cos(nurk));
            int algY = (int) (raadius * Math.sin(nurk));
            double loppX;
            double loppY;
            if (count % 2 == 0) {
                loppX = (int) (raadius * Math.cos(nurk + 0.1));
                loppY = (int) (raadius * Math.sin(nurk + 0.1));
            } else {
                loppX = 0;
                loppY = 0;
                Line joon = new Line(keskkohtX + algX, keskkohtY + algY, keskkohtX + loppX, keskkohtY + loppY);
                pane.getChildren().add(joon);
                algX = (int) (raadius * Math.cos(nurk + 0.1));
                algY = (int) (raadius * Math.sin(nurk + 0.1));
            }
            count++;
            Line joon = new Line(keskkohtX + algX, keskkohtY + algY, keskkohtX + loppX, keskkohtY + loppY);
            pane.getChildren().add(joon);
        }
    }
}
