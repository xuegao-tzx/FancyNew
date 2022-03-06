package com.xcl.fancynew;


import ohos.agp.render.Canvas;
import ohos.media.image.PixelMap;

/**
 * The type Ryb draw strategy state controller.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
class RYBDrawStrategyStateController {

    private final RYBDrawStrategyStateOne mRYBDrawStrategyStateOne = new RYBDrawStrategyStateOne();
    private final RYBDrawStrategyStateTwo mRYBDrawStrategyStateTwo = new RYBDrawStrategyStateTwo();
    private RYBDrawStrategyStateInterface mRYBDrawStrategyStateInterface;

    /**
     * Instantiates a new Ryb draw strategy state controller.
     */
    RYBDrawStrategyStateController() {
    }

    /**
     * 设置状态
     *
     * @param rybDrawStrategyStateInterface 状态接口
     */
    private void setState(RYBDrawStrategyStateInterface rybDrawStrategyStateInterface) {
        mRYBDrawStrategyStateInterface = rybDrawStrategyStateInterface;
    }

    /**
     * 选择合适的状态来执行操作
     *
     * @param canvas               画布
     * @param fraction             完成时间百分比
     * @param icon                 图标
     * @param colorOfIcon          绘制颜色
     * @param widthAndHeightOfView view的宽高
     */
    void choseStateDrawIcon(Canvas canvas, float fraction, PixelMap icon, int colorOfIcon,
                            WidthAndHeightOfView widthAndHeightOfView) {
        if (fraction <= 0.65f) {
            setState(mRYBDrawStrategyStateOne);
        } else if (fraction > 0.65f) {
            setState(mRYBDrawStrategyStateTwo);
        }
        mRYBDrawStrategyStateInterface.drawIcon(canvas, fraction, icon, colorOfIcon, widthAndHeightOfView);
    }

}
