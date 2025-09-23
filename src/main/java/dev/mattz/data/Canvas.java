package dev.mattz.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.Serial;


public class Canvas extends JFrame {
    private Color currentColor = Color.BLACK;

    private final JPanel jPanelDrawingArea;
    private final JPanel jPanelColorsPalette = new JPanel();
    private BufferedImage img;

    public Canvas(int width, int height) {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("resize");
            }
        });
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(jPanelColorsPalette,  BorderLayout.NORTH);

        JButton jButtonColorBlack = new JButton();
        jButtonColorBlack.setBackground(Color.BLACK);
        jButtonColorBlack.setBounds(0, 0, 100, 100);
        jPanelColorsPalette.add(jButtonColorBlack);


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
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    System.out.println("negr");
                    currentColor = Color.BLUE;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    int stredX = jPanelDrawingArea.getWidth() / 2;
                    int stredY = jPanelDrawingArea.getHeight() / 2;
                    for (int i = stredX; i <= jPanelDrawingArea.getWidth(); i++) {
                        if (i == jPanelDrawingArea.getWidth() - 1) break;
                        img.setRGB(i, stredY, currentColor.getRGB());
                    }
                    jPanelDrawingArea.repaint();
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
                mouseDraw(e.getX(), e.getY(), currentColor);
            }
        });

        jPanelDrawingArea.setPreferredSize(new Dimension(width, height));

        this.add(jPanelDrawingArea, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
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
            img.setRGB(x, y, color.getRGB());
            jPanelDrawingArea.repaint();
        }
    }

    public void start() {
        draw();
        jPanelDrawingArea.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}
