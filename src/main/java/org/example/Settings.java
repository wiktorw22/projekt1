package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Settings extends Application {
    Stage window;
    AnimalMap map;
    SimulationEngine engine;
    AppTemp app;
    public void setError(TextField value, String info, Stage primaryStage){

            window.close();
            window = primaryStage;
            window.setTitle(" errors ");
            GridPane newGrid = new GridPane();

            newGrid.setPadding(new Insets(10,10,10,10));
            newGrid.setVgap(8);
            newGrid.setHgap(10);

            Label error = new Label(info);
            GridPane.setConstraints(error,0,0);
            newGrid.getChildren().addAll(error);
            Scene newScene = new Scene(newGrid,400,50);
            window.setScene(newScene);
            window.show();

            throw new IllegalArgumentException(info);

    }
    @Override
    public void start(Stage primaryStage) throws IllegalArgumentException {
        //bedziemy rzucac wyjatki jak uzytkownik przekaze nam zle parametry do symulacji (ujemne)
        window = primaryStage;
        window.setTitle("settings");
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        // wysokość mapy
        Label mapHeight = new Label(" Map height ");
        GridPane.setConstraints(mapHeight,0,0);
        TextField defaultMapHeight = new TextField("20");
        GridPane.setConstraints(defaultMapHeight,1,0);

        //szerokość mapy
        Label mapWidth = new Label(" Map width ");
        GridPane.setConstraints(mapWidth,0,1);
        TextField defaultMapWidth = new TextField("20");
        GridPane.setConstraints(defaultMapWidth,1,1);

        //startowa liczba zwierzaków
        Label initalNumberOfAnimals = new Label(" Initial number of animals ");
        GridPane.setConstraints(initalNumberOfAnimals,0,2);
        TextField defaultNumOfAnimals = new TextField("10");
        GridPane.setConstraints(defaultNumOfAnimals,1,2);

        //startowa liczba traw
        Label initialNumberOfGrass = new Label(" Initial number of plants ");
        GridPane.setConstraints(initialNumberOfGrass,0,3);
        TextField defaultNumberOfGrass = new TextField("20");
        GridPane.setConstraints(defaultNumberOfGrass,1,3);

        //energia zapewniana przez zjedzenie jednej rośliny
        Label grassEnergy = new Label(" Plant energy ");
        GridPane.setConstraints(grassEnergy,0,4);
        TextField defaultGrassEnergy = new TextField("5");
        GridPane.setConstraints(defaultGrassEnergy,1,4);

        //liczba roślin wyrastająca każdego dnia
        Label numberOfGrassGrowingEveryday = new Label(" Number of plants growing every day ");
        GridPane.setConstraints(numberOfGrassGrowingEveryday,0,5);
        TextField defaultGrassNumberGrowingEveryday = new TextField("5");
        GridPane.setConstraints(defaultGrassNumberGrowingEveryday,1,5);

        //startowa energia zwierzaków
        Label initialAnimalEnergy = new Label(" Initial animal energy ");
        GridPane.setConstraints(initialAnimalEnergy,0,6);
        TextField defaultAnimalEnergy = new TextField("7");
        GridPane.setConstraints(defaultAnimalEnergy,1,6);

        //minimalna energia do stworzenia potomka
        Label energyNeededToMakeBabies = new Label(" Energy needed to make babies ");
        GridPane.setConstraints(energyNeededToMakeBabies,0,7);
        TextField defaultEnergyToMakeBabies = new TextField("10");
        GridPane.setConstraints(defaultEnergyToMakeBabies,1,7);

        //energia zuzywana do stworzenia potomka
        Label energyUsedToCreateBabies = new Label(" Energy used to create babies ");
        GridPane.setConstraints(energyUsedToCreateBabies,0,8);
        TextField defaultEnergyUsedToCreateBabies = new TextField("7");
        GridPane.setConstraints(defaultEnergyUsedToCreateBabies,1, 8);

        //dlugosc genomu zwierzakow
        Label lengthOfAnimalGenome = new Label(" Length of animal genome ");
        GridPane.setConstraints(lengthOfAnimalGenome,0,9);
        TextField defaultLengthOfAnimalGenome = new TextField("4");
        GridPane.setConstraints(defaultLengthOfAnimalGenome,1, 9);

        Button startButton = new Button(" start simulation ");
        GridPane.setConstraints(startButton,1,10);
        grid.getChildren().addAll(mapHeight, defaultMapHeight, mapWidth, defaultMapWidth, initalNumberOfAnimals,
                defaultNumOfAnimals, initialNumberOfGrass, defaultNumberOfGrass, grassEnergy, defaultGrassEnergy,
                numberOfGrassGrowingEveryday, defaultGrassNumberGrowingEveryday, initialAnimalEnergy,
                defaultAnimalEnergy, energyNeededToMakeBabies, defaultEnergyToMakeBabies,
                energyUsedToCreateBabies, defaultEnergyUsedToCreateBabies, lengthOfAnimalGenome,
                defaultLengthOfAnimalGenome, startButton);
        Scene scene = new Scene(grid,400,375);
        window.setScene(scene);
        window.show();
        startButton.setOnAction(action -> {
            if (Integer.parseInt(defaultMapWidth.getText())<0) {

                setError(defaultMapWidth, " Wrong map width! ", primaryStage);

            }
            if (Integer.parseInt(defaultMapHeight.getText())<0) {

                setError(defaultMapHeight, " Wrong map height! ", primaryStage);

            }
            if (Integer.parseInt(defaultNumberOfGrass.getText())<0) {

                setError(defaultNumberOfGrass, " Wrong plants number! ", primaryStage);

            }
            if (Integer.parseInt(defaultGrassNumberGrowingEveryday.getText())<0) {

                setError(defaultGrassNumberGrowingEveryday, " Wrong plants number growing every day! ", primaryStage);

            }
            if (Integer.parseInt(defaultNumOfAnimals.getText())<0) {

                setError(defaultNumOfAnimals, " Wrong number of animals! ", primaryStage);

            }
            if (Integer.parseInt(defaultLengthOfAnimalGenome.getText())<0) {

                setError(defaultLengthOfAnimalGenome, " Wrong length of animal genome! ", primaryStage);

            }
            if (Integer.parseInt(defaultGrassEnergy.getText())<0) {

                setError(defaultGrassEnergy, " Wrong plant energy! ", primaryStage);

            }
            if (Integer.parseInt(defaultAnimalEnergy.getText())<0) {

                setError(defaultAnimalEnergy, " Wrong animal energy! ", primaryStage);

            }
            if (Integer.parseInt(defaultEnergyToMakeBabies.getText())<0) {

                setError(defaultEnergyToMakeBabies, " Wrong energy to make babies! ", primaryStage);

            }
            if (Integer.parseInt(defaultEnergyUsedToCreateBabies.getText())<0) {

                setError(defaultEnergyUsedToCreateBabies, " Wrong energy used to create babies! ", primaryStage);

            }
            this.map = new AnimalMap(Integer.parseInt(defaultMapWidth.getText()),
                    Integer.parseInt(defaultMapHeight.getText()),
                    Integer.parseInt(defaultNumberOfGrass.getText()),
                    Integer.parseInt(defaultGrassNumberGrowingEveryday.getText()),
                    Integer.parseInt(defaultNumOfAnimals.getText()),
                    Integer.parseInt(defaultLengthOfAnimalGenome.getText()),
                    Integer.parseInt(defaultGrassEnergy.getText()),
                    Integer.parseInt(defaultAnimalEnergy.getText()),
                    Integer.parseInt(defaultEnergyToMakeBabies.getText()),
                    Integer.parseInt(defaultEnergyUsedToCreateBabies.getText()));
            this.app = new AppTemp();
            this.engine = new SimulationEngine(map, app);
            try {
                app.setSettingsToApp(map, engine);
                app.init();
                app.start(primaryStage);
                primaryStage.show();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
