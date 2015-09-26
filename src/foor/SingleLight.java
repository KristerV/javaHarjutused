package foor;

import javafx.application.Application;
import javafx.stage.Stage;

public class SingleLight extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        TrafficLight light = new TrafficLight(TrafficLight.UP, primaryStage);

        greenIdle(light);
        greenToRed(light);

        redIdle(light);
        redToGreen(light);

        light.playAnimation();
    }

    // 7s
    public static void greenIdle(TrafficLight light){
        light.setGreen();
        light.addPause(7.0);
    }

    // 4s
    public static void greenToRed(TrafficLight light) {
        for (int i = 0; i < 6; ++i) {
            light.toggleGreen();
            light.addPause(0.5);
        }
        light.toggleGreen();

        light.toggleYellow();
        light.addPause(1.0);
        light.toggleYellow();
    }

    // 10s
    public static void redIdle(TrafficLight light){
        light.setRed();
        light.addPause(10.0);
    }

    // 1s
    public static void redToGreen(TrafficLight light) {
        light.toggleYellow();
        light.addPause(1.0);

        light.toggleRed();
        light.toggleYellow();
    }
}
