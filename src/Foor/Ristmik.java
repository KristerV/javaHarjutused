package Foor;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by krister on 4/30/15.
 */
public class Ristmik extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Foor foor1 = new Foor("üleval", primaryStage);
        Foor foor2 = new Foor("parem", primaryStage);
        Foor foor3 = new Foor("vasak", primaryStage);
        Foor foor4 = new Foor("all", primaryStage);
        for (int i = 0; i < 5; i++) {
            foor1.punane();
            foor1.paus(5);
            foor1.kollane();
            foor1.paus(1);
            foor1.punane();
            foor1.kollane();
            foor1.roheline();
            foor1.paus(5);
            foor1.roheline();
            foor1.paus(0.5);
            foor1.roheline();
            foor1.paus(0.5);
            foor1.roheline();
            foor1.paus(0.5);
            foor1.roheline();
            foor1.paus(0.5);
            foor1.roheline();
            foor1.paus(0.5);
            foor1.roheline();
            foor1.paus(0.5);
            foor1.roheline();
        }
    }
}
