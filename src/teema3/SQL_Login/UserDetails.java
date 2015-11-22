package teema3.SQL_Login;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by krister on 20.11.15.
 */
public class UserDetails {
    private String kasutajanimi;
    private Stage stage = new Stage();
    private Button updateButton;
    private Button logoutButton;
    private HashMap<String, String> andmed;

    public UserDetails(String kasutajaSisse) {
        kasutajanimi = kasutajaSisse;
        setupStage();
        setupUpdate();
        setupLogout();
    }

    private void setupLogout() {
        logoutButton.setOnAction(event -> {
            stage.close();
            new LoginScreen();
        });
    }

    private void setupUpdate() {

    }

    private void setupStage() {
        TilePane tile = new TilePane();
        tile.setPrefColumns(2);
        Scene scene = new Scene(tile);
        stage.setScene(scene);

        Andmebaas a = new Andmebaas();
        andmed = a.getUser(kasutajanimi);

        TextField kasutajanimiField = new TextField(andmed.get("username"));
        PasswordField paroolField = new PasswordField();
        paroolField.setText(andmed.get("password"));
        TextField fullnameField = new TextField(andmed.get("fullname"));
        TextField numberField = new TextField(andmed.get("number"));
        TextField aadressField = new TextField(andmed.get("address"));
        updateButton = new Button("Uuenda andmeid");

        updateButton.setOnAction(event -> {
            HashMap<String, String> uuedAndmed = new HashMap<String, String>();
            uuedAndmed.put("username", kasutajanimiField.getText());
            uuedAndmed.put("password", paroolField.getText());
            uuedAndmed.put("fullname", fullnameField.getText());
            uuedAndmed.put("number", numberField.getText());
            uuedAndmed.put("address", aadressField.getText());

            a.uuendaKasutajaAndmeid(uuedAndmed);
            a.sulgeYhendus();
            stage.close();
            new UserDetails(kasutajanimiField.getText());
        });

        Label l2 = new Label("Kasutajanimi");
        Label l3 = new Label("Parool");
        Label l4 = new Label("Päris nimi");
        Label l5 = new Label("Telefoni number");
        Label l6 = new Label("Aadress");
        logoutButton = new Button("Logi välja");
        tile.getChildren().addAll(l2, kasutajanimiField, l3, paroolField, l4, fullnameField, l5, numberField, l6, aadressField, logoutButton, updateButton);

        stage.show();
    }
}
