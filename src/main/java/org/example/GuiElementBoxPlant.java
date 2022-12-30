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

public class GuiElementBoxPlant extends AbstractGuiElementBox {
    Image image;
    ImageView imageView;
    public GuiElementBoxPlant(IMapElementTemp mapElement, ArrayList<Animal> animalList, AnimalMap map) throws FileNotFoundException {
        super(mapElement, animalList, map);
    }
    @Override
    public Image initializePictures(int numberOfAnimalsInTheBox) throws FileNotFoundException {
        return new Image(new FileInputStream("src/main/resources/plant.png"));
    }

}

