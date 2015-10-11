package teema1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * NB! Lahenda käesolev ülesanne konsoolis. Pole vaja JavaFXi siia segada.
 *
 * 1. Kirjuta lihtne laevade pommitamise mäng, kus arvuti genereerib 1x1
 *    laevad mööda 1 dimensioonilist lauda. See tähendab, et mängulaud
 *    ei ole mitte ruudustik, vaid üks rida.
 *
 * 2. Täienda mängu nii, et nüüd oleks laual ruudustik.
 *
 * 3. Kas oskad ka nii, et laevad ei ole 1x1 vaid 1x2 ja 1x3 ja orienteeruvad
 *    nii vertikaalselt kui ka horisontaalselt?
 */
public class Peamurdja3_laevad {
    public static void main(String[] args) {

        int[] laud = {randLaev(), randLaev(), randLaev(), randLaev(), randLaev(), randLaev(), randLaev()};

        Scanner kasutaja = new Scanner(System.in);

        while (!gameover(laud)) {
            System.out.println(Arrays.toString(laud));
            System.out.println("Sisesta number 1-"+laud.length);
            int sisestus = kasutaja.nextInt() - 1;

            int hit = laud[sisestus];
            if (hit == 0) {
                System.out.println("haha, mööda!");
            } else if (hit == 2) {
                System.out.println("kuule kuule, siia oled juba lasknud.");
            } else if (hit == 1) {
                System.out.println("Yea, pihtas!");
                laud[sisestus] = 2;
            }
        }
        System.out.println("Mäng läbi!");
    }

    public static int randLaev() {
        int tulemus = (int) (Math.random() * 2);
        return tulemus;
    }

    public static boolean gameover(int[] laud) {
        for (int i = 0; i < laud.length; i++) {
            if (laud[i] == 1) {
                return false;
            }
        }
        return true;
    }
}
