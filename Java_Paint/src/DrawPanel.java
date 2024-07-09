import Enums.DrawingMode;
import Figures.Circle;
import Figures.Figure;
import Figures.Pen;
import Figures.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class DrawPanel extends JPanel implements ActionListener {

    private ArrayList<Figure> figureList = new ArrayList<>();
    private JLabel selectedToolLabel,statusLabel;
    private DrawingMode currentMode = DrawingMode.PEN;
    private Color penColor = Color.BLACK;
    private boolean isDKeyPressed = false;

    DrawPanel(JLabel statusLabel, JLabel selectedToolLabel) {
        this.selectedToolLabel = selectedToolLabel;
        this.statusLabel = statusLabel;
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                DrawPanel.this.mousePressed(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                DrawPanel.this.mouseDragged(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    isDKeyPressed = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    isDKeyPressed = false;
                }
            }
        });
    }


    private void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            statusLabel.setText("Modified");
            Point point = e.getPoint();
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            Color color = new Color(r, g, b);
            switch (currentMode) {
                case CIRCLE -> figureList.add(new Circle(point, color));
                case SQUARE -> figureList.add(new Square(point, color));
                case PEN -> {
                    Pen pen = new Pen(point, penColor);
                    figureList.add(pen);
                }
                default -> {System.out.println("Unknown command");;}
            }
            repaint();
        } else if (SwingUtilities.isRightMouseButton(e) && isDKeyPressed) {
            statusLabel.setText("Modified");
            ArrayList<Figure> figuresToRemove = findFigureAtPoint(e.getPoint());
            if (!figuresToRemove.isEmpty()) {
                int response = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this figure?",
                        "", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    figureList.removeAll(figuresToRemove);
                    repaint();
                }
            }
            }
            isDKeyPressed = false;
        }


    private ArrayList<Figure> findFigureAtPoint(Point point) {
        ArrayList<Figure> res =  new ArrayList<>();
        for (Figure figure : figureList) {
            if (figure instanceof Circle){
                if(point.distance(figure.getPoint()) <= (double) ((Circle) figure).getRadius()){
                    res.add(figure);
                }
            } else if (figure instanceof Square) {
                Rectangle rect = new Rectangle(
                        figure.getPoint().x - figure.getSize() / 2,
                        figure.getPoint().y - figure.getSize() / 2,
                        figure.getSize(), figure.getSize());
                if(rect.contains(point)) {
                    res.add(figure);
                }
            }
        }
        return res;
    }

    private void mouseDragged(MouseEvent e) {
        if (currentMode == DrawingMode.PEN && SwingUtilities.isLeftMouseButton(e)) {
            Pen pen = (Pen) figureList.get(figureList.size() - 1);
            pen.addPoint(e.getPoint());
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Figure figure : figureList) {
            figure.draw(g);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Clear" -> {
                int response = JOptionPane.showConfirmDialog(this,
                        "Do you want to clear Canvas?",
                        "Confirm clear",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    figureList.clear();
                    repaint();
                }
            }
            case "Circle" -> {
                currentMode = DrawingMode.CIRCLE;
                selectedToolLabel.setText("Circle");
            }
            case "Square" -> {
                currentMode = DrawingMode.SQUARE;
                selectedToolLabel.setText("Square");
            }
            case "Pen" -> {
                currentMode = DrawingMode.PEN;
                selectedToolLabel.setText("Pen");
            }
            case "Color" -> {
                penColor = JColorChooser.showDialog(this, "Choose Pen Color", penColor);
            }
            default -> System.out.println("Unknown command");
        }
    }

    public void saveToFile(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(figureList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromFile(File file){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof ArrayList<?> tempList) {
                ArrayList<Figure> res = new ArrayList<>();
                for (Object o : tempList) {
                    if (o instanceof Figure) {
                        res.add((Figure) o);
                    } else {
                        throw new ClassCastException("Element is not of type Figure: " + o.getClass().getName());
                    }
                }
                figureList = res;
            } else {
                throw new ClassCastException("Object read is not an ArrayList");
            }
            repaint();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
