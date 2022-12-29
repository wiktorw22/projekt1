package org.example;
//package org.example;
//
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//
//public class App extends Application {
//    GridPane gridPane = new GridPane();
//    private Label mylabel;
//    private TextField numberOfAnimals;
//    private Button myButton;
//    int initialNumberOfAnimals;
//    public void submit(ActionEvent event){
//        initialNumberOfAnimals = Integer.parseInt(numberOfAnimals.getText());
//    }
//
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        gridPane.setMinSize(400, 200);
//        gridPane.setPadding(new Insets(20, 20, 20, 20));
//        Scene scene = new Scene(gridPane, 700, 700);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    Statistics statistics;
    //    int leftBoundary;
//    int rightBoundary;
//    int bottomBoundary;
//    int topBoundary;
//    int height;
//    int width;
    SimulationEngine engine;
    Thread engineThread;

    public void setSettingsToApp(AnimalMap map, SimulationEngine engine){
        this.map = map;
        this.engine = engine;
        this.statistics = new Statistics(this.map, this,this.engine);
    }
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle(" Ewolucyjne polowanie 2022 ");
        grid = new GridPane();
        Button button = new Button("   STOP   ");
        Button button1 = new Button(" statystyki ");
        VBox vBox = new VBox(button, button1);
        button.setOnAction(action ->{
            primaryStage.close();
            exit(0);
        });

        StatisticsToFile statisticsToFile = new StatisticsToFile(this.statistics);
        statisticsToFile.makeData("statistics"+Integer.toString(this.engine.getSimulationDay())+".csv");

        //primaryStage.show();
        grid.setGridLinesVisible(true);
        HBox hBox = new HBox(vBox, grid);
        scene = new Scene(hBox, 800, 600);
        this.addObjectsToGrid(grid);
//        try {
//            this.updateScene();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        primaryStage.setScene(scene);
        primaryStage.show();

        engineThread = new Thread(engine);
        engineThread.start();
        button1.setOnAction(action -> {
            Stage window = new Stage(); //DZIALAAAAAAAAAAAAA
            statistics.start(window);
        });

        //po kliknieciu na przycisk powinno sie pokazac okienko kolejnego dnia - POKAZUJE SIE
        //this.updateScene();

        //for (int i = 0; i <= map.getMapWidth(); i++) grid.getColumnConstraints().add(new ColumnConstraints(550/map.getMapWidth()));
        //for (int i = 0; i <= map.getMapHeight(); i++) grid.getRowConstraints().add(new RowConstraints(550/map.getMapHeight()));
        //HBox hBox = new HBox(vBox, grid);

        //scene = new Scene(hBox, 800, 700);

        //primaryStage.setScene(scene);
        //primaryStage.show();
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
        //tutaj powinien sie wywolywac kazdy kolejny dzien symulacji, zeby wyswietlic go
        engine.moveOneDay(this.engine.getSimulationDay());
        this.engine.setSimulationDay(this.engine.getSimulationDay()+1);
        //jest progres ale kazdy dzien wywoluje sie na pierwszym genie - ZMIENIONE juz Z 'nieco szalenstwa'
        StatisticsToFile statisticsToFile = new StatisticsToFile(this.statistics);
        statisticsToFile.makeData("statistics"+Integer.toString(this.engine.getSimulationDay())+".csv");
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);

    }

    private void addObjectsToGrid(GridPane grid) throws FileNotFoundException {
        for (int i = 0; i <= map.getMapWidth(); i++) grid.getColumnConstraints().add(new ColumnConstraints(550/map.getMapWidth()));
        for (int i = 0; i <= map.getMapHeight(); i++) grid.getRowConstraints().add(new RowConstraints(550/map.getMapHeight()));
        label = new Label("y/x");
        grid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);

//        Vector2dLab8[] boundaries = map.findBoundaries();
//        leftBoundary = boundaries[0].x;
//        rightBoundary = boundaries[1].x;
//        bottomBoundary = boundaries[0].y;
//        topBoundary = boundaries[1].y;
//        height = topBoundary - bottomBoundary + 2;
//        width = rightBoundary - leftBoundary + 2;

        for (int i = 0; i <= map.getMapHeight()-1; i++) {
            for (int j = 0; j <= map.getMapWidth()-1; j++) {
                label = new Label(Integer.toString(i));
                grid.add(label, 0, map.getMapHeight() - i);
                GridPane.setHalignment(label, HPos.CENTER);

                label = new Label(Integer.toString(j));
                grid.add(label, 1 + j, 0);
                GridPane.setHalignment(label, HPos.CENTER);

                //IMapElementTemp
                Plant mapElement = map.objectAtPlant(new Vector2d(j, i));
                ArrayList<Animal> animalList = map.objectsAt(new Vector2d(j, i));

                //jak zamalowac pola ze zwierzakami w zaleznosci od ich liczby
                //im wiecej na danym polu tym mocniejszy odcien wybranego koloru
                //taka wizualizacja powinna sie zmieniac co dzien
                if (animalList != null && mapElement != null) {
                    VBox box = new GuiBoxElementAnimalPlant(mapElement, animalList,map).toBox();
                    grid.add(box, 1 + j, map.getMapHeight() - i);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else if (animalList != null){
                    VBox box = new GuiElementBoxAnimal(animalList,map).toBoxAnimals();
                    grid.add(box, 1 + j, map.getMapHeight() - i);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else if (mapElement != null) {
                    VBox box = new GuiElementBoxPlant(mapElement,map).toBox();
                    grid.add(box, 1 + j, map.getMapHeight() - i);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }
    public void init() {

        try {

//            map = new AnimalMap(10, 14, 30, 10, 80, 2);
//            engine = new SimulationEngine(map, this);
//            engine.run(); //trzeba bedzie wyswietlac po kolei kazdy dzien symulacji - JEST
//            if(engine.getSimulationDay()==0){
//                engine.makePlaceForDeadAnimals();
//                engine.placePlants(this.map.getStartAmountOfPlants());
//                engine.placeAnimals();
//                engine.multiplicate();
//                engine.moveOneDay(1);
//            }
            //engine.setSimulationDay(this.engine.getSimulationDay()+1);

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

