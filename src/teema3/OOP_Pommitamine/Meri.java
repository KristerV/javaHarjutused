package teema3.OOP_Pommitamine;

import java.util.Arrays;

/**
 * Created by kviirsaa on 21.11.15.
 */
public class Meri {
    private int mereServaPikkus;
    private Laev[] laevastik = new Laev[5];

    // Klass Meri konstruktor, ehk esimene meetod mis käivitub kohe objekti välja kutsudes.
    public Meri(int pikkus) {
        System.out.println("START MERI");
        mereServaPikkus = pikkus; // salvestame klassi külge väärtuse, hiljem kindlasti vaja
        looLaevastik();
    }

    private void looLaevastik() {
        for (int i = 0; i < laevastik.length; i++) { // laevastikku tuleb 5 laeva (vt. laevastik definitsioon)
            laevastik[i] = new Laev(mereServaPikkus); // loo uus laev ja salvesta laevastik muutujasse
        }
    }

    // Kasutame siis, kui tahame teada, kas mäng on läbi
    public boolean kasOnLaevuElus() {
        for (Laev laev : laevastik) {
            boolean elus = laev.kasOledElus();
            if (elus) {
                return true;
            }
        }
        return false;
    }

    // Kasutame pommitamisel. Meri ei tea kus laevad on, küsime siis laevade endi käest.
    public boolean kasKeegiSaiPihta(int[] lask) {
        for (Laev laev : laevastik) { // foreach tsükkel, googelda.
            boolean pihtas = laev.kasSaidPihta(lask);
            if (pihtas) {
                return true;
            }
        }
        return false;
    }

    // Tahame mängu seisu näha - millised laevad on juba põhja lastud.
    public void kuvaSeis() {

        // Loome laua maatriksi
        int[][] laud = new int[mereServaPikkus][mereServaPikkus];

        // Iga laeva kohta laevastikus
        for (Laev laev : laevastik) {
            int[] koordinaadid = laev.annaKoordinaadid(); // küsi kordinaadid
            boolean elus = laev.kasOledElus(); // ja kas on elus
            int x = koordinaadid[0];
            int y = koordinaadid[1];
            if (!elus) { // kui ei ole elus (näita ainult pihta saadud laevu)
                laud[y][x] = 2; // siis sisesta laua maatriksile märge põhja läinud laevast.
            }
        }

        // Prindi uus maatriks välja
        for (int i = 0; i < laud.length; i++) {
            System.out.println(Arrays.toString(laud[i]));
        }
    }
}












