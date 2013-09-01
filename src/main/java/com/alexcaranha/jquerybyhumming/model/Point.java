package com.alexcaranha.jquerybyhumming.model;

/**
 *
 * @author Lual
 */
public class Point <T1, T2> {
    private T1 x;
    private T2 y;

    public Point(T1 valueX, T2 valueY) {
        this.x = valueX;
        this.y = valueY;
    }

    public T1 getX() {
        return this.x;
    }

    public T2 getY() {
        return this.y;
    }

    public void setX(T1 value) {
        this.x = value;
    }

    public void getY(T2 value) {
        this.y = value;
    }
}
