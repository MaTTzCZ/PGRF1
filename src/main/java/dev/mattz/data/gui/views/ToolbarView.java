package dev.mattz.data.gui.views;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;


public class ToolbarView extends JPanel {
    private boolean isLocked = false;
    private int buttonCount = 0;

    JButton currentButton;

    public ToolbarView() {
    }

    public void createButton(String imageURL, ActionListener listener) {
        JButton button = new JButton();
        if (buttonCount == 0) {
            currentButton = button;
            button.setBorder(new LineBorder(Color.BLUE, 3));
        } else button.setBorder(null);
        button.setBounds(buttonCount++ * 40 + 40, 0, 40, 40);
        button.setFocusable(false);
        button.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(imageURL))));
        button.addActionListener(listener);
        button.addActionListener(_ -> {
            if (!isLocked)
                setCurrentButton(button);
        });
        this.add(button);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }


    private void setCurrentButton(JButton button) {
        if (button != currentButton && !isLocked) {
            currentButton.setBorder(null);
            button.setBorder(new LineBorder(Color.BLUE, 3));
            currentButton = button;
        }
    }
}
