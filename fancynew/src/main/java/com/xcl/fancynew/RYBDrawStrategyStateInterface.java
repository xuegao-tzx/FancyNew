package com.xcl.fancynew;


import ohos.agp.render.Canvas;
import ohos.media.image.PixelMap;

/**
 * The interface Ryb draw strategy state interface.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
interface RYBDrawStrategyStateInterface {

    /**
     * 绘制图标
     *
     * @param canvas               画布
     * @param fraction             完成时间
     * @param drawable             图标素材
     * @param colorOfIcon          绘制颜色
     * @param widthAndHeightOfView view的宽高
     */
    void drawIcon(Canvas canvas, float fraction, PixelMap drawable, int colorOfIcon,
                  WidthAndHeightOfView widthAndHeightOfView);
}
