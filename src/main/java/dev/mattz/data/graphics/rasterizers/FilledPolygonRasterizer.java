package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.FilledPolygon;
import dev.mattz.data.graphics.rasterizers.fillers.ScanLinePolygonFiller;

import java.awt.image.BufferedImage;

public class FilledPolygonRasterizer implements Rasterizer {
    ScanLinePolygonFiller scanLinePolygonFiller = new ScanLinePolygonFiller();
    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        FilledPolygon filledPolygon = (FilledPolygon) drawable;
        scanLinePolygonFiller.draw(filledPolygon, bufferedImage);
    }
}
