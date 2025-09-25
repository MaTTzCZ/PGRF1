package dev.mattz.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.Serial;


public class Canvas {
    private Color currentColor = Color.BLACK;
    Mode currentMode = Mode.BRUSH;
    private JFrame frame;
    private final JPanel jPanelDrawingArea;
    private final JPanel jPanelColorsPalette = new JPanel();
    JColorChooser jColorChooser = new JColorChooser();
    private BufferedImage img;

    public Canvas(int width, int height) {
        frame = new JFrame("Canvas");
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            }
        });
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton jButtonColorBlack = new JButton();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        jPanelDrawingArea = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    currentMode = Mode.BRUSH;
                }
            }
        });

        jPanelDrawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                img.setRGB(e.getX(), e.getY(), currentColor.getRGB());
                System.out.println(e.getX() + " " + e.getY());
                jPanelDrawingArea.repaint();
            }
        });
        jPanelDrawingArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentMode == Mode.BRUSH) mouseDraw(e.getX(), e.getY(), currentColor);
            }
        });


        jPanelDrawingArea.setPreferredSize(new Dimension(width, height));

        frame.add(jPanelDrawingArea, BorderLayout.CENTER);
        frame.add(jPanelColorsPalette, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear();
        img.setRGB(0, 0, 0xffff00);
    }

    private void mouseDraw(int x, int y, Color color) {
        if (x >= 0 && x <= jPanelDrawingArea.getWidth() - 1 && y >= 0 && y <= jPanelDrawingArea.getHeight() - 1) {
            System.out.println("frame " + frame.getWidth() + " " + frame.getHeight());
            System.out.println("panel " + jPanelDrawingArea.getWidth() + " " + jPanelDrawingArea.getHeight());
            img.setRGB(x, y, color.getRGB());
            jPanelDrawingArea.repaint();
        }
    }

    public void start() {
        draw();
        jPanelDrawingArea.repaint();
    }
}
