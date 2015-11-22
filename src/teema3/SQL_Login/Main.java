package teema3.SQL_Login;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Tere. Sinu käes on näide andmebaaside ülesehitusest Javas, kasutades SQLite'i. Tegu on väga lihtsa andmebaasi
 * süsteemiga, mida võid projektis kasutada.
 *
 * ENNE KUI SAAD ALUSTADA OMA PROJEKTIGA pean sqlite.jar'i alla laadima (https://bitbucket.org/xerial/sqlite-jdbc/downloads),
 * oma projekti tooma (soovitan /lib/ kausta) ja aktiveerima - klikkides .jar failile parema klikiga ja valides "Add as Library".
 * Muide, vahel mingil veidral põhjusel peab uuesti "Add as Library.." käsku käivitama.
 *
 * Edasi vaata Andmenaas.java kuidas seda aska kasutada. Käesolevas programmis on kaks akent: LoginScreen ja UserDetails. Ehk
 * kasutaja saab esiteks registreerida või sisse logida ja siis oma andmeid muuta.
 *
 * Tähtis on andmebaaside kohta teada seda, et piltlikult käituvad SQL_Login andmebaasi andmed kui exceli tabelid. Enne kui
 * midagi pärida või muuta saad pead andmebaasiga ühenduse looma. Pärast andmebaasi kasutamist pead ühenduse ka sulgema - muidu
 * kulutad asjatult arvuti ressursse.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new LoginScreen(); // Käivitame login akna
    }
}
