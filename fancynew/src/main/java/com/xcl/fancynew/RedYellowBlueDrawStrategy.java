package com.xcl.fancynew;


import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.agp.utils.TextAlignment;
import ohos.media.image.PixelMap;

/**
 * The type Red yellow blue draw strategy.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
public class RedYellowBlueDrawStrategy implements DrawStrategy {
    private final RYBDrawStrategyStateController mRYBDrawStrategyStateController;

    /**
     * Instantiates a new Red yellow blue draw strategy.
     */
    public RedYellowBlueDrawStrategy() {
        mRYBDrawStrategyStateController = new RYBDrawStrategyStateController();
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
    public void drawAppIcon(Canvas canvas, float fraction, PixelMap icon, int colorOfIcon,
                            WidthAndHeightOfView widthAndHeightOfView) {
        mRYBDrawStrategyStateController.choseStateDrawIcon(canvas, fraction, icon, colorOfIcon, widthAndHeightOfView);
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
