package org.example;
import java.util.*;

//dodac metody odpowiedzialne za kule ziemską! - DODANE
public class AnimalMap implements IWorldMap, IPositionChangeObserver{
    final private int mapWidth;
    final private int mapHeight;
    final private int startAmountOfPlants;
    final private int everyDayAmountOfPlants;
    final private int numberOfGenesPerAnimal;
    private int amountOfAnimals;
    private int amountOfPlants;
    final private int grassEnergy; //energia po zjedzenieu 1 rosliny
    final private int initialAnimalEnergy; //startowa energia zwierzakow
    final private int energyNeededToMakeBabies; //energia niezbedna by stworzyc dzidziusia
    final private int energyUsedToCreateBabies; //energia zuzywana na stworzenie potomka
    protected ArrayList<Animal> animals;  //lista wszystkich zwierzakow
    protected ArrayList<Animal> deadAnimalsList; //lista umarlych zwierzakow
    //slownik zwierzakow uwzgledniajacy fakt, ze moze byc ich kilka na jednej pozycji
    protected Map<Vector2d, ArrayList<Animal>> positions;
    protected Map<Vector2d, Plant> plants; //slownik roslinek
    protected Map<Vector2d, Integer> deadAnimals;
    public AnimalMap(int mapWidth, int mapHeight, int startAmountOfPlants, int everyDayAmountOfPlants, int startAmountOfAnimals, int numberOfGenes, int grassEnergy, int initialAnimalEnergy, int energyNeededToMakeBabies, int energyUsedToCreateBabies){
        animals = new ArrayList<>();
        plants = new HashMap<>();
        positions = new HashMap<>();
        deadAnimalsList = new ArrayList<>();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.startAmountOfPlants = startAmountOfPlants; //poczatkowa liczba roslin na mapie
        this.amountOfPlants = startAmountOfPlants;
        this.everyDayAmountOfPlants = everyDayAmountOfPlants; //liczba roslin wyrastajaca kazdego dnia
        this.amountOfAnimals = startAmountOfAnimals; //liczba poczatkowa zwierzat
        this.numberOfGenesPerAnimal = numberOfGenes; //liczba genow kazdego zwierzaka
        this.grassEnergy = grassEnergy;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.energyNeededToMakeBabies = energyNeededToMakeBabies;
        this.energyUsedToCreateBabies = energyUsedToCreateBabies;
        this.deadAnimals = new HashMap<>();
    }
    public ArrayList<Animal> getAnimalsList(){
        return this.animals;
    }
    public Map<Vector2d, Plant> getPlantsHashMap(){
        return this.plants;
    }
    public Map<Vector2d, ArrayList<Animal>> getAnimalsHashMap(){
        return this.positions;
    }
    public int getEveryDayAmountOfPlants(){
        return this.everyDayAmountOfPlants;
    }
    public void setAmountOfPlants(int amountOfPlants){
        this.amountOfPlants = amountOfPlants;
    }
    public int getAmountOfPlants(){
        return this.amountOfPlants;
    }
    public int getInitialAnimalEnergy(){
        return this.initialAnimalEnergy;
    }
    public int getGrassEnergy(){
        return this.grassEnergy;
    }
    public int getMapWidth(){
        return this.mapWidth;
    }
    public int getNumberOfGenesPerAnimal(){
        return this.numberOfGenesPerAnimal;
    }
    public int getMapHeight(){
        return this.mapHeight;
    }
    public int getStartAmountOfPlants(){
        return this.startAmountOfPlants;
    }
    public int getAmountOfAnimals(){
        return this.amountOfAnimals;
    }
    public void setAmountOfAnimals(int newAmountOfAnimals){
        this.amountOfAnimals = newAmountOfAnimals;
    }
    public int getEnergyNeededToMakeBabies(){
        return this.energyNeededToMakeBabies;
    }
    public int getEnergyUsedToCreateBabies(){
        return this.energyUsedToCreateBabies;
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if(position.x >= 0 && position.x < mapWidth && position.y >= 0 && position.y < mapHeight){
            return true; //moze byc kilka roznych zwierzat na jednej pozycji!
        }
        return false;
    }
    public Vector2d switchToOtherSide(Vector2d prevPosition, Animal animal){
        Vector2d newPosition;
        int newX = prevPosition.x;
        int newY = prevPosition.y;
        if (prevPosition.x >= getMapWidth()){
            newX = 0;
        }
        else if (prevPosition.x<0){
            newX = getMapWidth()-1;
        }
        if (prevPosition.y >= getMapHeight() || prevPosition.y<0){
            if (prevPosition.y >= getMapHeight()){
                newY  = getMapHeight()-1;
            }
            else if (prevPosition.y<0){
                newY = 0;}
            int oldGene = animal.getAnimalOrientation().getGene();
            int newGene;
            if (oldGene<4){
                newGene = oldGene+4;
            }
            else {
                newGene = oldGene-4;
            }
            animal.setAnimalOrientation(new Gene(newGene));
        }
        newPosition = new Vector2d(newX, newY);
        return newPosition;
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getAnimalPosition())){
            if(positions.get(animal.getAnimalPosition()) == null) {
                addToList(animal.getAnimalPosition(), animal);
                animals.add(animal);
                return true;
            }
            else if(!positions.get(animal.getAnimalPosition()).contains(animal)) {
                addToList(animal.getAnimalPosition(), animal);
                animals.add(animal);
                return true;
            }
        }
        return false;
    }
    @Override
    public void addToList(Vector2d position, Animal animal) {
        ArrayList<Animal> animalList = positions.get(position);
        // if list does not exist create it
        if(animalList == null) {
            animalList = new ArrayList<Animal>();
            animalList.add(animal);
            positions.put(position, animalList);
        } else {
            // add if item is not already in list
            if(!animalList.contains(animal)) animalList.add(animal);
        }
    }
    @Override
    public void removeFromList(Vector2d position, Animal animal) {
        ArrayList<Animal> animalList = positions.get(position);
        // if list does not exist create it
        if(animalList.contains(animal)){
            animalList.remove(animal);
        }
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return positions.containsKey(position);
    }
    @Override
    public ArrayList<Animal> objectsAt(Vector2d position) {
        if(isOccupied(position)){
            return (positions.get(position));
        }
        return null; //uwaga na nulla!
    }
    //i tak przy ruchu zwierzaków trzeba iść po indeksach przy ruszaniu sie
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, int index){
        Animal animal1 = animals.get(index);
        removeFromList(oldPosition, animal1);
        //animal1.setAnimalPosition(newPosition);
        addToList(newPosition, animal1);
    }
    @Override
    public boolean canMoveToPlant(Vector2d position) {
        if(position.x >= 0 && position.x < mapWidth && position.y >= 0 && position.y < mapHeight){
            return !this.isOccupiedPlant(position);
        }
        return false;
    }
    @Override
    public boolean placePlant(Plant plant) {
        if(canMoveToPlant(plant.getPlantPosition())){
            plants.put(plant.getPlantPosition(), plant);
            return true;
        }
        return false;
    }
    @Override
    public void cleanUpDeadAnimals(ArrayList<Animal> animals){
        for(int i=0; i<animals.toArray().length; i++){
            Animal animal = animals.get(i);
            if(animal!=null && animal.getAnimalEnergy()==0){
                Vector2d position = animal.getAnimalPosition();
                deadAnimals.put(position,deadAnimals.get(position)+1);
                deadAnimalsList.add(animal);
                animals.remove(animal);
                removeFromList(position, animal);
//                System.out.println("tutaj animal dead");
//                System.out.println(animal.getAnimalPosition());
                --i; //sprawdzic czy zadziala
            }
        }
    }
    @Override
    public boolean isOccupiedPlant(Vector2d position) {
        return plants.containsKey(position);
    }
    @Override
    public Plant objectAtPlant(Vector2d position) {
        if(isOccupiedPlant(position)){
            return (plants.get(position));
        }
        return null; //uwaga na nulla!
    }
    public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), new Vector2d(this.getMapWidth()-1, this.getMapHeight()-1));}
}
