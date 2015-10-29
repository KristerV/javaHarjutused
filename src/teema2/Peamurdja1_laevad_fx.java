package teema2;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * 1. Kasuta JavaFX, et joonistada laud ja näita mängu kulgemist.
 *      http://i200.itcollege.ee/javafx
 *      http://i200.itcollege.ee/javafx-layout-GridPane
 */
public class Peamurdja1_laevad_fx extends Application {
    GridPane laud;
    int laualTulpasid = 4;
    int laualRidasid = 4;
    int ruuduKylg = 50;
    double laevaToenaosus = 1.6; // suurem on tõenäosem

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Sea üles JavaFX laud, stseen ja lava
        laud = new GridPane();
        Scene scene = new Scene(laud, laualRidasid * ruuduKylg, laualTulpasid * ruuduKylg);
        primaryStage.setScene(scene);

        // Genereeri laevad
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

        // Reageeri hiire klikile
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

            if (kasMangLabi()) {
                Label tekst = new Label("Võitsid!");
                StackPane aken = new StackPane();
                aken.getChildren().add(tekst);
                Scene stseen = new Scene(aken, 200,200);
                primaryStage.setScene(stseen);
                System.exit(0);
            }
        });

        // Näita "lava"
        primaryStage.show();
    }

    private void kaivitaMang() {
    }

    private void genereeriLaud(int laius, int pikkus) {
    }

    private boolean kasMangLabi() {
        for (Node node : laud.getChildren()) {
            if (node.getId() == "laev") {
                return false;
            }
        }
        return true;
    }
}
