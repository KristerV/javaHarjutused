package foor;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by krister on 6/9/15.
 */
public class Vilkuv extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Foor foor = new Foor();

        for (int i = 0; i < 50; i++) {
            foor.kollane();
            foor.paus(0.7);
        }
    }
}
