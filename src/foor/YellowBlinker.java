package foor;

import javafx.application.Application;
import javafx.stage.Stage;

public class YellowBlinker extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        TrafficLight light1 = new TrafficLight(TrafficLight.UP, primaryStage);

        light1.toggleYellow();
        light1.addPause(0.75);
        light1.playAnimation();
    }
}
