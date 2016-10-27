package teema2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * 1. Joonista kast kasutades jooni
 * 2. Joonista spiraal kasutades jooni (vaata ringi meetodi)
 * 3. Joonista propeller (nagu propeller.png) kasutades jooni
 */
public class Harjutus2_ringid extends Application {
    int aknaLaius = 600;
    int aknaKorgus = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        joonistaRing();
    }

    private void joonistaRing() {

        // Loo aken ja lava
        Stage stage = new Stage();
        Pane pane = new Pane();
        Scene scene = new Scene(pane, aknaLaius, aknaKorgus);
        stage.setScene(scene);

        // Määra parameetrid
        double keskkohtX = aknaLaius / 2;
        double keskkohtY = aknaKorgus / 2;
        int raadius = 200;
        double kyljePikkus = 0.5;

        // Joonista ring joon joone haaval
        for (double nurk = 0; nurk <= Math.PI * 2; nurk = nurk + kyljePikkus) {
            int algX = (int) (raadius * Math.cos(nurk));
            int algY = (int) (raadius * Math.sin(nurk));
            int loppX = (int) (raadius * Math.cos(nurk + kyljePikkus));
            int loppY = (int) (raadius * Math.sin(nurk + kyljePikkus));
            Line joon = new Line(keskkohtX + algX, keskkohtY + algY, keskkohtX + loppX, keskkohtY + loppY);
            pane.getChildren().add(joon);
        }

        // Näita akent
        stage.show();
    }

}
