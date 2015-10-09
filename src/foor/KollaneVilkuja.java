package foor;

import javafx.application.Application;
import javafx.stage.Stage;

public class KollaneVilkuja extends Application {

    public void start(Stage primaryStage) throws Exception {
        Foor foor = new Foor();
        foor.vahetaKollast();
        foor.paus(0.5);
        foor.vahetaKollast();
        foor.paus(0.5);
        foor.vahetaKollast();
        foor.paus(0.5);
        foor.vahetaKollast();
        foor.paus(0.5);
        foor.vahetaKollast();
        foor.paus(0.5);
    }

}
