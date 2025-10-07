//package dev.mattz.data.panels;
//
//import dev.mattz.data.Mode;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//import java.awt.image.BufferedImage;
//
//public class DrawableAreaPanel extends JPanel {
//
//    private ColorPalette colorPalette;
//    private Toolbar toolbar;
//
//    public Canvas(int width, int height, ColorPalette colorPalette, Toolbar toolbar) {
//        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        this.colorPalette = colorPalette;
//        this.toolbar = toolbar;
//
//        this.setPreferredSize(new Dimension(width, height));
//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (toolbar.getCurrentMode() == Mode.LINE);
//                System.out.println("Mouse Pressed");
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//        });
//        this.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                if (toolbar.getCurrentMode() == Mode.PENCIL) {
//                    if (SwingUtilities.isLeftMouseButton(e)) mouseDraw(e.getX(), e.getY(), colorPalette.getPrimaryColor());
//                    else if (SwingUtilities.isRightMouseButton(e)) mouseDraw(e.getX(), e.getY(), colorPalette.getSecundaryColor());
//                } else if (toolbar.getCurrentMode() == Mode.LINE) {
//                    System.out.println("Mouse Dragged");
//                }
//            }
//        });
//        prepareCanvas();
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        present(g);
//    }
//
//    private void prepareCanvas() {
//        clear();
//        repaint();
//    }
//
//    public void clear() {
//        Graphics gr = bufferedImage.getGraphics();
//        gr.setColor(Color.BLACK);
//        gr.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
//    }
//
//    public void present(Graphics graphics) {
//        graphics.drawImage(bufferedImage, 0, 0, null);
//    }
//
//    private void mouseDraw(int x, int y, Color color) {
//        if (x >= 0 && x <= this.getWidth() - 1 && y >= 0 && y <= this.getHeight() - 1) {
//            bufferedImage.setRGB(x, y, color.getRGB());
//            this.repaint();
//        }
//    }
//}
