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

    @Override
    public void start(Stage primaryStage) throws Exception {

        int laius = 9;
        int pikkus = 9;

        int[][] laud = new int[laius][pikkus];

        for (int i = 0; i < laius; i++) {
            for (int j = 0; j < pikkus; j++) {
                laud[i][j] = (int) (Math.random() * 1.01);
            }
        }

        System.out.println("Genereeritud laud on järgmine:");
        for (int i = 0; i < laius; i++) {
            System.out.println(Arrays.toString(laud[i]));
        }

        Scanner scanner = new Scanner(System.in);

        while(laevuAlles(laud)) {
            System.out.println("\n\n");

            System.out.println("Sisesta X kordinaat:");
            int x = scanner.nextInt() - 1;
            System.out.println("Sisesta Y kordinaat:");
            int y = scanner.nextInt() - 1;

            if (x < 1 || y < 1 || x > 9 || y > 9) {
                System.out.println("Mängu laud on 9x9");
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
