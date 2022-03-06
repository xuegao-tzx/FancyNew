package com.xcl.fancynew;


import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.media.image.PixelMap;

/**
 * The type Ryb draw strategy state one.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
class RYBDrawStrategyStateOne implements RYBDrawStrategyStateInterface {
    @Override
    public void drawIcon(Canvas canvas, float fraction, PixelMap drawable, int colorOfIcon,
                         WidthAndHeightOfView widthAndHeightOfView) {
        float newFraction = fraction / 0.65f;
        int centerX = widthAndHeightOfView.getWidth() / 2;
        int centerY = widthAndHeightOfView.getHeight() / 2 - 150;
        Paint paint = new Paint();
        canvas.save();
        paint.setColor(new Color(Color.getIntColor("#FFFF0500")));//RED
        if (newFraction <= 0.33f) {
            canvas.drawCircle(centerX, centerY - 50, 100 * (newFraction / 0.33f), paint);
        } else {
            canvas.drawCircle(centerX, centerY - 50, 100, paint);
        }
        if (newFraction > 0.33f) {
            paint.setColor(new Color(Color.getIntColor("#FFFFD100")));//YELLOW
            if (newFraction <= 0.66f)
                canvas.drawCircle(centerX - 35, centerY + 35, 100 * ((newFraction - 0.33f) / 0.33f), paint);
            else
                canvas.drawCircle(centerX - 35, centerY + 35, 100, paint);
        }
        if (newFraction > 0.66f) {
            paint.setColor(new Color(Color.getIntColor("#FF008BFF")));//BLUE
            if (newFraction <= 1f)
                canvas.drawCircle(centerX + 35, centerY + 35, 100 * ((newFraction - 0.66f) / 0.34f), paint);
            else
                canvas.drawCircle(centerX + 35, centerY + 35, 100, paint);
        }
        canvas.restore();
    }
}
