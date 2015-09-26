package foor;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

public class Crossroads extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TrafficLight light1 = new TrafficLight(TrafficLight.UP, primaryStage);
        TrafficLight light2 = new TrafficLight(TrafficLight.DOWN, primaryStage);
        TrafficLight light3 = new TrafficLight(TrafficLight.RIGHT, primaryStage);
        TrafficLight light4 = new TrafficLight(TrafficLight.LEFT, primaryStage);

        animGroupA(light1);
        animGroupA(light2);
        animGroupB(light3);
        animGroupB(light4);
    }

    private void animGroupA(TrafficLight light) {
        greenIdle(light);
        greenToRed(light);
        redIdle(light);
        redToGreen(light);

        light.playAnimation();
    }
    private void animGroupB(TrafficLight light) {
        redIdle(light);
        redToGreen(light);
        greenIdle(light);
        greenToRed(light);

        light.playAnimation();
    }

    void greenIdle(TrafficLight light){
        light.setGreen();
        light.addPause(7.0);
    }

    void redIdle(TrafficLight light){
        light.setRed();
        light.addPause(12.0);
    }

    void greenToRed(TrafficLight light) {
        for (int i = 0; i < 6; ++i) {
            light.toggleGreen();
            light.addPause(0.5);
        }
        light.toggleGreen();

        light.toggleYellow();
        light.addPause(1.0);
        light.toggleYellow();
    }

    void redToGreen(TrafficLight light) {
        for (int i = 0; i < 10; ++i) {
            light.toggleRed();
            light.addPause(0.5);
        }
        light.toggleYellow();
        light.addPause(1.0);

        light.toggleRed();
        light.toggleYellow();
    }
}
