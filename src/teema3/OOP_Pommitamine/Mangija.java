package teema3.OOP_Pommitamine;

import java.util.Scanner;

/**
 * Created by krister on 20.11.15.
 */
public class Mangija {
    Scanner sc = new Scanner(System.in);
    public int[] kuhuLasta() {
        System.out.printf("\nSisesta laske kordinaadid formaadis xxx-yyy: ");
        String[] in = sc.nextLine().split("-");
        int x = Integer.parseInt(in[0]) - 1;
        int y = Integer.parseInt(in[1]) - 1;
        return new int[]{x, y};
    }

    public void pihtas() {
        System.out.println("Pihtas!");
    }

    public void mooda() {
        System.out.println("Mööda");
    }

    public void gameover() {
        System.out.println("Mäng on läbi");
    }
}
