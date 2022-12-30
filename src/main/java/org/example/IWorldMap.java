package org.example;

import java.util.ArrayList;

public interface IWorldMap {
    void addToList(Vector2d position, Animal animal);
    void removeFromList(Vector2d position, Animal animal);
    void cleanUpDeadAnimals(ArrayList<Animal> animals);
    boolean canMoveTo(Vector2d position);
    boolean place(Animal animal);
    boolean isOccupied(Vector2d position);
    ArrayList<Animal> objectsAt(Vector2d position);
    //metoda odpowiedzialna za 'kule ziemska'
    Vector2d switchToOtherSide(Vector2d prevPosition, Animal animal);
    boolean canMoveToPlant(Vector2d position);
    boolean placePlant(Plant plant);
    boolean isOccupiedPlant(Vector2d position);
    Plant objectAtPlant(Vector2d position);
}
