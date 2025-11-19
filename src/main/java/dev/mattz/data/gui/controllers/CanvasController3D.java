package dev.mattz.data.gui.controllers;

import dev.mattz.data.graphics.drawable_objects.*;
import dev.mattz.data.graphics.rasterizers.line_clipping.SutherlandHodgmanClipper;
import dev.mattz.data.gui.models.ColorPaletteModel;
import dev.mattz.data.gui.views.*;

import javax.swing.*;
import java.awt.event.*;

public class CanvasController3D {
    private final MainView3D mainView3D;
    private final CanvasView3D canvasView3D;
    private final ToolbarView toolbarView;

    private final ColorPaletteModel colorPaletteModel;



    public CanvasController3D(MainView3D mainView3D, CanvasView3D canvasView3D, ToolbarView toolbarView) {
        this.mainView3D = mainView3D;
        this.canvasView3D = canvasView3D;
        this.toolbarView = toolbarView;
        canvasView3D.setFocusable(true);

        this.colorPaletteModel = ColorPaletteModel.getInstance();

        canvasView3D.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                switch (canvasView3D.getCurrentMode()) {
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                switch (canvasView3D.getCurrentMode()) {
                }

            }
        });

        canvasView3D.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                switch (canvasView3D.getCurrentMode()) {
                }
            }
        });

        canvasView3D.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                setKeyBinds(event);
            }
        });
    }


    private void setKeyBinds(KeyEvent event) {
        if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Z) {
            canvasView3D.undo();
        } else if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Y) {
            canvasView3D.redo();
        } else if (event.getKeyCode() == KeyEvent.VK_C) {
            canvasView3D.clearDrawables();
            canvasView3D.clearBufferedImage();
        }
    }
}
