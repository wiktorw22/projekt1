package org.example;

import java.util.ArrayList;
import java.util.TreeSet;

public class Parents {
    //klasa odpowiedzialna za utworzenie posortowanego treeset dla danego pola mapy
    //ktory bedzie niezbedny przy rozmnazaniu sie zwierzat i zjadaniu roslinek
    protected TreeSet<Animal> animalsInTheArea;
    private Vector2d area;
    protected ArrayList<Animal> twoAnimalsList;
    private AnimalMap map;
    public Parents(ArrayList<Animal> animalList, Vector2d area, AnimalMap map){
        this.animalsInTheArea = new TreeSet<>(animalList);
        this.area = area;
        this.twoAnimalsList = new ArrayList<>();
        this.map = map;
    }
    //tutaj nalezy sprawdzic czy nasze dwa zwierzeta pierwsze w treeset posiadaja wlasciwa ilosc energii do rozmnozenia sie
    //jesli tak nie jest to nie nastapi zadne rozmnozenie na tym polu
    public boolean ifCanMultiplicate(){
        Object[] animalsPom = animalsInTheArea.toArray();
        int n = animalsPom.length;
        if(n<2){
            return false;
        }
        Animal animalTmp1 = (Animal) animalsPom[n-1];
        twoAnimalsList.add(animalTmp1);
        Animal animalTmp2 = (Animal) animalsPom[n-2];
        twoAnimalsList.add(animalTmp2);
        return animalTmp1.getAnimalEnergy() >= this.map.getEnergyNeededToMakeBabies() && animalTmp2.getAnimalEnergy() >= this.map.getEnergyNeededToMakeBabies();
    }

    public Vector2d getArea(){
        return this.area;
    }

}
