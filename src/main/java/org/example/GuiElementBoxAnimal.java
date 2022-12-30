package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static java.lang.Math.max;

public class GuiElementBoxAnimal extends AbstractGuiElementBox {
    Image image;
    public GuiElementBoxAnimal(IMapElementTemp mapElement, ArrayList<Animal> animalList, AnimalMap map) throws FileNotFoundException {
        super(mapElement, animalList, map);
    }
    public Image initializePictures(int numberOfAnimalsInTheBox) throws FileNotFoundException {
        if(numberOfAnimalsInTheBox>0 && numberOfAnimalsInTheBox<5){
            image = new Image(new FileInputStream("src/main/resources/dot0.png"));
        }
        else if(numberOfAnimalsInTheBox>=5 && numberOfAnimalsInTheBox<25){
            image = new Image(new FileInputStream("src/main/resources/dot.png"));
        }
        else if(numberOfAnimalsInTheBox>0){
            image = new Image(new FileInputStream("src/main/resources/dot2.png"));
        }
        return image;
    }
}

