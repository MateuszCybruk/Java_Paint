import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class DrawSwing {
    private static DrawPanel drawPanel;

    private JLabel statusLabel;
    private JLabel selectedToolLabel;
    private File currentFile;
    private static JFrame frame;

    private JMenuBar createMenuBar() {

        JMenu fileMenu, drawMenu;
        JMenuBar menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        //items of File menu
        JMenuItem fileMenuItemOpen = new JMenuItem("Open", KeyEvent.VK_O);
        fileMenuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileMenuItemOpen.addActionListener(e -> openFile());
        fileMenu.add(fileMenuItemOpen);

        JMenuItem fileMenuItemSave = new JMenuItem("Save", KeyEvent.VK_S);
        fileMenuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenuItemSave.addActionListener(e -> saveFile());
        fileMenu.add(fileMenuItemSave);

        JMenuItem fileMenuItemSaveAs = new JMenuItem("Save As...", KeyEvent.VK_S);
        fileMenuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
        fileMenuItemSaveAs.addActionListener(e -> saveAsFile());
        fileMenu.add(fileMenuItemSaveAs);

        fileMenu.addSeparator();

        JMenuItem fileMenuItemQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
        fileMenuItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        fileMenuItemQuit.addActionListener(e -> quit());
        fileMenu.add(fileMenuItemQuit);


        drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(drawMenu);

        //Items of Draw menu
        //submenu
        ButtonGroup group = new ButtonGroup();
        JMenu submenu = new JMenu("Figures");
        submenu.setMnemonic(KeyEvent.VK_1);
        

        JRadioButtonMenuItem circleItem = new JRadioButtonMenuItem("Circle");
        circleItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        circleItem.addActionListener(drawPanel);
        group.add(circleItem);
        submenu.add(circleItem);

        JRadioButtonMenuItem squareItem = new JRadioButtonMenuItem("Square");
        squareItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        squareItem.addActionListener(drawPanel);
        group.add(squareItem);
        submenu.add(squareItem);

        JRadioButtonMenuItem penItem = new JRadioButtonMenuItem("Pen");
        penItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        penItem.addActionListener(drawPanel);
        group.add(penItem);
        submenu.add(penItem);

        drawMenu.add(submenu);


        JMenuItem colorMenuItemClear = new JMenuItem("Color", KeyEvent.VK_C);
        colorMenuItemClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
        colorMenuItemClear.addActionListener(drawPanel);
        drawMenu.add(colorMenuItemClear);


        drawMenu.addSeparator();
        JMenuItem drawMenuItemClear = new JMenuItem("Clear", KeyEvent.VK_N);
        drawMenuItemClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
        drawMenuItemClear.addActionListener(drawPanel);
        drawMenu.add(drawMenuItemClear);

        return menuBar;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        Box box = Box.createHorizontalBox();

        box.add(selectedToolLabel);
        box.add(Box.createHorizontalGlue());
        box.add(statusLabel);
        toolBar.add(box);
        return toolBar;
    }

    private Container createContentPane(DrawPanel drawPanel) {
        drawPanel.setOpaque(true);
        drawPanel.setLayout(new BorderLayout());
        return drawPanel;
    }

    static void createAndShowGui() {
        //Create and set up the window.
        frame = new JFrame("Simple Draw");
        frame.setLocation(50, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DrawSwing drawSwing = new DrawSwing();
        drawSwing.statusLabel = new JLabel("New");
        drawSwing.selectedToolLabel = new JLabel("");

        drawPanel = new DrawPanel(drawSwing.statusLabel,  drawSwing.selectedToolLabel);

        frame.setJMenuBar(drawSwing.createMenuBar());
        frame.setContentPane(drawSwing.createContentPane(drawPanel));
        frame.add(drawSwing.createToolBar(), BorderLayout.PAGE_END);

        //Display the window.
        frame.setSize(450, 450);
        frame.setVisible(true);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(drawPanel);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            drawPanel.loadFromFile(file);
            currentFile = file;
            updateWindowTitle(currentFile.getName());
        }

    }

    private void saveFile() {
        if (currentFile != null) {
            drawPanel.saveToFile(currentFile);
            statusLabel.setText("Saved");
            updateWindowTitle(currentFile.getName());
        } else {
            saveAsFile();
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showSaveDialog(drawPanel);
        if (res == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            drawPanel.saveToFile(currentFile);
            statusLabel.setText("Saved");
            updateWindowTitle(currentFile.getName());
        }
    }

    private void quit() {
        int response = JOptionPane.showConfirmDialog(drawPanel,
                "Do you want to save your work before quiting?",
                "", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (response) {
            case JOptionPane.NO_OPTION -> System.exit(0);
            case JOptionPane.YES_OPTION -> saveAsFile();
            case JOptionPane.CANCEL_OPTION -> {
                return;
            }
            default -> System.out.println("Err");
        }
    }

    private void updateWindowTitle(String fileName) {
        frame.setTitle("Simple Draw: " + fileName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DrawSwing::createAndShowGui);
    }
}
