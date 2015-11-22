package teema3.SQL;

import java.sql.*;
import java.util.HashMap;

public class Andmebaas {
    Connection c = null;
    String dbName = "test.db";

    public Andmebaas() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void looTabelid() {
        try {
            Statement stat = c.createStatement();
            String sql = "CREATE TABLE USERS (ID INT AUTO_INCREMENT, USERNAME TEXT, PASSWORD TEXT, FULLNAME TEXT, NUMBER INT, ADDRESS TEXT)";
            stat.executeUpdate(sql);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Tabelid on olemas");
    }

    public void register(String username, String password) {
        try {
            Statement stat = c.createStatement();
            String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES ('"+username+"','"+password+"')";
            stat.executeUpdate(sql);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Insert läks hästi");
    }

    public boolean login(String username, String password) {
        try {
            Statement stat = c.createStatement();
            String sql = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);

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

    public void sulgeYhendus() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Ühendus suletud");
    }

    public HashMap getUser(String kasutajanimi) {
        HashMap andmed = new HashMap();
        try {
            Statement stat = c.createStatement();
            String sql = "SELECT * FROM USERS WHERE USERNAME = '" + kasutajanimi + "' LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);

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
            Statement stat = c.createStatement();
            String sql = String.format("UPDATE USERS SET USERNAME = '%s', PASSWORD = '%s', FULLNAME = '%s', NUMBER = '%s', ADDRESS = '%s';", username, password, fullname, number, address);
            stat.executeUpdate(sql);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}