package dev.mattz.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ColorPalette extends JPanel {
    JButton jButtonPrimaryColor = new JButton();
    JButton jButtonSecondaryColor = new JButton();

    ArrayList<Color> colorList = new ArrayList<>();

    public ColorPalette() {
        this.setPreferredSize(new Dimension(330, 40));
        this.setLayout(null);
        this.add(jButtonPrimaryColor);
        this.add(jButtonSecondaryColor);

        jButtonPrimaryColor.setBackground(Color.BLACK);
        jButtonPrimaryColor.setBounds(10, 5, 20, 20);
        jButtonPrimaryColor.setEnabled(false);

        jButtonSecondaryColor.setBackground(Color.WHITE);
        jButtonSecondaryColor.setBounds(20, 15, 20, 20);
        jButtonSecondaryColor.setEnabled(false);

        fillColorList();
        setupColorButtons();
    }

    private void setupColorButtons() {
        for (int i = 0; i < colorList.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                JButton jButtonColor = new JButton();
                jButtonColor.setBackground(colorList.get(i * 2 + j));
                jButtonColor.setBounds(i * 20 + 50, j * 20, 20, 20);
                int finalI = i;
                int finalJ = j;
                jButtonColor.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            Canvas.primaryColor = colorList.get(finalI * 2 + finalJ);
                            jButtonPrimaryColor.setBackground(colorList.get(finalI * 2 + finalJ));
                        }
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            Canvas.secundaryColor = colorList.get(finalI * 2 + finalJ);
                            jButtonSecondaryColor.setBackground(colorList.get(finalI * 2 + finalJ));
                        }
                    }
                });
                this.add(jButtonColor);
            }
        }
    }

    private void fillColorList() {
        colorList.add(Color.BLACK);
        colorList.add(Color.WHITE);
        colorList.add(Color.DARK_GRAY);
        colorList.add(Color.LIGHT_GRAY);
        colorList.add(new Color(131, 0, 16));
        colorList.add(Color.RED);
        colorList.add(new Color(127, 130, 38));
        colorList.add(Color.YELLOW);
        colorList.add(new Color(5, 127, 35));
        colorList.add(Color.GREEN);
        colorList.add(new Color(0, 129, 123));
        colorList.add(Color.CYAN);
        colorList.add(new Color(0, 25, 120));
        colorList.add(new Color(0, 57, 236));
        colorList.add(new Color(123, 18, 123));
        colorList.add(new Color(248, 42, 238));
        colorList.add(new Color(127, 130, 72));
        colorList.add(new Color(251, 255, 135));
        colorList.add(new Color(5, 65, 63));
        colorList.add(new Color(0, 253, 131));
        colorList.add(new Color(9, 128, 249));
        colorList.add(new Color(129, 254, 255));
        colorList.add(new Color(2, 65, 128));
        colorList.add(new Color(126, 129, 251));
        colorList.add(new Color(127, 3, 247));
        colorList.add(new Color(250, 1, 133));
        colorList.add(new Color(131, 64, 0));
        colorList.add(new Color(251, 129, 68));
    }
}
