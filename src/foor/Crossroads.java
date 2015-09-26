package foor;

import javafx.stage.Stage;

public class Crossroads extends SingleLight {

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
}
