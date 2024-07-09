package Figures;

import java.awt.*;
import java.io.Serializable;

public class Square extends Figure implements Serializable {

    private int size;

    public Square(Point point, Color color) {
        super(point, color);
        this.size = getSize();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getPoint().x - size / 2, getPoint().y - size / 2, size, size);
    }
}
