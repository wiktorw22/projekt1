package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.Math.max;

public class GuiElementBoxPlant {
    Image image;
    ImageView imageView;
    public GuiElementBoxPlant(IMapElementTemp mapElement,AnimalMap map) throws FileNotFoundException {

        image = new Image(new FileInputStream(mapElement.getImageFile()));

        imageView = new ImageView(image);

        imageView.setFitWidth((double)500/max(map.getMapWidth(),map.getMapHeight()));
        imageView.setFitHeight((double)500/max(map.getMapWidth(),map.getMapHeight()));

    }

    public VBox toBox() {

        VBox box = new VBox(imageView);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}

