package org.example;

public class MapVisualizer {
    private static final String EMPTY_CELL = " ";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = "|";
    private AnimalMap map;

    public MapVisualizer(AnimalMap map) {
        this.map = map;
    }

    public String draw(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.y + 1; i >= lowerLeft.y - 1; i--) {
            if (i == upperRight.y + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.x; j <= upperRight.x + 1; j++) {
                if (i < lowerLeft.y || i > upperRight.y) {
                    builder.append(drawFrame(j <= upperRight.x));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.x) {
                        if (drawObjectAnimal(new Vector2d(j, i)) != EMPTY_CELL && drawObjectAnimal(new Vector2d(j, i)) != null) {
                            builder.append(drawObjectAnimal(new Vector2d(j, i)));
                            //builder.append(drawObjectPlant(new Vector2d(j, i)));
                        }
                        else{
                            builder.append(drawObjectPlant(new Vector2d(j, i)));
                        }
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
            builder.append(String.format("%2d", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }
//    public String toString(int numberOfAnimals){
//        return "numberOfAnimals";
//    }

    private String drawObjectAnimal(Vector2d currentPosition) {
        String result;
        int numberOfAnimals = 0;
        if(this.map.objectsAt(currentPosition) != null && this.map.objectsAt(currentPosition).toArray().length!=0){
            numberOfAnimals = this.map.objectsAt(currentPosition).toArray().length;
            result = Integer.toString(numberOfAnimals);
        }
        else{
            result = EMPTY_CELL;
        }


        return result;
    }

    private String drawObjectPlant(Vector2d currentPosition) {
        String result = null;
        if (this.map.isOccupiedPlant(currentPosition)) { //po dopasowaniu funkcji obslugujacych roslinki zaktualizowac wizualizacje ich
            Plant object = this.map.objectAtPlant(currentPosition);
            if (object != null) {
                result = object.toString();
            } else {
                result = EMPTY_CELL;
            }
        } else {
            result = EMPTY_CELL;
        }
        return result;
    }
}
