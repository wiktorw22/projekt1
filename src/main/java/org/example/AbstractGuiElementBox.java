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

abstract class AbstractGuiElementBox {
    Image image;
    ImageView imageView;
    public AbstractGuiElementBox(IMapElementTemp mapElement, ArrayList<Animal> animalList, AnimalMap map) throws FileNotFoundException {

        int numberOfAnimalsInTheBox = 0;
        if(animalList!=null){
            numberOfAnimalsInTheBox = animalList.toArray().length;
        }
        image = initializePictures(numberOfAnimalsInTheBox);
        imageView = new ImageView(image);

        imageView.setFitWidth((double)500/max(map.getMapWidth(),map.getMapHeight()));
        imageView.setFitHeight((double)500/max(map.getMapWidth(),map.getMapHeight()));

    }

    abstract Image initializePictures(int numberOfAnimalsInTheBox) throws FileNotFoundException;

    public VBox toBox() {

        VBox box = new VBox(imageView);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}




