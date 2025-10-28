package dev.mattz.data.gui.views;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;


public class ToolbarView extends JPanel {
    private boolean isLocked = false;

    JButton jButtonSelectMode = new JButton();
    JButton jButtonLineMode = new JButton();
    JButton jButtonPolygonMode = new JButton();
    JButton jButtonPencilMode = new JButton();
    JButton jButtonFillMode = new JButton();
    JButton jButtonRectangleMode = new JButton();


    JButton currentButton = jButtonSelectMode;

    public ToolbarView() {
        this.add(jButtonSelectMode);
        this.add(jButtonLineMode);
        this.add(jButtonPolygonMode);
        this.add(jButtonPencilMode);
        this.add(jButtonFillMode);
        this.add(jButtonRectangleMode);

        jButtonSelectMode.setBounds(0, 0, 40, 40);
        jButtonSelectMode.setBorder(null);
        jButtonSelectMode.setFocusable(false);
        jButtonSelectMode.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/select_tool_icon.png"))));
        jButtonSelectMode.setBorder(new LineBorder(Color.BLUE, 3));
        jButtonSelectMode.addActionListener(_ -> {
            if (!isLocked)
                setCurrentButton(this.jButtonSelectMode);
        });

        jButtonLineMode.setBounds(40, 0, 40, 40);
        jButtonLineMode.setBorder(null);
        jButtonLineMode.setFocusable(false);
        jButtonLineMode.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/line_tool_icon.png"))));
        jButtonLineMode.addActionListener(_ -> {
            if (!isLocked)
                setCurrentButton(this.jButtonLineMode);
        });

        jButtonPolygonMode.setBounds(80, 0, 40, 40);
        jButtonPolygonMode.setBorder(null);
        jButtonPolygonMode.setFocusable(false);
        jButtonPolygonMode.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/polygon_tool_icon.png"))));
        jButtonPolygonMode.addActionListener(_ -> {
            if (!isLocked)
                setCurrentButton(this.jButtonPolygonMode);
        });

        jButtonPencilMode.setBounds(120, 0, 40, 40);
        jButtonPencilMode.setBorder(null);
        jButtonPencilMode.setFocusable(false);
        jButtonPencilMode.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pencil_tool_icon.png"))));
        jButtonPencilMode.addActionListener(_ -> {
            if (!isLocked)
                setCurrentButton(this.jButtonPencilMode);
        });

        jButtonFillMode.setBounds(160, 0, 40, 40);
        jButtonFillMode.setBorder(null);
        jButtonFillMode.setFocusable(false);
        jButtonFillMode.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/fill_tool_icon.png"))));
        jButtonFillMode.addActionListener(_ -> {
            if (!isLocked)
                setCurrentButton(this.jButtonFillMode);
        });

        jButtonRectangleMode.setBounds(200, 0, 40, 40);
        jButtonRectangleMode.setBorder(null);
        jButtonRectangleMode.setFocusable(false);
        jButtonRectangleMode.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/rectangle_tool_icon.png"))));
        jButtonRectangleMode.addActionListener(_ -> {
            if (!isLocked)
                setCurrentButton(this.jButtonRectangleMode);
        });

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

    public void setJButtonSelectModeListener(ActionListener listener) {
        jButtonSelectMode.addActionListener(listener);
    }

    public void setJButtonLineModeListener(ActionListener listener) {
        jButtonLineMode.addActionListener(listener);
    }

    public void setJButtonPolygonModeListener(ActionListener listener) {
        jButtonPolygonMode.addActionListener(listener);
    }

    public void setJButtonPencilModeListener(ActionListener listener) {
        jButtonPencilMode.addActionListener(listener);
    }

    public void setJButtonFillModeListener(ActionListener listener) {
        jButtonFillMode.addActionListener(listener);
    }

    public void setJButtonRectangleModeListener(ActionListener listener) {
        jButtonRectangleMode.addActionListener(listener);
    }

}
