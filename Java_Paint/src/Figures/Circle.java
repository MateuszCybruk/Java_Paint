package Figures;

import java.awt.*;
import java.io.Serializable;

public class Circle extends Figure implements Serializable {

    private int radius;

    public Circle(Point point, Color color) {
        super(point, color);
        this.radius = super.getSize() / 2;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getPoint().x - radius, getPoint().y - radius, radius * 2, radius * 2);
    }

    public int getRadius() {
        return radius;
    }
}
