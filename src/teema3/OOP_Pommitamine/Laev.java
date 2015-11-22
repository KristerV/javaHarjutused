package teema3.OOP_Pommitamine;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by kviirsaa on 21.11.15.
 */
public class Laev {
    private int[] koordinaadid;
    private boolean elus = true;

    public Laev(int mereServaPikkus) {
        System.out.println("START LAEV");

        // Konstruktor on parem tühjaks jätta. Seega tegevuse jaoks kutsume välja meetodi.
        genereeriKordinaadid(mereServaPikkus);
    }

    private void genereeriKordinaadid(int mereServaPikkus) {
        Random rand = new Random();
        int x = rand.nextInt(mereServaPikkus);
        int y = rand.nextInt(mereServaPikkus);
        koordinaadid = new int[]{x, y};
        System.out.println(Arrays.toString(koordinaadid));
    }

    // Parem on muutuja private hoida ja teha eraldi meetod väärtuse välja küsimiseks
    // või uue võõrtuse määramiseks - nii on sul rohkem kontrolli ja kindlust, et muutujat
    // ei kuritarvitata (kas sinu enda või teise arendaja poolt).
    public boolean kasOledElus() {
        return elus;
    }

    // Pommitamine ise
    public boolean kasSaidPihta(int[] lask) {
        if (Arrays.equals(lask, koordinaadid) && elus) { // pihta saab saada ainult elus laev, kelle koordinaadid kattuvad
            elus = false;
            return true;
        }
        return false;
    }

    // Jällegi, parem on eraldi meetod kirjutada kui muutuja publicuks teha, et keegi kogemata ei väärtarvita objekti.
    public int[] annaKoordinaadid() {
        return koordinaadid;
    }
}










