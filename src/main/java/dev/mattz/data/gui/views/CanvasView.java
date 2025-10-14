package dev.mattz.data.gui.views;

import dev.mattz.data.graphics.RasterImage;
import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Point2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CanvasView extends JPanel {
    private BufferedImage bufferedImageLayer1;
    private final BufferedImage bufferedImage;

    private Point2D tempStart, tempEnd, polygonStart;
    private RasterImage rasterImage;
    private Color tempColor;

    ArrayList<Drawable> drawables = new ArrayList<>();

    public CanvasView(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.rasterImage = new RasterImage(bufferedImage);
        this.setPreferredSize(new Dimension(width, height));
        clear();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        clear();
        Graphics2D g2d = (Graphics2D) graphics;

        //Line preview
        if (tempStart != null && tempEnd != null) {
            rasterImage.lineMode(tempStart, tempEnd, tempColor);
//            g2d.setColor(tempColor);
//            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                    0, new float[]{5, 5}, 0));
//            g2d.drawLine(tempStart.getX(), tempStart.getY(),
//                    tempEnd.getX(), tempEnd.getY());
        }

        //Polygon start point
        if (polygonStart != null) {
            graphics.setColor(Color.RED);
            int radius = 5;
            g2d.fillOval(polygonStart.getX() - radius, polygonStart.getY() - radius, radius * 2, radius * 2);
            g2d.dispose();
        }
        g2d.drawImage(bufferedImage, 0, 0, null);

    }

    public BufferedImage getRasterizedBufferImage() {
        return bufferedImage;
    }

    public void clear() {
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g2d.dispose();
    }

    public void setTemporaryLine(Point2D start, Point2D end, Color color) {
        this.tempStart = start;
        this.tempEnd = end;
        this.tempColor = color;
        repaint();
    }

    public void setPolygonStart(Point2D point) {
        this.polygonStart = point;
        repaint();
    }

    public void clearTemporaryLine() {
        this.tempStart = this.tempEnd = null;
        this.tempColor = null;
        repaint();
    }

    public void clearPolygonStart() {
        polygonStart = null;
        repaint();
    }
}

