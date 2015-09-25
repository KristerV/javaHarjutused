package foor;

import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        TrafficLight light1 = new TrafficLight(TrafficLight.UP, primaryStage);
        new Thread(()->
        {
                System.out.println("Starting traffic loop on background thread");
                while (true)
                {
                    greenIdle.playAnim(light1);
                    greenToRed.playAnim(light1);
                    redIdle.playAnim(light1);
                    redToGreen.playAnim(light1);
                }
        }).start();
    }

    private LightAnim greenIdle = new LightAnim("greenIdle") {
        @Override
        public void createAnim() {
            toggleGreen();
            addPause(7.0);
        }
    };

    private LightAnim redIdle = new LightAnim("redIdle") {
        @Override
        public void createAnim() {
            toggleRed();
            addPause(12.0);
        }
    };

    private LightAnim greenToRed = new LightAnim("greenToRed") {
        @Override
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
        @Override
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
