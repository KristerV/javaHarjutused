package teema2;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 0. Ehita 1x1 laevade pommitamine 9x9 maatriksile, nii nagu teema1 Peamurdja3_laevad ülesanne 2 tulemus.
 *
 * 1. Kasuta JavaFX, et joonistada laud ja näita mängu kulgemist.
 *      http://i200.itcollege.ee/javafx
 *      http://i200.itcollege.ee/javafx-layout-GridPane
 *
 * 2. Täienda graafikat nii, et kasutaja saaks rünnata kasutades hiire klikki.
 *
 *
 * 3. Kasuta JavaFXi kasutajalt nime küsimiseks enne mängu.
 *      http://i200.itcollege.ee/javafx-TextField
 */
public class Peamurdja1_laevad_fx {
    public static void main(String[] args) {

        int laius = 9;
        int pikkus = 9;

        // Loon uue laua 9x9 maatriksi
        int[][] laud = new int[laius][pikkus];

        // Käin kõik x-y asukohad läbi ja genereerin kas 0 või 1
        // Laual on laev kas 0, 1 või 2.
        // 0: tühi
        // 1: laev
        // 2: pihta saanud laev
        for (int i = 0; i < laius; i++) { // X telg
            for (int j = 0; j < pikkus; j++) { // Y telg
                laud[i][j] = (int) (Math.random() * 1.01); // Aseta laua positsioonile i-j (x-y) 0 või 1
            }
        }

        // Näita lauda lihtsalt arendamise lihtsustamiseks
        System.out.println("Genereeritud laud on järgmine:");
        for (int i = 0; i < laius; i++) {
            System.out.println(Arrays.toString(laud[i]));
        }

        // Scanner aitab meil kasutajalt sisendit võtta
        Scanner scanner = new Scanner(System.in);

        // Pommitamine käib while tsükli sees.
        // kuni (tingimus) {tegevus}
        // Antud juhul käivitatakse meetod nimega laevuAlles(), mis on defineeritud faili lõpus.
        while(laevuAlles(laud)) {

            // Lisa kaks tühja rida (\n on "uus rida")
            System.out.println("\n\n");

            // Küsi kasutajalt sisendut
            System.out.println("Sisesta X kordinaat:"); // näita juhendit
            int x = scanner.nextInt() - 1;              // küsi sisendit X
            System.out.println("Sisesta Y kordinaat:"); // näita juhendit
            int y = scanner.nextInt() - 1;              // küsi sisendit
            // "-1" on sisendil sellepärast, et kasutaja mängib laiusel 1-9
            // aga massiivide loendamine algab nullist ja tegelikult käib
            // mäng laiusel 0-8

            // Kontrolli, et sisend on mängu piires
            // "kui (x < 1 VÕI y < 1 VÕI x > 9 VÕI y > 9) {siis...}"
            if (x < 1 || y < 1 || x > 9 || y > 9) {
                System.out.println("Mängu laud on 9x9");
                continue; // katkesta while tsükli praegune ring ja hakka otsast
            }

            // Kas mängija sai pihta?
            if (laud[x][y] == 1) {                     // kui x-y on 1
                System.out.println("Said pihta!");     // teata tagajärjest
                laud[x][y] = 2;                        // muuda laua seisu positsioonil x-y
            } else if (laud[x][y] == 2) {              // kui x-y on 1
                System.out.println("Kõmmutad vrakki.");// teata tagajärjest
            } else if (laud[x][y] == 0) {              // kui x-y on 1
                System.out.println("Mööda.");          // teata tagajärjest
            }
        }

        // Kui tsükkel on katkenud siis järelikult on mäng läbi
        System.out.println("Said laevadest jagu!");
    }

    // Kas laual on pommitamata laevu alles?
    // Tagastab booleani, ehk true või false
    // vastu võtab int[][] ehk maatriksi, mille nimetame meetodi mugavuse jaoks "laud"
    private static boolean laevuAlles(int[][] laud) {

        // Iga Y telje rea kohta
        for (int i = 0; i < laud.length; i++) {

            // Iga X telje rea kohta
            for (int j = 0; j < laud[i].length; j++) {

                // Kui x-y väärtus on 1, siis
                if (laud[i][j] == 1) {

                    // tagasta true, ehk tõene.
                    return true;
                    // return katekstab reeglina meetodi töö.
                    // Programm jäetkub sealt, kus meetod välja kutsuti.
                }
            }
        }

        // Kui programm siia on jõudnud, siis järelikult "return true" pole
        // kordagi käinud, ehk laual ei ole ühtegi laeva.
        return false;
    }
}
