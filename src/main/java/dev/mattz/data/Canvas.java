package dev.mattz.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.Serial;


public class Canvas {
    private Color currentColor = Color.RED;
    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;

    public Canvas(int width, int height) {
        frame = new JFrame();
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("resize");
            }
        });
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
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
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    System.out.println("negr");
                    currentColor = Color.BLUE;
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    int stredX = panel.getWidth() / 2;
                    int stredY = panel.getHeight() / 2;
                    for (int i = stredX; i <= panel.getWidth(); i++) {
                        if (i == panel.getWidth() - 1) break;
                        img.setRGB(i, stredY, currentColor.getRGB());
                    }
                    panel.repaint();
                }
            }
        });
        panel.addMouseListener(new  MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                img.setRGB(e.getX(), e.getY(), currentColor.getRGB());
                System.out.println(e.getX() + " " + e.getY());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear();
        img.setRGB(0, 0, 0xffff00);
    }

    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}
