package teema3.OOP_Pommitamine;

import java.util.Scanner;

/**
 * Created by kviirsaa on 21.11.15.
 */
public class Mangija {
    private Scanner sc = new Scanner(System.in); // kasutame scannerit korduvalt, ei taha uuesti luua iga kord.

    public int[] kysiLasuKoordinadid() {
        System.out.println("Palun sisesta lasu koordinaadid formaadis xxx-yyy");
        String[] sisestus = sc.nextLine().split("-");
        int x = Integer.parseInt(sisestus[0]);
        int y = Integer.parseInt(sisestus[1]);
        return new int[]{x, y};
    }

    public void pihtas() {
        System.out.println("Yea, said pihta!");
    }

    public void moodas() {
        System.out.println("Ou, mööda.");
    }

    public void gameover() {
        System.out.println("GAME OVER");
    }
}
