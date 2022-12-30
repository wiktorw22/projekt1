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

        String[][] data = {{" Number of actual day ", Integer.toString(this.statistics.engine.getSimulationDay())},
                {" Actual amount of all animals on the map ", Integer.toString(this.statistics.map.getAmountOfAnimals())},
                {" Actual amount of all plants on the map ", Integer.toString(this.statistics.map.plants.keySet().toArray().length)},
                {" Actual number of free fields on the map ", Integer.toString(this.statistics.freeFields())},
                {" Average energy level of alive animals ", Double.toString(this.statistics.avgEnergyLevel())},
                {" Average age level of dead animals ", Double.toString(this.statistics.avgAgeLevel())}
        };

        // zapis do pliku
        writeFile(fileName, data);

    }

    // metoda ta zapisuje dane w pliku csv
    public static void writeFile(String fileName, String[][] data) throws FileNotFoundException {

        // tworzymy obiekt typu Path
        Path path = Paths.get(fileName);
        ArrayList<String> out = new ArrayList<>();

        // pobranie kolejnego rzędu danych
        for (String[] series : data) {
            String s = Arrays.toString(series);
            // Arrays.toString(seria) zwraca dane w postaci tablicy
            // trzeba usunąć znaki [ i ]
            s = s.replace("[", "");
            s = s.replace("]", "");
            // dodanie linijki z danymi do listy
            out.add(s);
        }
        try {
            Files.write(path, out);
        } catch (IOException ex) {
            System.out.println(" Cannot write data to file! ");
        }

    }

}
