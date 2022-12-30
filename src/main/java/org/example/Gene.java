package org.example;

public class Gene {
    private int gene;
    public Gene(int gene){
        this.gene = gene;
    }
    public int getGene(){
        return this.gene;
    }
    public Vector2d geneToVector2d(){
        return switch (this.gene) {
            case 0 -> new Vector2d(0, 1);
            case 1 -> new Vector2d(1, 1);
            case 2 -> new Vector2d(1, 0);
            case 3 -> new Vector2d(1, -1);
            case 4 -> new Vector2d(0, -1);
            case 5 -> new Vector2d(-1, -1);
            case 6 -> new Vector2d(-1, 0);
            case 7 -> new Vector2d(-1, 1);
            default -> throw new IllegalStateException("Unexpected value: " + this.gene);
        };
    }
}
