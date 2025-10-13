package dev.mattz.data.graphics;

public interface Raster {
    void setPixel(int x, int y, int color) ;
    int getPixel(int x, int y);
    void clear();
    int getWidth() ;
    int getHeight();
    void update(int newWidth, int newHeight);
}
