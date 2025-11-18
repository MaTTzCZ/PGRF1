package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Drawable2D;
import dev.mattz.data.graphics.drawable_objects.FilledPolygon;
import dev.mattz.data.graphics.rasterizers.fillers.ScanLinePolygonFiller;

import java.awt.image.BufferedImage;

public class FilledPolygonRasterizer implements Rasterizer {
    ScanLinePolygonFiller scanLinePolygonFiller = new ScanLinePolygonFiller();
    @Override
    public void draw(Drawable2D drawable2D, BufferedImage bufferedImage) {
        FilledPolygon filledPolygon = (FilledPolygon) drawable2D;
        scanLinePolygonFiller.draw(filledPolygon, bufferedImage);
    }
}
