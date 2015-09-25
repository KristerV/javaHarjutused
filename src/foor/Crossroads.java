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

        light1.runAnimations(()-> loopGroupA(light1));
        light2.runAnimations(()-> loopGroupA(light2));
        light3.runAnimations(()-> loopGroupB(light3));
        light4.runAnimations(()-> loopGroupB(light4));
    }

    private void loopGroupA(TrafficLight light) {
        while (true) {
            greenIdle.playAnim(light);
            greenToRed.playAnim(light);
            redIdle.playAnim(light);
            redToGreen.playAnim(light);
        }
    }
    private void loopGroupB(TrafficLight light) {
        while (true) {
            redIdle.playAnim(light);
            redToGreen.playAnim(light);
            greenIdle.playAnim(light);
            greenToRed.playAnim(light);
        }
    }

    private LightAnim greenIdle = new LightAnim("greenIdle") {
        public void createAnim() {
            setGreen();
            addPause(7.0);
        }
    };

    private LightAnim redIdle = new LightAnim("redIdle") {
        public void createAnim() {
            setRed();
            addPause(12.0);
        }
    };

    private LightAnim greenToRed = new LightAnim("greenToRed") {
        public void createAnim() {
            for (int i = 0; i < 6; ++i) {
                toggleGreen();
                addPause(0.5);
            }
            toggleGreen();

            toggleYellow();
            addPause(1.0);
            toggleYellow();
        }
    };

    private LightAnim redToGreen = new LightAnim("redToGreen") {
        public void createAnim() {
            for (int i = 0; i < 10; ++i) {
                toggleRed();
                addPause(0.5);
            }
            toggleYellow();
            addPause(1.0);

            toggleRed();
            toggleYellow();
        }
    };
}
