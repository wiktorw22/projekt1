package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.file.StandardOpenOption.WRITE;

public class StatisticsToFile {

    Statistics statistics;
    public StatisticsToFile(Statistics statistics){
        this.statistics = statistics;
    }
    // metoda tworzaca tablice 'data'
    public void makeData(String fileName) throws FileNotFoundException {
        // nazwa pliku do którego zapiszemy dane
        //String fileName = "statystyki.csv";
        // tablica z danymi - 3 serie w rzędach po 5 liczb (np. pomiarów) - ZMIENIC NA NASZE STATY
        String[][] data = {{" Number of actual day ", Integer.toString(this.statistics.engine.getSimulationDay())},
                {" Actual amount of all animals on the map ", Integer.toString(this.statistics.map.getAmountOfAnimals())},
                {" Actual amount of all plants on the map ", Integer.toString(this.statistics.map.plants.keySet().toArray().length)},
                {" Actual number of free fields on the map ", Integer.toString(this.statistics.freeFields())},
                {" Average energy level of alive animals ", Double.toString(this.statistics.avgEnergyLevel())},
                {" Average age level of dead animals ", Double.toString(this.statistics.avgAgeLevel())}
        };

        // zapis pliku
        writeFile(fileName, data);

    }

    // metoda ta zapisuje dane w pliku tekstowym
    public static void writeFile(String fileName, String[][] data) throws FileNotFoundException {

        // tworzymy obiekt typu Path

        Path path = Paths.get(fileName);
        ArrayList<String> out = new ArrayList<>();

        // pobranie kolejnego "rzędu" danych
        for (String[] series : data) {
            String s = Arrays.toString(series);
            // Arrays.toString(seria) zwraca dane w postaci:
            // [2.3, 2.2, 2.1, 2.0, 1.8]
            // trzeba usunąć znaki [ i ]
            s = s.replace("[", "");
            s = s.replace("]", "");
            // dodanie linijki z danymi do listy
            out.add(s);
        }
        try {

            Files.write(path, out, WRITE);
        } catch (IOException ex) {
            System.out.println(" Nie mogę zapisać pliku! ");
        }

    }

}
