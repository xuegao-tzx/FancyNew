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
 * The type Line draw strategy.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
public class LineDrawStrategy implements DrawStrategy {

    /**
     * Instantiates a new Line draw strategy.
     */
    public LineDrawStrategy() {
    }

    @Override
    public void drawAppName(Canvas canvas, float fraction, String name, int colorOfAppName,
                            WidthAndHeightOfView widthAndHeightOfView) {
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(new Color(colorOfAppName));
        paint.setStyle(Paint.Style.STROKE_STYLE);
        paint.setStrokeWidth(5);
        paint.setTextSize(50);
        paint.setStrokeJoin(Paint.Join.ROUND_JOIN);
        paint.setTextAlign(TextAlignment.LEFT);
        float x = widthAndHeightOfView.getWidth() >> 1;
        int centerY = widthAndHeightOfView.getHeight() / 2;
        float y = centerY - 275;
        Path path = new Path();
        path.moveTo(x, y);
        if (fraction <= 0.50) {
            path.lineTo(x, y + (25 + name.length() + 250) * (fraction / 0.50f));
            canvas.drawPath(path, paint);
        } else {
            path.lineTo(x, y + (25 + name.length() + 250) * ((1 - fraction) / 0.50f));
            canvas.drawPath(path, paint);
            paint.setStyle(Paint.Style.FILL_STYLE);
            canvas.drawText(paint, name, x + 20, y + 150);
        }
        canvas.restore();
    }

    @Override
    public void drawAppIcon(Canvas canvas, float fraction, PixelMap icon, int colorOfIcon,
                            WidthAndHeightOfView widthAndHeightOfView) {
        int centerX = widthAndHeightOfView.getWidth() / 2;
        int centerY = widthAndHeightOfView.getHeight() / 2;
        PixelMap bitmap = BitmapUtils.drawableToBitmap(new PixelMapElement(icon));
        Paint paint = new Paint();
        paint.setColor(new Color(colorOfIcon));
        paint.setStrokeWidth(3);
        paint.setStrokeJoin(Paint.Join.ROUND_JOIN);
        paint.setStyle(Paint.Style.STROKE_STYLE);
        float bitmapLeft = centerX - 250;
        float bitmapRight = bitmapLeft + bitmap.getImageInfo().size.width * 1.7f;
        float bitmapTop = centerY - 250;
        float bitmapBottom = bitmapTop + bitmap.getImageInfo().size.height * 1.7f;
        canvas.save();
        if (fraction <= 0.75) {
            float newfraction = fraction / 0.75f;
            if (newfraction <= 0.25) {
                canvas.drawLine(bitmapLeft, bitmapBottom, bitmapLeft,
                        bitmapBottom - (bitmapBottom - bitmapTop) * (newfraction / 0.25f), paint);
            } else {
                canvas.drawLine(bitmapLeft, bitmapBottom, bitmapLeft, bitmapTop, paint);
            }
            if (newfraction > 0.25) {
                if (newfraction <= 0.50) {
                    canvas.drawLine(bitmapLeft, bitmapTop,
                            bitmapLeft + (bitmapRight - bitmapLeft) * ((newfraction - 0.25f) / 0.25f),
                            bitmapTop, paint);
                } else {
                    canvas.drawLine(bitmapLeft, bitmapTop, bitmapRight, bitmapTop, paint);
                }
            }
            if (newfraction > 0.50) {
                if (newfraction <= 0.75) {
                    canvas.drawLine(bitmapRight, bitmapTop, bitmapRight,
                            bitmapTop + (bitmapBottom - bitmapTop) * ((newfraction - 0.50f) / 0.25f),
                            paint);
                } else {
                    canvas.drawLine(bitmapRight, bitmapTop, bitmapRight, bitmapBottom, paint);
                }
            }
            if (newfraction > 0.75) {
                if (newfraction <= 1) {
                    canvas.drawLine(bitmapRight, bitmapBottom, bitmapRight - (bitmapRight - bitmapLeft) * ((newfraction - 0.75f) / 0.25f),
                            bitmapBottom, paint);
                } else {
                    canvas.drawLine(bitmapRight, bitmapBottom, bitmapLeft, bitmapBottom, paint);
                }
            }
        }
        canvas.restore();
        canvas.save();
        if (fraction > 0.75) {
            canvas.clipRect(bitmapLeft + (bitmap.getImageInfo().size.width / 2f) * ((1 - fraction) / 0.25f),
                    bitmapTop + (bitmap.getImageInfo().size.height / 2f) * ((1 - fraction) / 0.25f),
                    bitmapRight - (bitmap.getImageInfo().size.width / 2f) * ((1 - fraction) / 0.25f),
                    bitmapBottom - (bitmap.getImageInfo().size.height / 2f) * ((1 - fraction) / 0.25f));
            Matrix matrix = new Matrix();
            matrix.postScale(1.7f, 1.7f, (bitmapLeft + bitmapRight) * 0.5f,
                    (bitmapTop + bitmapBottom) * 0.5f);
            canvas.concat(matrix);
            canvas.drawPixelMapHolder(new PixelMapHolder(bitmap), (bitmapLeft + bitmapRight) / 2 - (bitmap.getImageInfo().size.width >> 1),
                    (bitmapTop + bitmapBottom) / 2 - (bitmap.getImageInfo().size.height >> 1), paint);
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
        RectFloat rectF = new RectFloat((width >> 2) - statement.length(), height * 7 >> 3,
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
