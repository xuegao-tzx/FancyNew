package com.xcl.fancynew;

import ohos.agp.components.element.PixelMapElement;
import ohos.agp.render.Canvas;
import ohos.agp.render.Texture;
import ohos.media.image.PixelMap;

/**
 * github wongzy
 * wongzhenyu96@gmail.com
 * 2020-04-29
 */
public class BitmapUtils {
    private BitmapUtils() {

    }

    /**
     * Drawable to bitmap pixel map.
     *
     * @param drawable the drawable
     * @return the pixel map
     */
    public static PixelMap drawableToBitmap(PixelMapElement drawable) {

        int w = drawable.getWidth();
        int h = drawable.getHeight();

        PixelMap pixelMap = drawable.getPixelMap();

        Canvas canvas = new Canvas(new Texture(pixelMap));
        drawable.setBounds(0, 0, w, h);
        drawable.drawToCanvas(canvas);

        return pixelMap;
    }
}
