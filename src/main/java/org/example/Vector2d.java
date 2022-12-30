package org.example;

import java.util.Objects;

public class Vector2d {
    public int x;
    public int y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        else if (!(other instanceof Vector2d)) return false;
        else return this.x == ((Vector2d) other).x && this.y == ((Vector2d) other).y;
    }
    @Override
    public int hashCode() { return Objects.hash(x, y); };

}

