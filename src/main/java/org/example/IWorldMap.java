package org.example;

import java.util.ArrayList;

public interface IWorldMap {
    public void addToList(Vector2d position, Animal animal);
    public void removeFromList(Vector2d position, Animal animal);
    public void cleanUpDeadAnimals(ArrayList<Animal> animals);
    public boolean canMoveTo(Vector2d position);
    public boolean place(Animal animal);
    public boolean isOccupied(Vector2d position);
    ArrayList<Animal> objectsAt(Vector2d position);
    //metoda odpowiedzialna za 'kule ziemska'
    public Vector2d switchToOtherSide(Vector2d prevPosition, Animal animal);
    public boolean canMoveToPlant(Vector2d position);
    public boolean placePlant(Plant plant);
    public boolean isOccupiedPlant(Vector2d position);
    Plant objectAtPlant(Vector2d position);
}
