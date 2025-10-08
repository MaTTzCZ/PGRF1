package dev.mattz.data.gui.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ColorPaletteView extends JPanel {
    JButton jButtonPrimaryColor = new JButton();
    JButton jButtonSecondaryColor = new JButton();

    JButton newButton = new JButton();


    public ColorPaletteView() {

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
    }

    public void createButtons(List<Color> colors) {
        for (int i = 0; i < colors.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                newButton = new JButton();
                newButton.setBackground(colors.get(i * 2 + j));
                newButton.setBounds(i * 20 + 50, j * 20, 20, 20);
                int finalI = i;
                int finalJ = j;
                newButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        int index = finalI * 2 + finalJ;
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            jButtonPrimaryColor.setBackground(colors.get());
                        }
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            controller.setSecondaryColor(finalI * 2 + finalJ);
                            jButtonPrimaryColor.setBackground(controller.getSecondaryColor());
                        }
                    }
                });
                this.add(newButton);
            }
        }
    }
    public void addListener(MouseListener listener){
        newButton.addMouseListener(listener);
    }
}
