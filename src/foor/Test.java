package foor;

import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        TrafficLight light1 = new TrafficLight(TrafficLight.UP, primaryStage);

        light1.setGreen();
        light1.addPause(1.0);
        light1.unsetGreen();

        light1.playAnimation();
    }
}
