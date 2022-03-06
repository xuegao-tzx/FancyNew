package com.xcl.fancynew;

import ohos.agp.render.*;
import ohos.agp.utils.Color;
import ohos.agp.utils.Matrix;
import ohos.agp.utils.RectFloat;
import ohos.agp.utils.TextAlignment;
import ohos.media.image.PixelMap;

/**
 * The type Rotation draw strategy.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
public class RotationDrawStrategy implements DrawStrategy {
    /**
     * Instantiates a new Rotation draw strategy.
     */
    public RotationDrawStrategy() {
    }

    @Override
    public void drawAppName(Canvas canvas, float fraction, String name, int colorOfAppName,
                            WidthAndHeightOfView widthAndHeightOfView) {
        canvas.save();
        int width = widthAndHeightOfView.getWidth();
        int height = widthAndHeightOfView.getHeight();
        canvas.clipRect((width >> 1) - 150, (height >> 1) - 80,
                (width >> 1) + 150 * fraction, (height >> 1) + 65 * fraction);
        Paint paint = new Paint();
        paint.setColor(new Color(colorOfAppName));
        paint.setTextAlign(TextAlignment.CENTER);
        paint.setTextSize(50);
        canvas.drawText(paint, name, width >> 1, (height >> 1) + 50);
        canvas.restore();
    }

    @Override
    public void drawAppIcon(Canvas canvas, float fraction, PixelMap icon, int colorOfIcon,
                            WidthAndHeightOfView widthAndHeightOfView) {
        drawIcon(canvas, (int) (fraction * 540), icon,
                widthAndHeightOfView.getWidth(), widthAndHeightOfView.getHeight());
    }

    @Override
    public void drawAppStatement(Canvas canvas, float fraction, String statement,
                                 int colorOfStatement, WidthAndHeightOfView widthAndHeightOfView) {
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

    /**
     * 根据角度绘制图标的类
     *
     * @param canvas 画布
     * @param degree 角度
     * @param icon   图标
     * @param width  view宽度
     * @param height view高度
     */
    private void drawIcon(Canvas canvas, int degree, PixelMap icon, int width, int height) {
        if (degree <= 180) {
            drawIconOne(canvas, degree / 180f, icon, width, height);
        }
        if (degree > 180 && degree <= 360) {
            drawIconTwo(canvas, degree - 180, icon, width, height);
        }
        if (degree > 360 && degree <= 540) {
            drawIconThree(canvas, degree - 360, icon, width, height);
        }
    }

    /**
     * 根据角度绘制图标的类
     *
     * @param canvas   画布
     * @param fraction 完成时间百分比
     * @param icon     图标
     * @param width    view宽度
     * @param height   view高度
     */
    private void drawIconOne(Canvas canvas, float fraction, PixelMap icon, int width, int height) {
        ThreeDimView camera = new ThreeDimView();
        Paint paint = new Paint();
        canvas.save();
        int centerX = width / 2;
        int centerY = height / 2 - 200;
        int x = centerX - icon.getImageInfo().size.width / 2;
        int y = centerY - icon.getImageInfo().size.height / 2;
        Matrix matrix = new Matrix();
        matrix.postScale(1.7f, 1.7f, centerX, centerY);
        canvas.concat(matrix);
        canvas.clipRect(x, y, x + icon.getImageInfo().size.width * fraction * 0.5f, y + icon.getImageInfo().size.height * fraction * 0.5f);
        canvas.translate(centerX, centerY);
        camera.rotateX(180);
        camera.rotateY(-180);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        canvas.drawPixelMapHolder(new PixelMapHolder(icon), x, y, paint);
        canvas.restore();
    }

    /**
     * 根据角度绘制图标的类
     *
     * @param canvas 画布
     * @param degree 角度
     * @param icon   图标
     * @param width  view宽度
     * @param height view高度
     */
    private void drawIconTwo(Canvas canvas, int degree, PixelMap icon, int width, int height) {
        ThreeDimView camera = new ThreeDimView();
        Paint paint = new Paint();
        int centerX = width / 2;
        int centerY = height / 2 - 200;
        int x = centerX - icon.getImageInfo().size.width / 2;
        int y = centerY - icon.getImageInfo().size.height / 2;
        Matrix matrix = new Matrix();
        matrix.postScale(1.7f, 1.7f, centerX, centerY);
        //绘制左半部分
        canvas.save();
        canvas.concat(matrix);
        canvas.clipRect(x, y, x + icon.getImageInfo().size.width / 2, y + (icon.getImageInfo().size.height >> 1));
        canvas.translate(centerX, centerY);
        camera.rotateX(180);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        canvas.drawPixelMapHolder(new PixelMapHolder(icon), x, y, paint);
        canvas.restore();

        //绘制右半部分
        canvas.save();
        canvas.concat(matrix);
        if (degree <= 90)
            canvas.clipRect(x, y, x + icon.getImageInfo().size.width / 2, y + (icon.getImageInfo().size.height >> 1));
        else
            canvas.clipRect(x + icon.getImageInfo().size.width / 2, y, x + icon.getImageInfo().size.width, y + icon.getImageInfo().size.height / 2);
        canvas.translate(centerX, centerY);
        camera.rotateX(180);
        camera.rotateY(180 - degree);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        canvas.drawPixelMapHolder(new PixelMapHolder(icon), x, y, paint);
        canvas.restore();
    }

    /**
     * 根据角度绘制图标的类
     *
     * @param canvas 画布
     * @param degree 角度
     * @param icon   图标
     * @param width  view宽度
     * @param height view高度
     */
    private void drawIconThree(Canvas canvas, int degree, PixelMap icon, int width, int height) {
        ThreeDimView camera = new ThreeDimView();
        Paint paint = new Paint();
        int centerX = width / 2;
        int centerY = height / 2 - 200;
        int x = centerX - icon.getImageInfo().size.width / 2;
        int y = centerY - icon.getImageInfo().size.height / 2;
        Matrix matrix = new Matrix();
        matrix.postScale(1.7f, 1.7f, centerX, centerY);
        //绘制上半部分
        canvas.save();
        canvas.concat(matrix);
        canvas.clipRect(x, y, x + icon.getImageInfo().size.width, y + (icon.getImageInfo().size.height >> 1));
        canvas.drawPixelMapHolder(new PixelMapHolder(icon), x, y, paint);
        canvas.restore();

        //绘制下半部分
        canvas.save();
        canvas.concat(matrix);
        if (degree <= 90)
            canvas.clipRect(x, y, x + icon.getImageInfo().size.width, y + (icon.getImageInfo().size.height >> 1));
        else
            canvas.clipRect(x, y + icon.getImageInfo().size.height / 2, x + icon.getImageInfo().size.width, y + icon.getImageInfo().size.height);
        canvas.translate(centerX, centerY);
        camera.rotateX(180 - degree);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        canvas.drawPixelMapHolder(new PixelMapHolder(icon), x, y, paint);
        canvas.restore();
    }
}
