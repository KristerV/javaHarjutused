package foor;

import java.util.ArrayList;

public abstract class LightAnim {

    //// class and interface definitions

    public interface IAnimPlayable {
        /**
         * Triggers an animation event
         * @param evt Animation event type
         * @param value Value/param associated with the event
         * @param time Timeline timestamp of the animation
         */
        void animEvent(Event evt, Object value, double time) throws InterruptedException;
    }

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

    //// implementation

    private ArrayList<Command> Commands = new ArrayList<Command>();
    private double AnimTime = 0.0;
    private String Name;

    public LightAnim(String name) {
        Name = name;
        createAnim();
    }

    public double getAnimTime() {
        return AnimTime;
    }

    public abstract void createAnim();

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

    /**
     * Plays the animation and blocks until the animation is complete
     * @param onPlayable Callback object to play animation on
     */
    public void playAnim(IAnimPlayable onPlayable) {
        System.out.printf("playAnim() '%s' %.2fs\n", Name, AnimTime);
        for (Command cmd : Commands) {
            try {
                onPlayable.animEvent(cmd.Evt, cmd.Value, cmd.Time);
            } catch (InterruptedException ex) {
                System.err.printf("playAnim() %s(%s) interrupted\n", cmd.Evt, cmd.Value);
            }
        }
    }
}
