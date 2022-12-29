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

public class GuiElementBoxAnimal {

    Image image;
    ImageView imageView;
    Label label;

    public GuiElementBoxAnimal(ArrayList<Animal> animalList,AnimalMap map) throws FileNotFoundException {

        int numberOfAnimalsInTheBox = animalList.toArray().length;
        if(numberOfAnimalsInTheBox>0 && numberOfAnimalsInTheBox<5){
            image = new Image(new FileInputStream("src/main/resources/dot0.png"));
        }
        else if(numberOfAnimalsInTheBox>=5 && numberOfAnimalsInTheBox<25){
            image = new Image(new FileInputStream("src/main/resources/dot.png"));
        }
        else if(numberOfAnimalsInTheBox>0){
            image = new Image(new FileInputStream("src/main/resources/dot2.png"));
        }

        imageView = new ImageView(image);

        imageView.setFitWidth((double)500/max(map.getMapWidth(),map.getMapHeight()));
        imageView.setFitHeight((double)500/max(map.getMapWidth(),map.getMapHeight()));

////        if (numberOfAnimalsInTheBox > 0) {
////
////            label = new Label(Integer.toString(numberOfAnimalsInTheBox));
////        }
////        else{
////            label = new Label();
//        }

    }

    public VBox toBoxAnimals() {

        VBox box = new VBox(imageView);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}

