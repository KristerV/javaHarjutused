package lib;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Foor {

    //// constant definitions

    private static final ValgusV2rv HALL     = ValgusV2rv.HALL;
    private static final ValgusV2rv PUNANE   = ValgusV2rv.PUNANE;
    private static final ValgusV2rv KOLLANE  = ValgusV2rv.KOLLANE;
    private static final ValgusV2rv ROHELINE = ValgusV2rv.ROHELINE;

    public static final FooriSuund YLEMINE = FooriSuund.YLEMINE;
    public static final FooriSuund ALUMINE = FooriSuund.ALUMINE;
    public static final FooriSuund VASAK   = FooriSuund.VASAK;
    public static final FooriSuund PAREM   = FooriSuund.PAREM;
    public static final FooriSuund DEFAULT   = FooriSuund.DEFAULT;

    public enum FooriSuund { YLEMINE, ALUMINE, VASAK, PAREM, DEFAULT }
    public enum ValgusV2rv { HALL,    PUNANE,  KOLLANE, ROHELINE }

    //// events and commands

    private enum FooriSyndmus {
        SYYTA,    /* set light status on */
        KUSTUTA,  /* set light status off */
        VAHETA,   /* toggle light status (on/off) */
        PAUS,     /* add animation delay on timeline */
    }

    private class FooriK2sk {
        public FooriSyndmus Syndmus;
        public Object       Param;
        public double       Aeg;     // this is just for more timeline info, not actually required for anything
        public FooriK2sk(FooriSyndmus syndmus, Object param, double aeg) {
            Syndmus = syndmus;
            Param = param;
            Aeg = aeg;
        }
    }

    //// private state variables

    private StackPane Stack;
    private Scene Scene;
    private Stage Stage;


    private int StseeniSuurus = 700;
    private int ValguseLaius  = StseeniSuurus / 4;
	private FooriSuund Suund  = DEFAULT;
    private Color[]  V2rvid   = { Color.GRAY, Color.RED,    Color.YELLOW, Color.SPRINGGREEN };
    private Circle[] Tuled    = { null,       new Circle(), new Circle(), new Circle()      };
	private Label    InfoTekst= new Label();

    private ArrayList<FooriK2sk> Syndmused   = new ArrayList<FooriK2sk>();
    private double               AnimKestus  = 0.0;
    private Thread               AnimThread  = null;
    private volatile boolean     AnimJookseb = false;


    public Foor() {
        setupStage();
        drawLight(FooriSuund.DEFAULT);
    }

    public Foor(FooriSuund fooriSuund) {
        System.err.printf("Palun anna teise argumendina kaasa primaryStage.\n");
    }

    public Foor(FooriSuund fooriSuund, Stage primaryStage) {
        setupStage(primaryStage);
        drawLight(fooriSuund);
    }


    //// animation related stuff

    private void lisaK2sk(FooriSyndmus evt, Object value) {
        Syndmused.add(new FooriK2sk(evt, value, AnimKestus /* current time on anim */));
    }

    public void syytaPunane()     { lisaK2sk(FooriSyndmus.SYYTA, Foor.PUNANE);    }
    public void syytaKollane()    { lisaK2sk(FooriSyndmus.SYYTA, Foor.KOLLANE);   }
    public void syytaRoheline()   { lisaK2sk(FooriSyndmus.SYYTA, Foor.ROHELINE);  }

    public void kustutaPunane()   { lisaK2sk(FooriSyndmus.KUSTUTA, Foor.PUNANE);  }
    public void kustutaKollane()  { lisaK2sk(FooriSyndmus.KUSTUTA, Foor.KOLLANE); }
    public void kustutaRoheline() { lisaK2sk(FooriSyndmus.KUSTUTA, Foor.ROHELINE);}

    public void vahetaPunast()    { lisaK2sk(FooriSyndmus.VAHETA, Foor.PUNANE);   }
    public void vahetaKollast()   { lisaK2sk(FooriSyndmus.VAHETA, Foor.KOLLANE);  }
    public void vahetaRohelist()  { lisaK2sk(FooriSyndmus.VAHETA, Foor.ROHELINE); }

    public void paus(double pausSekundites) {
        if (pausSekundites > 0.0) {
            lisaK2sk(FooriSyndmus.PAUS, pausSekundites);
            AnimKestus += pausSekundites;
        }
        alustaAnimatsiooni();
    }

    //// internal manip

    private void vahetaTuld(ValgusV2rv v2rv) {
        muudaTuld(v2rv, !onTuliSees(v2rv));
    }

    private boolean onTuliSees(ValgusV2rv lightId) {
        int index = lightId.ordinal();
        return Tuled[index].getFill().equals(V2rvid[index]);
    }

    private void muudaTuld(ValgusV2rv v2rv, boolean onSees) {
        int indeks = v2rv.ordinal();
        Circle tuli = Tuled[indeks];

        // call setFill on main UI thread, otherwise we can get a race condition
        Platform.runLater(()-> tuli.setFill(V2rvid[onSees ? indeks : HALL.ordinal()]));

        // "YLEMINE::KOLLANE switched on"
        // System.out.printf("%7s::%-8s %s\n", Suund, v2rv, onSees ? "on" : "off");
    }

	private void muudaInfot(String infoTekst) {
		Platform.runLater(()-> InfoTekst.setText(infoTekst));
	}


    //// Animation playing and stopping

    private boolean animatsioonJookseb() {
        return AnimJookseb;
    }

    private void kustutaAnimatsioon() {
        peataAnimatsioon();
        Syndmused.clear();
    }

    private void peataAnimatsioon() {
        if (AnimJookseb) AnimJookseb = false;
        if (onTuliSees(ValgusV2rv.PUNANE))    muudaTuld(ValgusV2rv.PUNANE, false);
        if (onTuliSees(ValgusV2rv.KOLLANE))   muudaTuld(ValgusV2rv.KOLLANE, false);
        if (onTuliSees(ValgusV2rv.ROHELINE))  muudaTuld(ValgusV2rv.ROHELINE, false);
    }

    private void alustaAnimatsiooni() {
        if (AnimJookseb)
            return;

        AnimJookseb = true;
        AnimThread = new Thread(()-> {
            // repeat animation while not set as stopped
            while (AnimJookseb) {
                for (int i = 0; i < Syndmused.size(); ++i) {
                    if (!AnimJookseb)
                        return; // exit thread
                    animatsiooniK2sk(Syndmused.get(i));
                }
                return; // infinite loop disabled
            }
        });
        AnimThread.start();
    }

    private void animatsiooniK2sk(FooriK2sk k2sk) {
        try {
			double aeg = k2sk.Aeg;
			muudaInfot(String.format("t=%.1fs", aeg));
			switch (k2sk.Syndmus) {
                case SYYTA:    muudaTuld((ValgusV2rv) k2sk.Param, true);   break;
                case KUSTUTA:  muudaTuld((ValgusV2rv) k2sk.Param, false);  break;
                case VAHETA:   vahetaTuld((ValgusV2rv) k2sk.Param);        break;
                case PAUS:     magaKatkestusega((Double) k2sk.Param, aeg); break;
            }
        }
        catch (InterruptedException ex) {
            System.err.printf("alustaAnimatsiooni() %s(%s) katkestati\n", k2sk.Syndmus, k2sk.Param);
        }
    }

    private void magaKatkestusega(double sekundeidMagamiseks, double algusAeg) throws InterruptedException {
        int millisekundid = (int)(sekundeidMagamiseks * 1000);

		double aeg = algusAeg;
        for (; millisekundid > 0 && AnimJookseb; millisekundid -= 15, aeg += 0.015) {
            Thread.sleep(15); // sleep only 15s at a time, this allows to check if AnimPlaying

			// update every 0.1 seconds
			if ((aeg - algusAeg) > 0.1) {
				muudaInfot(String.format("t=%.1fs", aeg));
				algusAeg = aeg;
			}
        }
    }

    //// Foor graphics setup

    private Circle tekitaTuli(ValgusV2rv v2rv, String id, int yAsukoht) {
        Circle c = Tuled[v2rv.ordinal()];
        c.setFill(V2rvid[HALL.ordinal()]);
        c.setRadius(ValguseLaius / 2.5);
        c.setTranslateY(yAsukoht);
        c.setId(id);
        return c;
    }

    private void drawLight(FooriSuund fooriSuund) {
        int lightSize = StseeniSuurus / 3;
        double skaala = 0.3;

        InfoTekst.setText("Foor");
        InfoTekst.setScaleX(4.0);
        InfoTekst.setScaleY(4.0);
        InfoTekst.setTranslateX(-ValguseLaius - 40);

        if (fooriSuund != FooriSuund.DEFAULT) {
            Suund = fooriSuund;
            int suund  = fooriSuund.ordinal();
            int orient  = new int[]{ 180,     0,     90,     -90 }[suund];
            int torient = new int[]{ 180,     0,    -90,     +90 }[suund];
            int x       = new int[]{ 0, 0, -lightSize, lightSize }[suund];
            int y       = new int[]{ -lightSize, lightSize, 0, 0 }[suund];

            Stack.setTranslateX(x);
            Stack.setTranslateY(y);
            Stack.setScaleX(skaala);
            Stack.setScaleY(skaala);
            Stack.setRotate(orient);

            InfoTekst.setRotate(torient);
        }

        Rectangle kast = new Rectangle();
        kast.setWidth(ValguseLaius);
        kast.setHeight(ValguseLaius * 3);
        kast.setFill(Color.DIMGRAY);

        Stack.getChildren().addAll(kast,
                tekitaTuli(PUNANE, "punane", -ValguseLaius),
                tekitaTuli(KOLLANE, "kollane", 0),
                tekitaTuli(ROHELINE, "roheline", ValguseLaius));
    }

    private void setupStage() {
        Stage = new Stage();
        Stack = new StackPane();
        Scene = new Scene(Stack, StseeniSuurus, StseeniSuurus);
        Stage.setScene(Scene);
        Stage.show();
        Stage.setOnCloseRequest(event -> System.exit(0));
    }

    private void setupStage(Stage stage) {
        StackPane rootStack;

        Scene = stage.getScene();
        if (Scene != null) {
            rootStack = (StackPane) Scene.lookup("#root");
        }
        else {
            rootStack = new StackPane();
            rootStack.setId("root");
            Scene = new Scene(rootStack, StseeniSuurus, StseeniSuurus);
            stage.setScene(Scene);
            stage.show();
        }
        Stack = new StackPane();
        rootStack.getChildren().add(Stack);
        stage.setOnCloseRequest(event -> System.exit(0));
    }
}
