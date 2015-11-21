package OOP_Pommitamine;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by kviirsaa on 21.11.15.
 */
public class Laev {
    private int[] kordinaadid;
    private boolean elus = true;

    public Laev(int mereServaPikkus) {
        System.out.println("START LAEV");
        genereeriKordinaadid(mereServaPikkus);
    }

    private void genereeriKordinaadid(int mereServaPikkus) {
        Random rand = new Random();
        int x = rand.nextInt(mereServaPikkus);
        int y = rand.nextInt(mereServaPikkus);
        kordinaadid = new int[]{x, y};
        System.out.println(Arrays.toString(kordinaadid));
    }

    public boolean kasOledElus() {
        return elus;
    }

    public boolean kasSaidPihta(int[] lask) {
        if (Arrays.equals(lask, kordinaadid) && elus) {
            elus = false;
            return true;
        }
        return false;
    }

    public int[] annaKoordinaadid() {
        return kordinaadid;
    }
}










