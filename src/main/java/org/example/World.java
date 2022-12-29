package org.example;

// 1 2 1 2
// wzorzec solid
//30 grudnia
//metoda szablonowa
//klasy + własności
//metody z labów
//strona Polaka https://www.icsr.agh.edu.pl/~polak/jezyki/java/
//random na pozycje trawy z prawdopodobieństwem

import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        System.out.println("Welcome to our game!");
        // hej

//        AnimalMap map = new AnimalMap(10, 14, 30, 10, 80, 2);
//        AppTemp app = new AppTemp();
//        SimulationEngine engine = new SimulationEngine(map, app); //, app
//        engine.run();
        //System.out.println(map.toString());
        Application.launch(Settings.class, args);
        //Application.launch(AppTemp.class, args);

    }
}
