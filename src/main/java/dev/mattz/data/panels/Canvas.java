package dev.mattz.data.panels;

import dev.mattz.data.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    public static Color primaryColor = Color.BLACK;
    public static Color secundaryColor = Color.WHITE;

    private final BufferedImage bufferedImage;

    public Canvas(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse Pressed");
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (Toolbar.currentMode == Mode.PENCIL) {
                    if (SwingUtilities.isLeftMouseButton(e)) mouseDraw(e.getX(), e.getY(), primaryColor);
                    else if (SwingUtilities.isRightMouseButton(e)) mouseDraw(e.getX(), e.getY(), secundaryColor);
                } else if (Toolbar.currentMode == Mode.LINE) {
                    System.out.println("Mouse Dragged");
                }
            }
        });
        prepareCanvas();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        present(g);
    }

    private void prepareCanvas() {
        clear();
        repaint();
    }

    public void clear() {
        Graphics gr = bufferedImage.getGraphics();
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(bufferedImage, 0, 0, null);
    }

    private void mouseDraw(int x, int y, Color color) {
        if (x >= 0 && x <= this.getWidth() - 1 && y >= 0 && y <= this.getHeight() - 1) {
            bufferedImage.setRGB(x, y, color.getRGB());
            this.repaint();
        }
    }
}
