package teema2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * 1. Joonista kast kasutades jooni
 * 2. Joonista ring kasutades jooni ( punkt ringjoonel = raadius * cos(kraad) )
 * 3. Joonista spiraal kasutades jooni
 * 4. Joonista propeller kasutades jooni
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

        for (double nurk = 0; nurk <= Math.PI * 2; nurk = nurk + 0.1) {
            int algX = (int) (raadius * Math.cos(nurk));
            int algY = (int) (raadius * Math.sin(nurk));
            int loppX = (int) (raadius * Math.cos(nurk + 0.1));
            int loppY = (int) (raadius * Math.sin(nurk + 0.1));
            Line joon = new Line(keskkohtX + algX, keskkohtY + algY, keskkohtX + loppX, keskkohtY + loppY);
            pane.getChildren().add(joon);
        }
    }
}
