package Foor;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by krister on 4/30/15.
 */
public class Ristmik extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Foor foor = new Foor();
        for (int i = 0; i < 5; i++) {
            foor.punane();
            foor.paus(5);
            foor.kollane();
            foor.paus(1);
            foor.punane();
            foor.kollane();
            foor.roheline();
            foor.paus(5);
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
        }
    }
}
