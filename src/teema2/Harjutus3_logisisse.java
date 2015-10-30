package teema2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 1. Loo sisse logimise ekraan (ainult visuaal)
 * 2. Määra üks võimalik kasutaja ja parool (andmebaasi veel ei kasuta)
 * 3. Ebaõnnestunud katse näitab kasutajale errorit
 * 4. Õnnestunud katse puhul vaheta pilt uue vastu (kasvõi roheline ring), .
 */
public class Harjutus3_logisisse extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Lava seadistamine
        VBox layout = new VBox();
        Scene scene = new Scene(layout, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        // Visuaalid
        Label kasutajaLabel = new Label("Kasutajanimi");
        TextField kasutajaInput = new TextField();
        Label paroolLabel = new Label("Parool");
        PasswordField paroolInput = new PasswordField();
        Button nupp = new Button("Logi sisse");
        Label teade = new Label();
        layout.getChildren().addAll(kasutajaLabel, kasutajaInput, paroolLabel, paroolInput, nupp, teade);

        // Sisse logitud vaade (ära  kohe näita)
        StackPane seesLayout = new StackPane();
        Scene seesScene = new Scene(seesLayout, 200, 200);
        Label seesLabel = new Label("Oled sisse logitud!");
        seesLayout.getChildren().add(seesLabel);

        // Nupu tegevus
        nupp.setOnAction(event -> {
            String kasutajanimi = kasutajaInput.getText();
            String parool = paroolInput.getText();
            if (kasutajanimi.toLowerCase().equals("kaarel") && parool.equals("laika123")) {
                primaryStage.setScene(seesScene);
            } else {
                teade.setText("Sisse logimine ebaõnnestus");
                kasutajaInput.setText(""); // tühjenda väli
                paroolInput.setText(""); // tühjenda väli
            }
        });

        // Kui kasutaja enterit vajutab siis käitu nagu nuppu oleks vajutatud
        paroolInput.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                nupp.fire();
            }
        });
        kasutajaInput.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                nupp.fire();
            }
        });

    }
}
