package teema3.OOP_Pommitamine;

/**
 * Mere ja laevastiku genereerimine
 * Kuni laevu on elus,
 *     küsi kasutajalt kordinaadid
 *     pommita merd
 *     anna tatgasisidet
 * Mäng on läbi
 */
public class Mang {
    public Mang() {
        System.out.println("START MÄNG");

        // Loome objektid, mida hiljem vaja
        Meri meri = new Meri(10); // Meri genereerib ka laevastiku oma konstruktoris
        Mangija mangija = new Mangija();

        // Mängu loogika, ehk sisu
        while (meri.kasOnLaevuElus()) { // Kui on laevu alles
            meri.kuvaSeis(); // renderda grid laevadega meile ette
            int[] lask = mangija.kysiLasuKoordinadid(); // Küsi kasutajalt laskmise kordinaate
            boolean pihtas = meri.kasKeegiSaiPihta(lask); // kontrolli kas keegi sai pihta (meri teab)
            if (pihtas) {
                mangija.pihtas();
            } else {
                mangija.moodas();
            }
        }
        mangija.gameover();
    }
}















