package teema3.OOP_Pommitamine;

/**
 * Created by krister on 20.11.15.
 */
public class Meri {
    Laev[] laevastik = new Laev[5];
    int lauaServaPikkus;

    public Meri(int laius) {
        lauaServaPikkus = laius;
        genereeriLaevastik();
    }

    private void genereeriLaevastik() {
        for (int i = 0; i < laevastik.length; i++) {
            laevastik[i] = new Laev(i+1, lauaServaPikkus, lauaServaPikkus);
        }
    }

    public void kuva() {
        for (Laev laev : laevastik) {
            laev.kuvaKordinaadid();
        }
    }

    public boolean laevuAlles() {
        for (Laev laev : laevastik) {
            if (laev.oledElus()) {
                return true;
            }
        }
        return false;
    }

    public boolean lask(int[] laskeKordinaadid) {
        for (Laev laev : laevastik) {
            boolean pihtas = laev.lask(laskeKordinaadid);
            if (pihtas) {
                return true;
            }
        }
        return false;
    }

    public void kuvaLaud() {
        char[][] laud = new char[lauaServaPikkus][lauaServaPikkus];
        for (int i = 0; i < laud.length; i++) {
            for (int j = 0; j < laud[i].length; j++) {
                laud[i][j] = '~';
            }
        }
        for (int i = 0; i < laevastik.length; i++) {
            Laev laev = laevastik[i];
            int[][] kordinaadid = laev.getLaevaKordinaadid();
            for (int j = 0; j < kordinaadid.length; j++) {
                int[] k = kordinaadid[j];
                if (laev.kasElusKordinaadil(k)) {
                    laud[k[0]][k[1]] = 'O';
                } else {
                    laud[k[0]][k[1]] = 'X';
                }
            }
        }
        for (int i = 0; i < laud.length; i++) {
            for (int j = 0; j < laud[i].length; j++) {
                System.out.print(" " + laud[j][i]);
            }
            System.out.println();
        }
    }
}
