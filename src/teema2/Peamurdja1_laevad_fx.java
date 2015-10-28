package teema2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 1. Kasuta JavaFX, et joonistada laud ja näita mängu kulgemist.
 *      http://i200.itcollege.ee/javafx
 *      http://i200.itcollege.ee/javafx-layout-GridPane
 */
public class Peamurdja1_laevad_fx extends Application {
    int[][] laud;

    @Override
    public void start(Stage primaryStage) throws Exception {
        genereeriLaud(9, 9);
        kaivitaMang();
    }

    private void kaivitaMang() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("START");
        while(laevuAlles(laud)) {
            System.out.println("\n\n");

            System.out.println("Sisesta X kordinaat:");
            int x = scanner.nextInt() - 1;
            System.out.println("Sisesta Y kordinaat:");
            int y = scanner.nextInt() - 1;

            if (x < 0 || y < 0 || x > laud.length || y > laud[0].length) {
                System.out.printf("Mängu laud on %s-%s", laud.length, laud[0].length);
                continue;
            }

            if (laud[x][y] == 1) {
                System.out.println("Said pihta!");
                laud[x][y] = 2;
            } else if (laud[x][y] == 2) {
                System.out.println("Kõmmutad vrakki.");
            } else if (laud[x][y] == 0) {
                System.out.println("Mööda.");
            }
        }
        System.out.println("Said laevadest jagu!");
    }

    private void genereeriLaud(int laius, int pikkus) {

        laud = new int[laius][pikkus];

        for (int i = 0; i < laius; i++) {
            for (int j = 0; j < pikkus; j++) {
                laud[i][j] = (int) (Math.random() * 2);
            }
        }

        System.out.println("Genereeritud laud:");
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(laud[i]));
        }
    }

    private static boolean laevuAlles(int[][] laud) {
        for (int i = 0; i < laud.length; i++) {
            for (int j = 0; j < laud[i].length; j++) {
                if (laud[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
