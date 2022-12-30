package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static java.lang.System.exit;
import static java.lang.System.out;

public class AppTemp extends Application implements IPositionChangeObserver{
    Label label;
    GridPane grid;
    Scene scene;
    AnimalMap map;
    Statistics statistics;;
    SimulationEngine engine;
    Thread engineThread;
    public void setSettingsToApp(AnimalMap map, SimulationEngine engine){
        this.map = map;
        this.engine = engine;
        this.statistics = new Statistics(this.map, this,this.engine);
    }
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle(" Evolution on the Earth 2022 ");
        grid = new GridPane();
        Button button = new Button("   STOP   ");
        Button button1 = new Button(" statistics ");
        Button button2 = new Button(" properties "); //opisy ilustracji uzytych na mapie
        VBox vBox = new VBox(button, button1, button2);
        button.setOnAction(action ->{
            primaryStage.close();
            exit(0);
        });

        button2.setOnAction(action -> {
            Properties properties = new Properties(this.map, this, this.engine);
            Stage newWindow = new Stage();
            try {
                properties.start(newWindow);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        //zapis statystyk do pliku csv
        StatisticsToFile statisticsToFile = new StatisticsToFile(this.statistics);
        statisticsToFile.makeData("statistics"+Integer.toString(this.engine.getSimulationDay())+".csv");

        grid.setGridLinesVisible(true);
        HBox hBox = new HBox(vBox, grid);
        scene = new Scene(hBox, 800, 600);
        this.addObjectsToGrid(grid);

        primaryStage.setScene(scene);
        primaryStage.show();

        engineThread = new Thread(engine);
        engineThread.start();
        button1.setOnAction(action -> {
            Stage window = new Stage();
            statistics.start(window);
        });

    }
    public void renderMap(AnimalMap newMap) throws FileNotFoundException {
        this.grid.setGridLinesVisible(false);
        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();
        this.grid.getChildren().clear();
        this.grid.setGridLinesVisible(true);
        this.map=newMap;
        addObjectsToGrid(grid);
    }

    public void updateScene() throws FileNotFoundException {
        engine.moveOneDay(this.engine.getSimulationDay());
        this.engine.setSimulationDay(this.engine.getSimulationDay()+1);
       //zapis staystyk do pliku csv
        StatisticsToFile statisticsToFile = new StatisticsToFile(this.statistics);
        statisticsToFile.makeData("statistics"+Integer.toString(this.engine.getSimulationDay())+".csv");
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);
    }

    private void addObjectsToGrid(GridPane grid) throws FileNotFoundException {
        for (int i = 0; i <= map.getMapWidth(); i++) grid.getColumnConstraints().add(new ColumnConstraints((double)550/map.getMapWidth()));
        for (int i = 0; i <= map.getMapHeight(); i++) grid.getRowConstraints().add(new RowConstraints((double)550/map.getMapHeight()));
        label = new Label("y/x");
        grid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);

        for (int i = 0; i <= map.getMapHeight()-1; i++) {
            for (int j = 0; j <= map.getMapWidth()-1; j++) {
                label = new Label(Integer.toString(i));
                grid.add(label, 0, map.getMapHeight() - i);
                GridPane.setHalignment(label, HPos.CENTER);

                label = new Label(Integer.toString(j));
                grid.add(label, 1 + j, 0);
                GridPane.setHalignment(label, HPos.CENTER);

                Plant mapElement = map.objectAtPlant(new Vector2d(j, i));
                ArrayList<Animal> animalList = map.objectsAt(new Vector2d(j, i));

                if (animalList != null && mapElement != null) {
                    VBox box = new GuiBoxElementAnimalPlant(mapElement, animalList,map).toBox();
                    grid.add(box, 1 + j, map.getMapHeight() - i);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else if (animalList != null){
                    VBox box = new GuiElementBoxAnimal(mapElement, animalList, map).toBox();
                    grid.add(box, 1 + j, map.getMapHeight() - i);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else if (mapElement != null) {
                    VBox box = new GuiElementBoxPlant(mapElement, animalList, map).toBox();
                    grid.add(box, 1 + j, map.getMapHeight() - i);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }
    public void init() {
        try {
            Thread engineThread = new Thread(engine::run);
            engineThread.start();
        } catch(IllegalArgumentException ex) {
            out.println(ex.getMessage());
            exit(0);
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, int index) {
        Platform.runLater(() -> {
            grid.getChildren().clear();
            try {
                this.updateScene();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}

