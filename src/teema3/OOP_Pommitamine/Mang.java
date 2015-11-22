package teema3.OOP_Pommitamine;

public class Mang {
    public Mang() {
        Meri meri = new Meri(10);
        Mangija m = new Mangija();
        while (meri.laevuAlles()) {
            meri.kuvaLaud();
            int[] lask = m.kuhuLasta();
            boolean pihtas = meri.lask(lask);
            if (pihtas) {
                m.pihtas();
            } else {
                m.mooda();
            }
        }
        m.gameover();
    }
}
