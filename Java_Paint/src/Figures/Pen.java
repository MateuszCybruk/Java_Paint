package Figures;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Pen extends Figure implements Serializable {

    private ArrayList<Point> pointList = new ArrayList<>();

    public Pen(Point point, Color color) {
        super(point, color);
        pointList.add(point);
    }

    public void addPoint(Point point){
        pointList.add(point);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());

        for (int i = 0; i < pointList.size()-1; i++) {
            Point p1 = pointList.get(i);
            Point p2 = pointList.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

    }
}
