package org.example;

import java.util.*;

public class Animal implements Comparable<Animal>{
    private int energy; //aktualna energia zwierzaka
    private int age = 0; //z kazdym dniem symulacji wiek zwierzecia powinien wzrastac o 1
    private int numberOfDescendants = 0; //z kazdym rozmnozeniem liczbe potomkow nalezy zwiekszyc o 1
    private Vector2d position;
    private Gene orientation;
    private AnimalMap map;
    private Gene[] genes;
    List<IPositionChangeObserver> observers = new ArrayList<>(); //lista obserwatorow
    public Animal(int energy, AnimalMap map, Gene[] genes){
        this.energy = energy;
        this.map = map;
        this.genes = genes;
        //zarowno poczatkowa pozycja jak i orientacja sa losowe
    }
    @Override
    public int compareTo(Animal other) {
        int energyDifference = this.energy - other.energy;
        if (energyDifference != 0) {
            return energyDifference;
        }
        else {
            int ageDifference = this.age - other.age;
            if (ageDifference != 0) {
                return ageDifference;
            }
            else {
                return this.numberOfDescendants - other.numberOfDescendants;
            }
        }
    }
    public int getAnimalAge(){
        return this.age;
    }
    public int getAnimalNumberOfDescendants(){
        return this.numberOfDescendants;
    }
    public void setAnimalAge(int newAge){
        this.age = newAge;
    }
    public void setAnimalNumberOfDescendants(int numberOfDescendants){
        this.numberOfDescendants = numberOfDescendants;
    }
    public Vector2d getAnimalPosition(){
        return this.position;
    }
    public Gene getAnimalOrientation(){
        return this.orientation;
    }
    public void setAnimalPosition(Vector2d newPosition){
        this.position = newPosition;
    }
    public int getAnimalEnergy(){
        return this.energy;
    }
    public void setAnimalEnergy(int newEnergy){
        this.energy = newEnergy;
    }
    public void setAnimalOrientation(Gene newOrientation){
        this.orientation = newOrientation;
    }
    public Gene[] getAnimalGenes(){
        return this.genes;
    }
    public void setAnimalGenes(Gene[] newGenes){
        this.genes = newGenes;
    }
    public void setAnimalVariables(Vector2d newPosition, int startOrientation){
        setAnimalPosition(newPosition);
        setAnimalOrientation(new Gene(startOrientation));
    }
    public void move(Gene orientation) {
        Vector2d newPosition = this.position.add(orientation.geneToVector2d());
        if(this.map.canMoveTo(newPosition)){
            this.position = newPosition;
        }
        else{
            this.position = this.map.switchToOtherSide(newPosition,this);
        }
    }
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, int index){
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition, index);
        }
    }
}
