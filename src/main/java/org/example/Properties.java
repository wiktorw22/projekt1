package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Properties extends Application {
    AnimalMap map;
    Stage window;
    AppTemp app;
    SimulationEngine engine;
    public Properties(AnimalMap map, AppTemp app, SimulationEngine engine){
        this.map = map;
        this.app = app;
        this.engine = engine;
    }
    public void start(Stage primaryStage) throws FileNotFoundException {
        window = primaryStage;
        window.setTitle(" properties ");
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(8);
        grid.setHgap(10);

        //zolta ikona
        ImageView image0 = new ImageView(new Image(new FileInputStream("src/main/resources/dot0.png")));
        GridPane.setConstraints(image0,0,1,1,1);

        Label image0Label = new Label(" used for fields where amount of animals there is between 0 and 3 ");
        GridPane.setConstraints(image0Label,1,1,1,1);

        //miodowa ikona
        ImageView image1 = new ImageView(new Image(new FileInputStream("src/main/resources/dot1.png")));
        GridPane.setConstraints(image1,0,2,1,1);

        Label image1Label = new Label(" used for fields where amount of animals there is between 3 and 8 ");
        GridPane.setConstraints(image1Label,1,2,1,1);

        //pomaranczowa ikona
        ImageView image2 = new ImageView(new Image(new FileInputStream("src/main/resources/dot2.png")));
        GridPane.setConstraints(image2,0,3,1,1);

        Label image2Label = new Label(" used for fields where amount of animals there is between 8 and 12 ");
        GridPane.setConstraints(image2Label,1,3,1,1);

        //czerwona ikona
        ImageView image3 = new ImageView(new Image(new FileInputStream("src/main/resources/dot3.png")));
        GridPane.setConstraints(image3,0,4,1,1);

        Label image3Label = new Label(" used for fields where amount of animals there is between 12 and 20 ");
        GridPane.setConstraints(image3Label,1,4,1,1);

        //bordowa ikona
        ImageView image4 = new ImageView(new Image(new FileInputStream("src/main/resources/dot4.png")));
        GridPane.setConstraints(image4,0,5,1,1);

        Label image4Label = new Label(" used for fields where amount of animals there is more than 20 ");
        GridPane.setConstraints(image4Label,1,5,1,1);

        grid.getChildren().addAll(image0, image0Label, image1, image1Label, image2, image2Label,
                image3, image3Label, image4, image4Label);
        Scene scene = new Scene(grid,800,600);
        window.setScene(scene);
        window.show();

    }

}
