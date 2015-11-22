package teema3.SQL;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by krister on 20.11.15.
 */
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Andmebaas a = new Andmebaas();
        // a.looTabelid();
        a.sulgeYhendus();
        new LoginScreen();
    }
}
