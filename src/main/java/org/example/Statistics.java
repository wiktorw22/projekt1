package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Statistics extends Application {
    AnimalMap map;
    Stage window;
    AppTemp app;
    SimulationEngine engine;
    public Statistics(AnimalMap map, AppTemp app, SimulationEngine engine){
        this.map = map;
        this.app = app;
        this.engine = engine;
    }
    public void start(Stage primaryStage){
        window = primaryStage;
        window.setTitle(" statistics ");
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(8);
        grid.setHgap(10);

        //aktualna ilosc roslinek
        Label numberOfPlants = new Label(" Actual number of plants: ");
        GridPane.setConstraints(numberOfPlants,0,1,1,1);

        Label numberOfPlantsRes = new Label(Integer.toString(this.map.plants.keySet().toArray().length));
        GridPane.setConstraints(numberOfPlantsRes,1,1,1,1);

        //aktualna ilosc zwierzakow
        Label numberOfAnimals = new Label(" Actual number of animals: ");
        GridPane.setConstraints(numberOfAnimals,0,2,1,1);

        Label numberOfAnimalsRes = new Label(Integer.toString(this.map.animals.toArray().length));
        GridPane.setConstraints(numberOfAnimalsRes,1,2,1,1);

        //aktualna liczba wolnych pol
        Label freeFields = new Label(" Actual number of free fields: ");
        GridPane.setConstraints(freeFields,0,3,1,1);

        Label numberOfFreeFieldsRes = new Label(Integer.toString(this.freeFields()));
        GridPane.setConstraints(numberOfFreeFieldsRes,1,3,1,1);

        //aktualny sredni poziom energii zwierzat zyjacych
        Label energyLevel = new Label(" Actual number of average energy level: ");
        GridPane.setConstraints(energyLevel,0,4,1,1);

        Label numberOfEnergyLevelRes = new Label(Double.toString(this.avgEnergyLevel()));
        GridPane.setConstraints(numberOfEnergyLevelRes,1,4,1,1);

        //aktualny sredni poziom wieku zwierzat umarlych
        Label ageLevel = new Label(" Actual number of average age: ");
        GridPane.setConstraints(ageLevel,0,5,1,1);

        Label numberOfAgeLevelRes = new Label(Double.toString(this.avgAgeLevel()));
        GridPane.setConstraints(numberOfAgeLevelRes,1,5,1,1);

        grid.getChildren().addAll(numberOfPlants, numberOfPlantsRes, numberOfAnimals, numberOfAnimalsRes,
                freeFields, numberOfFreeFieldsRes, energyLevel, numberOfEnergyLevelRes, ageLevel, numberOfAgeLevelRes);
        Scene scene = new Scene(grid,400,375);
        window.setScene(scene);
        window.show();

    }
    //metoda zwracajaca liczbe wolnych pol na mapie
    public int freeFields(){
        int res = this.map.getMapHeight()*this.map.getMapWidth()-(this.map.animals.toArray().length+this.map.plants.keySet().toArray().length);
        return res;
    }
    //metoda podajaca sredni poziom energii sposrod aktywnych zwierzat
    public double avgEnergyLevel(){
        int energySum = 0;
        int numberOfAnimals = 0;
        for(int i=0; i<this.map.animals.toArray().length; i++){
            if(this.map.animals.get(i) != null){
                energySum = energySum + this.map.animals.get(i).getAnimalEnergy();
                numberOfAnimals++;
            }
        }
        return (double)energySum/(double)numberOfAnimals;
    }
    //metoda podajaca sredni wiek zycia umarlych juz zwierzat
    public double avgAgeLevel(){
        int ageSum = 0;
        int numberOfAnimals = 0;
        for(int i=0; i<this.map.animals.toArray().length; i++){
            if(this.map.animals.get(i) != null && this.map.deadAnimalsList.contains(this.map.animals.get(i))){
                ageSum = ageSum + this.map.animals.get(i).getAnimalAge();
                numberOfAnimals++;
            }
        }
        return (double)ageSum/(double)numberOfAnimals;
    }

}
