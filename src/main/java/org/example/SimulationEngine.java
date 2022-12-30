package org.example;

import javafx.application.Platform;

import java.io.FileNotFoundException;
import java.util.*;

public class SimulationEngine implements IEngine, Runnable {
    private AnimalMap map;
    private AppTemp app;
    private int moveDelay = 300;
    private int day = 0; //trwajacy dzien symulacji
    public SimulationEngine(AnimalMap map, AppTemp app){
        this.map = map;
        this.app = app;
    }
    public int getSimulationDay(){
        return this.day;
    }
    public void setSimulationDay(int day){
        this.day = day;
    }
    public void makePlaceForDeadAnimals(){
        for (int x=0;x<map.getMapWidth();x++){
            for(int y=0;y<map.getMapHeight();y++){
                map.deadAnimals.put(new Vector2d(x,y),0);
            }
        }
    }
    public static <K, V extends Comparable<V>> Map<K, V> sortValues(final Map<K, V> map){
        Comparator<K> comparator = new Comparator<K>() {
            @Override
            public int compare(K o1, K o2) {
                int compare = map.get(o1).compareTo(map.get(o2));
                if(compare == 0){
                    return 1;
                }
                else{
                    return compare;
                }
            }
        };
        Map<K,V> sortedByValues = new TreeMap<K,V>(comparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
    public boolean ifCanGrowPlants(){ //czy wogole mozna zasadzic jakies roslinki na mapie w danym momencie
        for(int i=0; i<this.map.getMapWidth(); i++){
            for(int j=0; j<this.map.getMapHeight(); j++){
                if(this.map.canMoveToPlant(new Vector2d(i, j))){
                    return true;
                }
            }
        }
        return false;
    }
    public void placePlants(int amountOfPlants) { //metoda odpowiedzialna za umieszczanie roslin na mapie
        if(!ifCanGrowPlants()){
            return; //nie da rady nic juz zasadzic nowego (poki co)
        }
        Map<Vector2d, Integer> sortedDeadAnimals = sortValues(map.deadAnimals);
        Deque<Vector2d> stack = new ArrayDeque<Vector2d>();
        for (Map.Entry<Vector2d, Integer> set : sortedDeadAnimals.entrySet()) {
            stack.push(set.getKey());
        }
        int cnt = 0;
        while (cnt < amountOfPlants) {
            Platform.runLater(() -> {
                try {
                    this.app.renderMap(this.map);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            Random randomP = new Random();
            int probability = randomP.nextInt(100);
            if (probability >= 20 && !stack.isEmpty()) {
                Vector2d plantPosition = stack.pop();
                Plant newPlant = new Plant(plantPosition);
                if (this.map.placePlant(newPlant)) {
                    this.map.placePlant(newPlant);
                    cnt++;
                }
            } else {
                Random randomX = new Random();
                int startPositionX = randomX.nextInt(this.map.getMapWidth());
                Random randomY = new Random();
                int startPositionY = randomY.nextInt(this.map.getMapHeight());
                Vector2d plantPosition = new Vector2d(startPositionX, startPositionY);
                Plant newPlant = new Plant(plantPosition);
                if (this.map.placePlant(newPlant)) {
                    this.map.placePlant(newPlant);
                    cnt++;
                }
            }
        }
    }
    public void placeAnimals(){
        int cnt = 0;
        while(cnt < this.map.getAmountOfAnimals()){
            Gene[] genes = new Gene[this.map.getNumberOfGenesPerAnimal()];
            for(int i=0; i<this.map.getNumberOfGenesPerAnimal(); i++){
                Random randomGene = new Random();
                int gene = randomGene.nextInt(8);
                genes[i] = new Gene(gene);
            }
            Animal newAnimal = new Animal(this.map.getInitialAnimalEnergy(), this.map, genes);
            Random random1 = new Random();
            int startOrientation = random1.nextInt(8);
            Random random2 = new Random();
            int startPositionX = random2.nextInt(this.map.getMapWidth());
            Random random3 = new Random();
            int startPositionY = random3.nextInt(this.map.getMapHeight());
            Vector2d newPosition = new Vector2d(startPositionX, startPositionY);
            newAnimal.setAnimalVariables(newPosition, startOrientation);
            if (this.map.place(newAnimal)) {
                this.map.place(newAnimal);
                newAnimal.addObserver(this.map);
                cnt++;
            }
        }
    }
    //metoda poruszajaca zwierzakami w ciagu jednego dnia trwania symulacji
    public void moveOneDay(int day){
        int today = day;
        int j = day;
        Random randomP = new Random();
        int probability = randomP.nextInt(100);
        if(probability<=19){
            Random randomG = new Random();
            j = randomG.nextInt(this.map.getNumberOfGenesPerAnimal());
        }
        int pom;
        if(j >= this.map.getNumberOfGenesPerAnimal()){
            pom = j % this.map.getNumberOfGenesPerAnimal();
        }
        else{
            pom = j;
        }
        Platform.runLater(() -> {
            try {
                this.app.renderMap(this.map);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            Thread.sleep(this.moveDelay);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < this.map.animals.toArray().length; i++) {
            Animal movedAnimal = this.map.animals.get(i);
            if(movedAnimal != null){
                Vector2d animalPosition = movedAnimal.getAnimalPosition();
                Gene movingGene = new Gene((movedAnimal.getAnimalOrientation().getGene() + movedAnimal.getAnimalGenes()[pom].getGene()) % 8);
                movedAnimal.move(movingGene);
                Platform.runLater(() -> {
                    try {
                        this.app.renderMap(this.map);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                try {
                    Thread.sleep(this.moveDelay);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                movedAnimal.positionChanged(animalPosition, movedAnimal.getAnimalPosition(), i);
                movedAnimal.setAnimalAge(movedAnimal.getAnimalAge()+1);
                movedAnimal.setAnimalEnergy(movedAnimal.getAnimalEnergy()-1);
                if (map.isOccupiedPlant(movedAnimal.getAnimalPosition())){ //zjedzenie roslinki
                    int energyFromPlant = map.getGrassEnergy();
                    int oldEnergy = movedAnimal.getAnimalEnergy();
                    int newEnergy = oldEnergy+energyFromPlant;
                    movedAnimal.setAnimalEnergy(newEnergy);
                    map.plants.remove(movedAnimal.getAnimalPosition());
                    this.map.setAmountOfPlants(this.map.getAmountOfPlants()-1);
                }
            }
        }
        this.map.cleanUpDeadAnimals(this.map.animals);
        makePlaceForDeadAnimals();
        multiplicate();
        placePlants(this.map.getEveryDayAmountOfPlants()); //wyrastanie roslinek kazdego dnia symulacji
        this.map.setAmountOfPlants(this.map.getAmountOfPlants()+this.map.getEveryDayAmountOfPlants());
        if (today < 1000){
            moveOneDay(today+1);
        }
    }
    //metoda rozmnazajace zdolne do tego zwierzaki
    public void multiplicate(){
        for(int i = 0; i < this.map.animals.toArray().length; i++) {
            if(this.map.animals.get(i) != null && this.map.positions.get(this.map.animals.get(i).getAnimalPosition()) != null){
                if(this.map.positions.get(this.map.animals.get(i).getAnimalPosition()).toArray().length >= 2){
                    Parents parents = new Parents(this.map.positions.get(this.map.animals.get(i).getAnimalPosition()), this.map.animals.get(i).getAnimalPosition(), this.map);
                    if(parents.ifCanMultiplicate()){
                        Multiplication multiplication = new Multiplication(parents.twoAnimalsList.get(0), parents.twoAnimalsList.get(1), this.map);
                        Animal newAnimal = multiplication.makeNewAnimal();
                        if(this.map.place(newAnimal)){
                            this.map.place(newAnimal);
                            newAnimal.addObserver(this.map);
                            this.map.setAmountOfAnimals(this.map.getAmountOfAnimals()+1);
                        }
                    }
                }
            }
        }
    }
    //glowna metoda sterujaca zwierzakami na mapie
    @Override
    public void run(){
        placeAnimals();
        placePlants(map.getStartAmountOfPlants());
        moveOneDay(1);

    }
}