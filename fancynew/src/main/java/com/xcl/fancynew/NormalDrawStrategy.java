package com.xcl.fancynew;


import ohos.agp.components.element.PixelMapElement;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.render.PixelMapHolder;
import ohos.agp.utils.Color;
import ohos.agp.utils.Matrix;
import ohos.agp.utils.RectFloat;
import ohos.agp.utils.TextAlignment;
import ohos.media.image.PixelMap;

/**
 * The type Normal draw strategy.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
public class NormalDrawStrategy implements DrawStrategy {

    /**
     * Instantiates a new Normal draw strategy.
     */
    public NormalDrawStrategy() {
    }

    @Override
    public void drawAppName(Canvas canvas, float fraction, String name, int colorOfAppName,
                            WidthAndHeightOfView widthAndHeightOfView) {
        canvas.save();
        int width = widthAndHeightOfView.getWidth();
        int height = widthAndHeightOfView.getHeight();
        Paint paint = new Paint();
        paint.setColor(new Color(colorOfAppName));
        paint.setTextAlign(TextAlignment.CENTER);
        paint.setTextSize(50);
        canvas.drawText(paint, name, width >> 1, (height >> 1) + 50);
        canvas.restore();
    }

    @Override
    public void drawAppIcon(Canvas canvas, float fraction, PixelMap icon, int colorOfIcon, WidthAndHeightOfView widthAndHeightOfView) {
        canvas.save();
        int width = widthAndHeightOfView.getWidth();
        int height = widthAndHeightOfView.getHeight();
        Paint paint = new Paint();
        PixelMap bitmap = BitmapUtils.drawableToBitmap(new PixelMapElement(icon));

        int bitmapWidth = bitmap.getImageInfo().size.width;
        int bitmapHeight = bitmap.getImageInfo().size.height;
        int radius = bitmapWidth * 3 / 2;
        int centerX = width / 2 + bitmapWidth / 2;
        int centerY = height / 2 - 100;
        if (fraction <= 0.60) {
            Path path = new Path();
            Matrix matrix = new Matrix();
            matrix.postScale(1.2f, 1.2f, centerX - (bitmapWidth >> 1), centerY - (bitmapHeight >> 1));
            path.addCircle(centerX, centerY, radius * (fraction - 0.1f) * 2, Path.Direction.CLOCK_WISE);
            canvas.concat(matrix);
            canvas.clipPath(path, Canvas.ClipOp.INTERSECT);
            canvas.drawPixelMapHolder(new PixelMapHolder(bitmap), centerX - bitmapWidth, centerY - bitmapHeight, paint);
        } else {
            Matrix matrix = new Matrix();
            matrix.postScale(1.2f + (0.5f) * (fraction - 0.6f) * 2.5f,
                    1.2f + (0.5f) * (fraction - 0.6f) * 2.5f,
                    centerX - (bitmapWidth >> 1), centerY - (bitmapHeight >> 1));
            canvas.concat(matrix);
            canvas.drawPixelMapHolder(new PixelMapHolder(bitmap), centerX - bitmapWidth, centerY - bitmapHeight, paint);
        }
        canvas.restore();
    }

    @Override
    public void drawAppStatement(Canvas canvas, float fraction, String statement, int colorOfStatement,
                                 WidthAndHeightOfView widthAndHeightOfView) {
        canvas.save();
        int width = widthAndHeightOfView.getWidth();
        int height = widthAndHeightOfView.getHeight();
        Paint paint = new Paint();
        paint.setColor(new Color(colorOfStatement));
        paint.setStyle(Paint.Style.STROKE_STYLE);
        paint.setTextSize(45);
        paint.horizontalTilt(-0.2f);
        paint.setTextAlign(TextAlignment.CENTER);
        RectFloat rectF = new RectFloat((width >> 2) - statement.length(), height * 7 / 8,
                width * 3, height);
        if (fraction <= 0.60f) {
            Path path = new Path();
            path.addArc(rectF, 193, 40 * fraction * 1.67f);
            canvas.drawPath(path, paint);
        } else {
            Path path = new Path();
            path.addArc(rectF, 193, 40);
            canvas.drawPath(path, paint);
            canvas.drawTextOnPath(paint, statement, path, 0, 0);
        }
        canvas.restore();
    }
}
