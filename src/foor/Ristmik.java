package foor;

import javafx.stage.Stage;

public class Ristmik extends YksikFoor {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ValgusFoor foor1 = new ValgusFoor(ValgusFoor.YLEMINE, primaryStage);
        ValgusFoor foor2 = new ValgusFoor(ValgusFoor.ALUMINE, primaryStage);
        ValgusFoor foor3 = new ValgusFoor(ValgusFoor.PAREM, primaryStage);
        ValgusFoor foor4 = new ValgusFoor(ValgusFoor.VASAK, primaryStage);

        gruppVertikaalne(foor1);
        gruppVertikaalne(foor2);
        gruppHorisontaalne(foor3);
        gruppHorisontaalne(foor4);
    }

    private void gruppVertikaalne(ValgusFoor foor) {
        rohelineKestab(foor);
        rohlinePunaseks(foor);

        punaneKestab(foor);
        punaneRoheliseks(foor);
    }
    private void gruppHorisontaalne(ValgusFoor foor) {
        punaneKestab(foor);
        punaneRoheliseks(foor);

        rohelineKestab(foor);
        rohlinePunaseks(foor);
    }
}
