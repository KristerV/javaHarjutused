package teema1;

/**
 * Kasutades while() {} tsükleid, lahendada järgmised ülesanded:
 * 1. Trükkida ekraanile numbrid 10 kuni 1
 * 2. Trükkida ekraanile paaritud arvud vahemikus 0 kuni 10
 * 3. Trükkida ekraanile liitmise tabel:
 *    0  1  2  3  4  5  6  7  8  9
 *    1  2  3  4  5  6  7  8  9 10
 *    2  3  4  5  6  7  8  9 10 11
 *    3  4  5  6  7  8  9 10 11 12
 *    4  5  6  7  8  9 10 11 12 13
 *    5  6  7  8  9 10 11 12 13 14
 *    6  7  8  9 10 11 12 13 14 15
 *    7  8  9 10 11 12 13 14 15 16
 *    8  9 10 11 12 13 14 15 16 17
 *    9 10 11 12 13 14 15 16 17 18
 */
public class Harjutus1_tsyklid {
    public static void main(String[] args) {

        // 1. numbrid kümnest üheni
        int count = 10;
        while (count > 0) {
            System.out.println(count);
            count = count - 1;
        }

        // 2. Paaritud arvud 0st 10ni
        int count2 = 0;
        while (count2 <= 10) {
            if (count2 % 2 == 1) {
                System.out.println(count2);
            }
            count2 = count2 + 1;
        }

        // 3. Tabel
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.printf("%3d", i + j);
            }
            System.out.println();
        }
    }
}
