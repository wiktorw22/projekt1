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

public class GuiBoxElementAnimalPlant extends AbstractGuiElementBox {
    Image image;
    public GuiBoxElementAnimalPlant(IMapElementTemp mapElement, ArrayList<Animal> animalList, AnimalMap map) throws FileNotFoundException {
        super(mapElement, animalList, map);
    }
    public Image initializePictures(int numberOfAnimalsInTheBox) throws FileNotFoundException {
        if(numberOfAnimalsInTheBox>0 && numberOfAnimalsInTheBox<3){
            image = new Image(new FileInputStream("src/main/resources/both0.png"));
        }
        else if(numberOfAnimalsInTheBox>=3 && numberOfAnimalsInTheBox<8){
            image = new Image(new FileInputStream("src/main/resources/both1.png"));
        }
        else if(numberOfAnimalsInTheBox>=8 && numberOfAnimalsInTheBox<12){
            image = new Image(new FileInputStream("src/main/resources/both2.png"));
        }
        else if(numberOfAnimalsInTheBox>=12 && numberOfAnimalsInTheBox<20){
            image = new Image(new FileInputStream("src/main/resources/both3.png"));
        }
        else if(numberOfAnimalsInTheBox>0){
            image = new Image(new FileInputStream("src/main/resources/both4.png"));
        }
        return image;
    }

}



