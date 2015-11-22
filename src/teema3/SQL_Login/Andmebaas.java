package teema3.SQL_Login;

import java.sql.*;
import java.util.HashMap;

public class Andmebaas {
    Connection conn = null;

    // Constructor, ehk meetod mis käivitub kohe objekti välja kutsumisel
    public Andmebaas() {
        looYhendus();
        looTabel();
    }

    // Et andmebaasi kasutada peame sellega esiteks ühenduse looma
    private void looYhendus() {
        try {
            Class.forName("org.sqlite.JDBC");                          // Lae draiver sqlite.jar failist
            conn = DriverManager.getConnection("jdbc:sqlite:test.db"); // loo ühendus andmebaasi failiga
        } catch ( Exception e ) {                                      // püüa kinni võimalikud errorid
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Opened database successfully");            // lihtsalt meie enda jaoks teade
    }

    // Et andmebaasist kasu oleks, loome uue tabeli. See on nagu uus 'sheet' excelis.
    public void looTabel() {
        // Käsk ise on CREATE TABLE ja sulgude vahel on kõik tulbad, mida tahan, et tabel hoiaks.
        String sql = "CREATE TABLE IF NOT EXISTS USERS (ID INT AUTO_INCREMENT, USERNAME TEXT, " + // jätkub järgmisel real
                "PASSWORD TEXT, FULLNAME TEXT, NUMBER INT, ADDRESS TEXT);";
        teostaAndmebaasiMuudatus(sql);
    }

    // Andmebaasi muudatused ei tagasta väärtusi (erinevalt
    // päringutest) ja on lihtne eraldi meetodi tuua.
    private void teostaAndmebaasiMuudatus(String sql) {
        try {
            // Statement objekt on vajalik, et SQL_Login käsku käivitada
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            stat.close(); // Statement tuleb samuti kinni panna nagu ka Connection.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registreeriKasutaja(String username, String password) {
        // Andmete sisestamiseks on käsk INSERT. Esimestes sulgudes on tulabad KUHU salvestada,
        // teistes sulgudes VALUES() on MIDA salvestada.
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES ('"+username+"','"+password+"')";
        teostaAndmebaasiMuudatus(sql);
    }

    public boolean login(String username, String password) {
        try {
            Statement stat = conn.createStatement();

            // SELECT on nagu excelis hiirega "selekteeriks" mingeid kaste. SQLis tähendab konkreetselt,
            // et milliste tulpade infot soovid kätte saada. WHERE'ga käsed välja võtta ainult tingimustele
            // vastavad väljad.
            String sql = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' LIMIT 1;";

            // Kuna tegu on päringuga siis käsuks on executeQuery ja ta tagastab andme objekti ResultSet.
            ResultSet rs = stat.executeQuery(sql);

            // Kui Query andmeid ei tagastanud (päring ei toonud tulemusi) siis rs-i kasutada ei saa.
            // Seepärast, kui kasutajat ei eksisteeri tuleb lihtsalt error ja "return" käsuni ei jõutagi.
            // Aga jõutakse lõpu "return false" käsuni küll.
            String dbPassword = rs.getString("password");

            rs.close();
            stat.close();
            return password.equals(dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return false;
    }

    // Kui programmis avad ainult ühendusi ja ühtegi ei sulge siis see kulutab arvuti (serveri) ressursse.
    // Üsna kiiresti võib masina kokku jooksutada.
    public void sulgeYhendus() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Ühendus suletud");
    }

    // Kasutaja andmete päring.
    public HashMap getUser(String kasutajanimi) {
        HashMap andmed = new HashMap();
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM USERS WHERE USERNAME = '" + kasutajanimi + "' LIMIT 1;"; // LIMIT piirab tulemuste arvu.

            ResultSet rs = stat.executeQuery(sql);
            // Kui stat.executeQuery() toob tagasi tühja tulemuse, siis rs'i kasutada ei saa.

            // Kui oleks mitu rida andmeid, peaks tsükliga lahendama while (rs.next()) {}

            // Nopin käsitsi andmed ühelt realt välja
            andmed.put("username", rs.getString("username"));
            andmed.put("password", rs.getString("password"));
            andmed.put("fullname", rs.getString("fullname"));
            andmed.put("number", rs.getString("number"));
            andmed.put("address", rs.getString("address"));

            rs.close();
            stat.close();
            return andmed;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return andmed;
    }

    public void uuendaKasutajaAndmeid(HashMap<String, String> andmed) {
        String username = andmed.get("username");
        String password = andmed.get("password");
        String fullname = andmed.get("fullname");
        String number = andmed.get("number");
        String address = andmed.get("address");

        try {
            Statement stat = conn.createStatement();
            // Andmete uuendamise käsi on UPDATE. SET ütleb, et nüüd tulevad need uued andmed sisse. WHERE ütleb mis ridu uuendada.
            String sql = String.format("UPDATE USERS SET USERNAME = '%s', PASSWORD = '%s', FULLNAME = '%s', NUMBER = '%s', ADDRESS = '%s' WHERE USERNAME = '%s';", username, password, fullname, number, address, username);
            stat.executeUpdate(sql);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}