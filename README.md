# javaHarjutused

Siia jõuavad Eesti Infotehnoloogia Kolledži Java algkursuse harjutusülesanded.

## Foor

Laiendades `Application` klassi ja luues `Foor` objekti saad omale tühja valgusfoori:

```
public class Ristmik extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Foor foor = new Foor();
    }
}
```

![Foor1](/readme/Foor.png?raw=true)

Programmeerida saab järgmiste vahenditega:
```
foor.vahetaPunast();   // pane punane   tuli põlema, või kui juba põleb kustuta ära.
foor.vahetaKollast();  // pane kollane  tuli põlema, või kui juba põleb kustuta ära.
foor.vahetaRohelist(); // pane roheline tuli põlema, või kui juba põleb kustuta ära.
foor.paus(sek);  // jäta mis iganes värv praegu ees on põlema 'sek' sekundiks.
```

Näiteks paneb järgnev kood rohelise tule põlema ja kustutab viie sekundi pärast ära ka.
```
foor.vahetaRohelist();
foor.paus(5);
foor.vahetaRohelist();
``` 

Foori luues saab talle anda kaasa kaks parameetrit:
```
new Foor(suund, primaryStage);
```

**suund** on üks neljast: Foor.YLEMINE
                          Foor.ALUMINE
                          Foor.VASAK
                          Foor.PAREM
**primaryStage** on see samune, mis start() meetodiga kaasa tuleb. Selle lisamine teiseks parameetriks on vajalik selleks, et kõik foorid ühte aknasse tuua.

Näide, kuidas kaks foori ühe ja sama akna sisse saab:
```
new Foor(Foor.YLEMINE, primaryStage);
new Foor(Foor.ALUMINE, primaryStage);
```

### Harjutus 1

Pane kollane tuli vilkuma

![Foor1](/readme/FoorKollane.gif?raw=true)

### Harjutus 2

Programmeeri terve foori tsükkel

![Foor1](/readme/FoorTsykkel.gif?raw=true)

### Harjutus 3

Programmeeri terve ristmik.

![Foor1](/readme/FoorRistmik.gif?raw=true)
