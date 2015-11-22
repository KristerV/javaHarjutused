package teema3.OOP_Pommitamine;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by krister on 20.11.15.
 */
public class Laev {
    private int[][] laevaKordinaadid;
    private staatused[] laevaSeis;

    public enum staatused {
        ELAB, PIHTAS;

    }
    public Laev(int laevaPikkus, int lauaLaius, int lauaKorgus) {

        // Genereeri laeva laevaKordinaadid
        laevaKordinaadid = new int[laevaPikkus][];
        genereeriKordinaadid(laevaPikkus, lauaLaius, lauaKorgus);

        // Märgi kõik ruudud ELAVaks
        laevaSeis = new staatused[laevaPikkus];
        for (int i = 0; i < laevaPikkus; i++) {
            laevaSeis[i] = staatused.ELAB;
        }
    }
    private void genereeriKordinaadid(int laevaPikkus, int lauaLaius, int lauaKorgus) {
        Random rand = new Random();
        int x = Math.abs(rand.nextInt(lauaLaius));
        int y = Math.abs(rand.nextInt(lauaKorgus));
        int[] punkt = {x, y};
        boolean suund = rand.nextBoolean();
        for (int i = 0; i < laevaPikkus; i++) {
            if (x + laevaPikkus > lauaLaius) {
                punkt[0] -= 1;
            } else if (y + laevaPikkus > lauaKorgus) {
                punkt[1] -= 1;
            } else if (suund) {
                punkt[0] += 1;
            } else {
                punkt[1] += 1;
            }
            laevaKordinaadid[i] = Arrays.copyOf(punkt, punkt.length);
        }
    }

    public void kuvaKordinaadid() {
        System.out.println("Laeva laevaKordinaadid:");
        for (int i = 0; i < laevaKordinaadid.length; i++) {
            System.out.println(Arrays.toString(laevaKordinaadid[i]));
        }
    }

    public boolean oledElus() {
        for (staatused staatus : laevaSeis) {
            if (staatus == staatused.ELAB) {
                return true;
            }
        }
        return false;
    }

    public boolean lask(int[] laskeKordinaadid) {
        for (int i = 0; i < laevaKordinaadid.length; i++) {
            int[] kordinaadid = laevaKordinaadid[i];
            if (Arrays.equals(kordinaadid, laskeKordinaadid) && laevaSeis[i] == staatused.ELAB) {
                laevaSeis[i] = staatused.PIHTAS;
                return true;
            }
        }
        return false;
    }

    public int[][] getLaevaKordinaadid() {
        return laevaKordinaadid;
    }

    public boolean kasElusKordinaadil(int[] kordinaatElus) {
        for (int i = 0; i < laevaKordinaadid.length; i++) {
            if (laevaKordinaadid[i].equals(kordinaatElus)) {
                return laevaSeis[i] == staatused.ELAB;
            }
        }
        return false;
    }

    public staatused[] getLaevaSeis() {
        return laevaSeis;
    }
}
