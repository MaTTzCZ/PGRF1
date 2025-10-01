package dev.mattz.data.panels;

import dev.mattz.data.Mode;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Toolbar extends JPanel {
    private final ArrayList<String> toolsImagesURLs = new ArrayList<>();
    private final List<Mode> modes;
    protected static Mode currentMode = Mode.PENCIL;
    private JButton currentModeButton;

    public Toolbar() {
        modes = Arrays.asList(Mode.values());
        fillToolIcons();
        setupToolbar();
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    private void setupToolbar() {
        for (int i = 0; i < toolsImagesURLs.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                JButton jButtonTool = new JButton();
                jButtonTool.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(toolsImagesURLs.get(i * 2 + j)))));
                jButtonTool.setBounds(i * 20 + 50, j * 20, 20, 20);
                jButtonTool.setFocusable(false);
                jButtonTool.setBorder(null);
                int finalI = i;
                int finalJ = j;
                if (i == 0 && j == 0) {
                    currentModeButton = jButtonTool;
                    jButtonTool.setBorder(new LineBorder(Color.BLUE, 3));

                }
                jButtonTool.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (jButtonTool != currentModeButton) {
                                currentMode = modes.get(finalI * 2 + finalJ);
                                System.out.println(currentMode);
                                jButtonTool.setBorder(new LineBorder(Color.BLUE, 3));
                                currentModeButton.setBorder(null);
                                currentModeButton = jButtonTool;
                            }
                        }
                    }
                });
                this.add(jButtonTool);
            }
        }
    }

    private void fillToolIcons() {
        toolsImagesURLs.add("/images/select_tool_icon.png");
        toolsImagesURLs.add("/images/lines_tool_icon.png");
        toolsImagesURLs.add("/images/pencil_tool_icon.png");
        toolsImagesURLs.add("/images/brush_tool_icon.png");

    }

}
