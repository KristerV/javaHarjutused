package foor;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by krister on 4/30/15.
 */
public class Ristmik extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Foor foor = new Foor();
        Foor foor1 = new Foor("üleval", primaryStage);
        Foor foor2 = new Foor("all", primaryStage);
        Foor foor3 = new Foor("paremal", primaryStage);
        Foor foor4 = new Foor("vasakul", primaryStage);

        foor.kollane();
        foor1.punane();
        foor2.punane();
        foor3.roheline();
        foor4.roheline();

        for (int i = 0; i < 5; i++) {
            foor.paus(0.8);
            foor.kollane();
            foor.paus(0.8);
            foor.kollane();
            foor.paus(0.8);
            foor.kollane();
            foor.paus(0.8);
            foor.kollane();
            foor.paus(0.8);
            foor.kollane();
            foor.paus(0.8);
            foor.kollane();
            foor.paus(0.8);
            foor.kollane();
            foor.paus(0.8);
            foor.kollane();

            foor1.paus(11);
            punasestRoheliseks(foor1);
            foor1.paus(5);
            rohelisestPunaseks(foor1);
            foor1.paus(3);

            foor2.paus(11);
            punasestRoheliseks(foor2);
            foor2.paus(5);
            rohelisestPunaseks(foor2);
            foor2.paus(3);

            foor3.paus(5);
            rohelisestPunaseks(foor3);
            foor3.paus(14);
            punasestRoheliseks(foor3);

            foor4.paus(5);
            rohelisestPunaseks(foor4);
            foor4.paus(14);
            punasestRoheliseks(foor4);
        }
    }

    private void rohelisestPunaseks(Foor foor) {
        foor.roheline();
        foor.paus(0.5);
        foor.roheline();
        foor.paus(0.5);
        foor.roheline();
        foor.paus(0.5);
        foor.roheline();
        foor.paus(0.5);
        foor.roheline();
        foor.paus(0.5);
        foor.roheline();
        foor.paus(0.5);
        foor.roheline();
        foor.kollane();
        foor.paus(1);
        foor.kollane();
        foor.punane();
    }

    private void punasestRoheliseks(Foor foor) {
        foor.kollane();
        foor.paus(1);
        foor.punane();
        foor.kollane();
        foor.roheline();
    }
}
