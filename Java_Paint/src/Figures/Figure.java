package Figures;

import java.awt.*;
import java.io.Serializable;

public abstract class Figure implements Serializable {
    private static final int size = 40;
    private Color color;
    private Point point;

    public Figure(Point point, Color color) {
        this.point = point;
        this.color = color;
    }

    public abstract void draw(Graphics g);

    public Color getColor() {
        return color;
    }

    public Point getPoint() {
        return point;
    }

    public int getSize() {
        return size;
    }
}
