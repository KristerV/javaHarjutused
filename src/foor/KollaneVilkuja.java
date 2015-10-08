package foor;

import javafx.application.Application;
import javafx.stage.Stage;

public class KollaneVilkuja extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ValgusFoor foor = new ValgusFoor(ValgusFoor.YLEMINE, primaryStage);

        foor.vahetaKollast();
        foor.paus(0.75);
    }
}
