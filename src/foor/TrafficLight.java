package foor;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficLight implements LightAnim.IAnimPlayable {

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

    //// direct manip

    public void toggleRed()    { toggleLight(LightColour.RED);    }
    public void toggleYellow() { toggleLight(LightColour.YELLOW); }
    public void toggleGreen()  { toggleLight(LightColour.GREEN);  }
    public void setRed()    { setLight(LightColour.RED, true);    }
    public void setYellow() { setLight(LightColour.YELLOW, true); }
    public void setGreen()  { setLight(LightColour.GREEN, true);  }


    //// event based manipulation

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
        System.out.printf("%s::%s switched %s\n", Dir, lightId, isActive ? "off" : "on");
    }

    @Override
    public void animEvent(LightAnim.Event evt, Object value, double time) throws InterruptedException {
        switch (evt) {
            case SET:    setLight((TrafficLight.LightColour)value, true);   break;
            case UNSET:  setLight((TrafficLight.LightColour)value, false);  break;
            case TOGGLE: toggleLight((TrafficLight.LightColour)value);      break;
            case PAUSE:  Thread.sleep((int)((Double)value * 1000));         break; /* @todo Replace Thread.sleep */
        }
    }

    public void runAnimations(Runnable runnable) {
        new Thread(runnable).start();
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
                initCircle(RED, "red", -LightWidth),
                initCircle(YELLOW, "yellow", 0),
                initCircle(GREEN, "green", LightWidth));

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
