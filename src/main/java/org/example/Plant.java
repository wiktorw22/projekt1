package org.example;

public class Plant implements IMapElementTemp {
    private Vector2d position;
    public Plant(Vector2d position){
        this.position = position;
    }
    public Vector2d getPlantPosition(){
        return this.position;
    }

    public String toString(){
        return "*";
    }
    public String getImageFile(){
        return "src/main/resources/plant.jpg";
    };

}
