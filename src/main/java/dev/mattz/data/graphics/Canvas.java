package dev.mattz.data.graphics;

import dev.mattz.data.Mode;
import dev.mattz.data.gui.controllers.CanvasController;
import dev.mattz.data.gui.controllers.ColorPaletteController;
import dev.mattz.data.gui.controllers.ToolbarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    private final BufferedImage bufferedImage;

    private final CanvasController canvasController;

    public Canvas(int width, int height, ColorPaletteController colorPaletteController, ToolbarController toolbarController) {
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.canvasController = new CanvasController(colorPaletteController, toolbarController);

        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (canvasController.getCurrentMode() == Mode.LINE);
                System.out.println("Mouse Pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (canvasController.getCurrentMode() == Mode.PENCIL) {
                    if (SwingUtilities.isLeftMouseButton(e)) mouseDraw(e.getX(), e.getY(), canvasController.getPrimaryColor());
                    else if (SwingUtilities.isRightMouseButton(e)) mouseDraw(e.getX(), e.getY(), canvasController.getSecondaryColor());
                } else if (canvasController.getCurrentMode() == Mode.LINE) {
                    System.out.println("Mouse Dragged");
                }
            }
        });
        prepareCanvas();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
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

    private void mouseDraw(int x, int y, Color color) {
        if (x >= 0 && x <= this.getWidth() - 1 && y >= 0 && y <= this.getHeight() - 1) {
            bufferedImage.setRGB(x, y, color.getRGB());
            this.repaint();
        }
    }
}
