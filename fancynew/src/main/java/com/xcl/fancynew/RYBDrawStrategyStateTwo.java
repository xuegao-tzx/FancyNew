package com.xcl.fancynew;


import ohos.agp.components.element.PixelMapElement;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.render.PixelMapHolder;
import ohos.agp.utils.Color;
import ohos.agp.utils.Matrix;
import ohos.media.image.PixelMap;

/**
 * The type Ryb draw strategy state two.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
class RYBDrawStrategyStateTwo implements RYBDrawStrategyStateInterface {
    @Override
    public void drawIcon(Canvas canvas, float fraction, PixelMap drawable, int colorOfIcon,
                         WidthAndHeightOfView widthAndHeightOfView) {
        int centerX = widthAndHeightOfView.getWidth() / 2;
        int centerY = widthAndHeightOfView.getHeight() / 2 - 150;
        canvas.save();
        Paint paint = new Paint();
        float newFraction = (fraction - 0.65f) / 0.35f;
        paint.setColor(new Color(Color.getIntColor("#FFFF0500")));//RED
        canvas.drawCircle(centerX, centerY - 50, 100 * (1 - newFraction), paint);
        paint.setColor(new Color(Color.getIntColor("#FFFFD100")));//YELLOW
        canvas.drawCircle(centerX - 35, centerY + 35, 100 * (1 - newFraction), paint);
        paint.setColor(new Color(Color.getIntColor("#FF008BFF")));//BLUE
        canvas.drawCircle(centerX + 35, centerY + 35, 100 * (1 - newFraction), paint);
        canvas.restore();
        canvas.save();
        Path path = new Path();
        PixelMap bitmap = BitmapUtils.drawableToBitmap(new PixelMapElement(drawable));
        Matrix matrix = new Matrix();
        matrix.postScale(1.7f, 1.7f, centerX, centerY);
        canvas.concat(matrix);
        path.addCircle(centerX, centerY, bitmap.getImageInfo().size.height * 1.5f * newFraction, Path.Direction.CLOCK_WISE);
        canvas.clipPath(path, Canvas.ClipOp.INTERSECT);
        canvas.drawPixelMapHolder(new PixelMapHolder(bitmap), centerX - (bitmap.getImageInfo().size.width >> 1), centerY - bitmap.getImageInfo().size.height / 2, paint);
        canvas.restore();
    }
}
