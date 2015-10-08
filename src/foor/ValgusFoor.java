package foor;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ValgusFoor {

    //// constant definitions

    public static final ValgusV2rv HALL     = ValgusV2rv.HALL;
    public static final ValgusV2rv PUNANE   = ValgusV2rv.PUNANE;
    public static final ValgusV2rv KOLLANE  = ValgusV2rv.KOLLANE;
    public static final ValgusV2rv ROHELINE = ValgusV2rv.ROHELINE;

    public static final FooriSuund YLEMINE = FooriSuund.YLEMINE;
    public static final FooriSuund ALUMINE = FooriSuund.ALUMINE;
    public static final FooriSuund VASAK   = FooriSuund.VASAK;
    public static final FooriSuund PAREM   = FooriSuund.PAREM;

    public enum FooriSuund { YLEMINE, ALUMINE, VASAK,   PAREM    }
    public enum ValgusV2rv { HALL,    PUNANE,  KOLLANE, ROHELINE }

    //// events and commands

    public enum FooriSyndmus {
        SYYTA    /* set light status on */,
        KUSTUTA  /* set light status off */,
        VAHETA   /* toggle light status (on/off)*/,
        PAUS     /* add animation delay on timeline */,
    }

    private class FooriK2sk {
        public FooriSyndmus Syndmus;
        public Object V22rtus;
        public double       Aeg;     // this is just for more timeline info, not actually required for anything
        public FooriK2sk(FooriSyndmus syndmus, Object v22rtus, double aeg) {
            Syndmus = syndmus;
            V22rtus = v22rtus;
            Aeg = aeg;
        }
    }

    //// private state variables

    private StackPane Stack;
    private Scene Scene;
    private Stage Stage;

    private int StseeniSuurus = 700;
    private int ValguseLaius  = StseeniSuurus / 4;
    private Color[]  V2rvid   = { Color.GRAY, Color.RED,    Color.YELLOW, Color.SPRINGGREEN };
    private Circle[] Tuled    = { null,       new Circle(), new Circle(), new Circle()      };
    private FooriSuund Suund  = YLEMINE;

    public ValgusFoor() {
        setupStage();
        drawLight(YLEMINE);
    }

    public ValgusFoor(FooriSuund fooriSuund) {
        setupStage();
        drawLight(fooriSuund);
    }

    public ValgusFoor(FooriSuund fooriSuund, Stage primaryStage) {
        setupStage(primaryStage);
        drawLight(fooriSuund);
    }

    //// animation related stuff

    private ArrayList<FooriK2sk> Syndmused = new ArrayList<FooriK2sk>();
    private double               AnimKestus = 0.0;
    private Thread               AnimThread = null;
    private volatile boolean     AnimJookseb = false;

    private void lisaK2sk(FooriSyndmus evt, Object value) { Syndmused.add(new FooriK2sk(evt, value, AnimKestus)); }

    public void syytaPunane()     { lisaK2sk(FooriSyndmus.SYYTA, ValgusFoor.PUNANE);    }
    public void syytaKollane()    { lisaK2sk(FooriSyndmus.SYYTA, ValgusFoor.KOLLANE);   }
    public void syytaRoheline()   { lisaK2sk(FooriSyndmus.SYYTA, ValgusFoor.ROHELINE);  }

    public void kustutaPunane()   { lisaK2sk(FooriSyndmus.KUSTUTA, ValgusFoor.PUNANE);  }
    public void kustutaKollane()  { lisaK2sk(FooriSyndmus.KUSTUTA, ValgusFoor.KOLLANE); }
    public void kustutaRoheline() { lisaK2sk(FooriSyndmus.KUSTUTA, ValgusFoor.ROHELINE);}

    public void vahetaPunast()    { lisaK2sk(FooriSyndmus.VAHETA, ValgusFoor.PUNANE);   }
    public void vahetaKollast()   { lisaK2sk(FooriSyndmus.VAHETA, ValgusFoor.KOLLANE);  }
    public void vahetaRohelist()  { lisaK2sk(FooriSyndmus.VAHETA, ValgusFoor.ROHELINE); }

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
        System.out.printf("%7s::%-8s %s\n", Suund, v2rv, onSees ? "on" : "off");
    }


    //// Animation playing and stopping

    public boolean animatsioonJookseb() {
        return AnimJookseb;
    }

    public void alustaAnimatsiooni() {
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
            }
        });
        AnimThread.start();
    }

    private void animatsiooniK2sk(FooriK2sk k2sk) {
        try {
            switch (k2sk.Syndmus) {
                case SYYTA:    muudaTuld((ValgusV2rv) k2sk.V22rtus, true);   break;
                case KUSTUTA:  muudaTuld((ValgusV2rv) k2sk.V22rtus, false);  break;
                case VAHETA:   vahetaTuld((ValgusV2rv) k2sk.V22rtus);        break;
                case PAUS:     magaKatkestusega((Double) k2sk.V22rtus);      break;
            }
        }
        catch (InterruptedException ex) {
            System.err.printf("alustaAnimatsiooni() %s(%s) katkestati\n", k2sk.Syndmus, k2sk.V22rtus);
        }
    }

    private void magaKatkestusega(double sekundeidMagamiseks) throws InterruptedException {
        int millisekundid = (int)(sekundeidMagamiseks * 1000);

        for (; millisekundid > 0 && AnimJookseb; millisekundid -= 15) {
            Thread.sleep(15); // sleep only 15s at a time, this allows to check if AnimPlaying
        }
    }

    public void peataAnimatsioon() {
        if (AnimJookseb) AnimJookseb = false;
        if (onTuliSees(ValgusV2rv.PUNANE))    muudaTuld(ValgusV2rv.PUNANE, false);
        if (onTuliSees(ValgusV2rv.KOLLANE))   muudaTuld(ValgusV2rv.KOLLANE, false);
        if (onTuliSees(ValgusV2rv.ROHELINE))  muudaTuld(ValgusV2rv.ROHELINE, false);
    }

    //// ValgusFoor graphics setup

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

        Suund = fooriSuund;
        int suund  = fooriSuund.ordinal();
        int orient = new int[]{ 180,     0,     90,     -90 }[suund];
        int x      = new int[]{ 0, 0, -lightSize, lightSize }[suund];
        int y      = new int[]{ -lightSize, lightSize, 0, 0 }[suund];

        Rectangle kast = new Rectangle();
        kast.setWidth(ValguseLaius);
        kast.setHeight(ValguseLaius * 3);
        kast.setFill(Color.DIMGRAY);

        Stack.getChildren().addAll(kast,
                tekitaTuli(PUNANE, "punane", -ValguseLaius),
                tekitaTuli(KOLLANE, "kollane", 0),
                tekitaTuli(ROHELINE, "roheline", ValguseLaius));

        Stack.setTranslateX(x);
        Stack.setTranslateY(y);
        Stack.setScaleX(skaala);
        Stack.setScaleY(skaala);
        Stack.setRotate(orient);
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
