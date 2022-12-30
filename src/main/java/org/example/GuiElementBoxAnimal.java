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
        if(numberOfAnimalsInTheBox>0 && numberOfAnimalsInTheBox<3){
            image = new Image(new FileInputStream("src/main/resources/dot0.png"));
        }
        else if(numberOfAnimalsInTheBox>=3 && numberOfAnimalsInTheBox<8){
            image = new Image(new FileInputStream("src/main/resources/dot1.png"));
        }
        else if(numberOfAnimalsInTheBox>=8 && numberOfAnimalsInTheBox<12){
            image = new Image(new FileInputStream("src/main/resources/dot2.png"));
        }
        else if(numberOfAnimalsInTheBox>=12 && numberOfAnimalsInTheBox<20){
            image = new Image(new FileInputStream("src/main/resources/dot3.png"));
        }
        else if(numberOfAnimalsInTheBox>0){
            image = new Image(new FileInputStream("src/main/resources/dot4.png"));
        }
        return image;
    }
}

