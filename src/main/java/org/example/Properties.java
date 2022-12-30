package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
    public void start(Stage primaryStage){
        window = primaryStage;
        window.setTitle(" properties ");
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(8);
        grid.setHgap(10);

        //zolta ikona
        Label yellowDot = new Label(" Yellow dots: ");
        GridPane.setConstraints(yellowDot,0,1,1,1);

        Label yellowDotLabel = new Label(" used for fields where amoutnt of animals there is between 0 and 5 ");
        GridPane.setConstraints(yellowDotLabel,1,1,1,1);

        //pomaranczowa ikona
        Label orangeDot = new Label(" Orange dots: ");
        GridPane.setConstraints(orangeDot,0,2,1,1);

        Label orangeDotLabel = new Label(" used for fields where amoutnt of animals there is between 5 and 25 ");
        GridPane.setConstraints(orangeDotLabel,1,2,1,1);

        //bordowa ikona
        Label claretDot = new Label(" Claret dots: ");
        GridPane.setConstraints(claretDot,0,3,1,1);

        Label claretDotLabel = new Label(" used for fields where amoutnt of animals there is more than 25 ");
        GridPane.setConstraints(claretDotLabel,1,3,1,1);

        grid.getChildren().addAll(yellowDot, yellowDotLabel, orangeDot, orangeDotLabel,
                claretDot, claretDotLabel);
        Scene scene = new Scene(grid,800,200);
        window.setScene(scene);
        window.show();

    }

}
