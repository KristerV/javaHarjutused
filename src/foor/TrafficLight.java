package foor;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TrafficLight {

    //// constant definitions

    public static final LightColour GRAY   = LightColour.GRAY;
    public static final LightColour RED    = LightColour.RED;
    public static final LightColour YELLOW = LightColour.YELLOW;
    public static final LightColour GREEN  = LightColour.GREEN;

    public static final Direction UP    = Direction.UP;
    public static final Direction DOWN  = Direction.DOWN;
    public static final Direction LEFT  = Direction.LEFT;
    public static final Direction RIGHT = Direction.RIGHT;

    public enum Direction   { UP, DOWN, LEFT, RIGHT }
    public enum LightColour { GRAY, RED, YELLOW, GREEN }


    //// private state variables

    private StackPane Stack;
    private Scene Scene;
    private Stage Stage;
    private int SceneSize = 700;
    private int LightWidth = SceneSize / 4;
    private Color[] Colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.SPRINGGREEN};
    private Circle[] Circles = { null, new Circle(), new Circle(), new Circle() };
    private Direction Dir;


    public TrafficLight() {
        setupStage();
        drawLight(UP);
    }

    public TrafficLight(Direction direction) {
        setupStage();
        drawLight(direction);
    }

    public TrafficLight(Direction direction, Stage primaryStage) {
        setupStage(primaryStage);
        drawLight(direction);
    }

    //// event based manipulation

    public enum Event {
        SET    /* set light status on */,
        UNSET  /* set light status off */,
        TOGGLE /* toggle light status (on/off)*/,
        PAUSE  /* add animation delay on timeline */,
    }

    private class Command {
        public Event Evt;
        public Object Value;
        public double Time;  // this is just for more timeline info, not actually required for anything
        public Command(Event evt, Object value, double time) {
            Evt = evt;
            Value = value;
            Time = time;
        }
    }

    //// animation related stuff

    private ArrayList<Command> Commands = new ArrayList<Command>();
    private double AnimTime = 0.0;
    private Thread AnimThread;
    private volatile boolean AnimRunning = false;

    public double getAnimTime() {
        return AnimTime;
    }
    private void addCmd(Event evt, Object value) {
        Commands.add(new Command(evt, value, AnimTime));
    }

    public void setRed()    { addCmd(Event.SET, TrafficLight.RED);    }
    public void setYellow() { addCmd(Event.SET, TrafficLight.YELLOW); }
    public void setGreen()  { addCmd(Event.SET, TrafficLight.GREEN);  }

    public void unsetRed()    { addCmd(Event.UNSET, TrafficLight.RED);    }
    public void unsetYellow() { addCmd(Event.UNSET, TrafficLight.YELLOW); }
    public void unsetGreen()  { addCmd(Event.UNSET, TrafficLight.GREEN);  }

    public void toggleRed()    { addCmd(Event.TOGGLE, TrafficLight.RED);    }
    public void toggleYellow() { addCmd(Event.TOGGLE, TrafficLight.YELLOW); }
    public void toggleGreen()  { addCmd(Event.TOGGLE, TrafficLight.GREEN);  }

    public void addPause(double pause) {
        if (pause > 0.0) {
            addCmd(Event.PAUSE, pause);
            AnimTime += pause;
        }
    }

    //// internal manip

    private void toggleLight(LightColour lightId) { setLight(lightId, !isActive(lightId)); }

    private boolean isActive(LightColour lightId) {
        int index = lightId.ordinal();
        return Circles[index].getFill().equals(Colors[index]);
    }

    private void setLight(LightColour lightId, boolean isActive) {
        int index = lightId.ordinal();
        Circle light = Circles[index];

        Platform.runLater(()-> light.setFill(Colors[isActive ? index : GRAY.ordinal()]));

        // "UP::YELLOW switched on"
        System.out.printf("%5s::%-6s switched %s\n", Dir, lightId, isActive ? "on" : "off");
    }


    //// Animation playing and stopping

    public boolean isAnimationPlaying() {
        return AnimRunning;
    }

    public void playAnimation() {
        if (AnimRunning) return;
        AnimRunning = true;
        AnimThread = new Thread(()->{
            while (AnimRunning) for (Command cmd : Commands) {
                if (!AnimRunning) return; // exit thread
                try {
                    switch (cmd.Evt) {
                        case SET:    setLight((LightColour)cmd.Value, true);   break;
                        case UNSET:  setLight((LightColour)cmd.Value, false);  break;
                        case TOGGLE: toggleLight((LightColour)cmd.Value);      break;
                        case PAUSE:  Thread.sleep((int)((Double)cmd.Value * 1000));         break; /* @todo Replace Thread.sleep */
                    }
                }
                catch (InterruptedException ex) {
                    System.err.printf("runAnimation() %s(%s) interrupted\n", cmd.Evt, cmd.Value);
                }
            }
        });
        AnimThread.start();
    }

    public void stopAnimation() {
        if (AnimRunning) {
            AnimRunning = false;
            try {
                AnimThread.join(500);
            } catch (InterruptedException e) {
                System.err.println("AnimThread.join() failed. Animation was not properly stopped.\n");
                e.printStackTrace();
            }
        }
        setLight(LightColour.RED,    false);
        setLight(LightColour.YELLOW, false);
        setLight(LightColour.GREEN,  false);
    }

    //// TrafficLight graphics setup

    private Circle initCircle(LightColour lightId, String id, int translateY) {
        Circle c = Circles[lightId.ordinal()];
        c.setFill(Colors[GRAY.ordinal()]);
        c.setRadius(LightWidth / 2.5);
        c.setTranslateY(translateY);
        c.setId(id);
        return c;
    }

    private void drawLight(Direction direction) {
        int lightSize = SceneSize / 3;
        double scale = 0.3;

        Dir = direction;
        int dir = direction.ordinal();
        int rotation = new int[]{180, 0, 90, -90}[dir];
        int x = new int[]{ 0, 0, -lightSize, lightSize}[dir];
        int y = new int[]{ -lightSize, lightSize, 0, 0}[dir];

        Rectangle rect = new Rectangle();
        rect.setWidth(LightWidth);
        rect.setHeight(LightWidth * 3);
        rect.setFill(Color.DIMGRAY);

        Stack.getChildren().addAll(rect,
                initCircle(RED,    "red",    -LightWidth),
                initCircle(YELLOW, "yellow", 0),
                initCircle(GREEN,  "green",  LightWidth));

        Stack.setTranslateX(x);
        Stack.setTranslateY(y);
        Stack.setScaleX(scale);
        Stack.setScaleY(scale);
        Stack.setRotate(rotation);
    }

    private void setupStage() {
        Stage = new Stage();
        Stack = new StackPane();
        Scene = new Scene(Stack, SceneSize, SceneSize);
        Stage.setScene(Scene);
        Stage.show();
        Stage.setOnCloseRequest(event -> System.exit(0));
    }

    private void setupStage(Stage stage) {
        StackPane rootStack;

        if (stage.getScene() == null) {
            rootStack = new StackPane();
            rootStack.setId("root");
            Scene = new Scene(rootStack, SceneSize, SceneSize);
            stage.setScene(Scene);
            stage.show();
        } else {
            Scene = stage.getScene();
            rootStack = (StackPane) Scene.lookup("#root");
        }
        Stack = new StackPane();
        rootStack.getChildren().add(Stack);

        stage.setOnCloseRequest(
                event -> System.exit(0)
        );
    }
}
