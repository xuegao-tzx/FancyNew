package com.xcl.fancynew;


import ohos.agp.render.Canvas;
import ohos.media.image.PixelMap;

/**
 * The interface Draw strategy.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
public interface DrawStrategy {

    /**
     * 绘制app名称文字
     *
     * @param canvas               画布
     * @param fraction             完成时间百分比
     * @param name                 文字
     * @param colorOfAppName       字体颜色
     * @param widthAndHeightOfView view的宽和高
     */
    void drawAppName(Canvas canvas, float fraction, String name, int colorOfAppName,
                     WidthAndHeightOfView widthAndHeightOfView);

    /**
     * 绘制app图标
     *
     * @param canvas               画布
     * @param fraction             完成时间百分比
     * @param icon                 图标
     * @param colorOfIcon          绘制图标颜色
     * @param widthAndHeightOfView view的宽和高
     */
    void drawAppIcon(Canvas canvas, float fraction, PixelMap icon, int colorOfIcon,
                     WidthAndHeightOfView widthAndHeightOfView);

    /**
     * 绘制app一句话描述
     *
     * @param canvas               画布
     * @param fraction             完成时间百分比
     * @param statement            一句话描述
     * @param colorOfStatement     字体颜色
     * @param widthAndHeightOfView view的宽和高
     */
    void drawAppStatement(Canvas canvas, float fraction, String statement, int colorOfStatement,
                          WidthAndHeightOfView widthAndHeightOfView);
}
