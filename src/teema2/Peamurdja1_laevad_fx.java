package teema2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 1. Kasuta JavaFX, et joonistada laud ja näita mängu kulgemist.
 *      http://i200.itcollege.ee/javafx
 *      http://i200.itcollege.ee/javafx-layout-GridPane
 */
public class Peamurdja1_laevad_fx extends Application {
    GridPane laud;
    Stage stage;
    int laualTulpasid = 4;
    int laualRidasid = 4;
    int ruuduKylg = 50;
    double laevaToenaosus = 1.6; // suurem on tõenäosem

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        seadistaStseen();
        genereeriLaevad();
        reageeriKlikile();

        primaryStage.show(); // Näita "lava"
    }

    private void seadistaStseen() {
        laud = new GridPane();
        Scene scene = new Scene(laud, laualRidasid * ruuduKylg, laualTulpasid * ruuduKylg);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> System.exit(0));
    }

    private void reageeriKlikile() {
        laud.setOnMouseClicked(event -> {
            Rectangle shape = (Rectangle) event.getTarget();
            Integer rida = GridPane.getRowIndex(shape);
            Integer tulp = GridPane.getColumnIndex(shape);
            String id = shape.getId();

            if (id == "laev") {
                shape.setId("põhjas");
                shape.setFill(Color.RED);
            } else if (id == "tühi") {
                shape.setId("meri");
                shape.setFill(Color.BLUE);
            }
            if (laevadOnOtsas()) {
                gameover();
            }
            // Kui soovid sekundit oodata enne kirja
            /* if (laevadOnOtsas()) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameover();
                    }
                }));
                timeline.play();
            }*/
        });
    }

    private void gameover() {
        Label tekst = new Label("Võitsid!");
        StackPane stack = new StackPane();
        stack.getChildren().add(tekst);
        Scene stseen = new Scene(stack, 200,200);
        stage.setScene(stseen);
    }

    private void genereeriLaevad() {
        int laevasid = 0;
        for (int i = 0; i < laualRidasid; i++) {
            for (int j = 0; j < laualTulpasid; j++) {
                Rectangle ruut = new Rectangle(ruuduKylg, ruuduKylg);
                int rand = (int) (Math.random() * laevaToenaosus);
                if (rand == 1) {
                    ruut.setId("laev");
                    ruut.setFill(Color.DARKBLUE);
                    laevasid++;
                } else {
                    ruut.setId("tühi");
                    ruut.setFill(Color.DARKBLUE);
                }
                laud.add(ruut, j, i);
            }
        }
        System.out.println("Laevasid on " + laevasid);
    }

    private boolean laevadOnOtsas() {
        for (Node node : laud.getChildren()) {
            if (node.getId() == "laev") {
                return false;
            }
        }
        return true;
    }
}
