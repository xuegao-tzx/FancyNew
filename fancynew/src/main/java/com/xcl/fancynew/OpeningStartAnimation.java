package com.xcl.fancynew;

import ohos.aafwk.ability.Ability;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.Component;
import ohos.agp.components.StackLayout;
import ohos.agp.render.Canvas;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.global.configuration.Configuration;
import ohos.global.resource.NotExistException;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Timer;
import java.util.TimerTask;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_PARENT;

/**
 * The type Opening start animation.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
public class OpeningStartAnimation extends Component implements Component.DrawTask {
    private static final int FINISHANIMATION = 1;
    private final WidthAndHeightOfView mWidthAndHeightOfView;
    /**
     * The Device.
     */
    int device = 0;
    private long animationInterval = 1500;
    private long animationFinishTime = 350;
    private int colorOfBackground = 0;
    private float fraction;
    private PixelMap mDrawable = null;
    private int colorOfAppIcon = Color.getIntColor("#FF4BF3E2");
    private String appName = "";
    private int colorOfAppName = Color.getIntColor("#FF86AEAA");
    private String appStatement = "";
    private int colorOfAppStatement = Color.getIntColor("#FF8E6FFD");
    private DelegateRecycleView mDelegateRecycleView;
    private DrawStrategy mDrawStrategy = new NormalDrawStrategy();

    {
        StackLayout.LayoutConfig layoutParams;
        layoutParams = new StackLayout.LayoutConfig(MATCH_PARENT,
                MATCH_PARENT);
        this.setLayoutConfig(layoutParams);
        mWidthAndHeightOfView = new WidthAndHeightOfView();
    }

    private OpeningStartAnimation(Context context, int iconId, int dev) {
        super(context);
        int colorMode = new Configuration().getSystemColorMode();
        if (colorMode == Configuration.DARK_MODE) {
            colorOfBackground = Color.BLACK.getValue();
        } else {
            colorOfBackground = Color.WHITE.getValue();
        }
        device = dev;
        appName = "示例APP名字";
        appStatement = "示例一句话";
        mDrawable = getPixelMapFromResource(context, iconId);
        addDrawTask(this);
    }

    private OpeningStartAnimation(Context context, int iconId) {
        super(context);
        int colorMode = new Configuration().getSystemColorMode();
        if (colorMode == Configuration.DARK_MODE) {
            colorOfBackground = Color.BLACK.getValue();
        } else {
            colorOfBackground = Color.WHITE.getValue();
        }
        device = 0;
        appName = "示例APP名字";
        appStatement = "示例一句话";
        mDrawable = getPixelMapFromResource(context, iconId);
        addDrawTask(this);
    }

    private PixelMap getPixelMapFromResource(Context context, int resourceId) {
        InputStream inputStream = null;
        try {
            // 创建图像数据源ImageSource对象
            inputStream = context.getResourceManager().getResource(resourceId);
            ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
            srcOpts.formatHint = "image/png";
            ImageSource imageSource = ImageSource.create(inputStream, srcOpts);
            // 设置图片参数
            ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
            return imageSource.createPixelmap(decodingOptions);
        } catch (IOException | NotExistException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 设置完成的百分比
     *
     * @param fraction 百分比
     */
    private void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        canvas.drawColor(colorOfBackground, Canvas.PorterDuffMode.SRC_OVER); //绘制背景色
        mWidthAndHeightOfView.setHeight(getHeight());
        mWidthAndHeightOfView.setWidth(getWidth());
        if (device == 0 || device == 3) {
            mDrawStrategy.drawAppIcon(canvas, fraction, mDrawable, colorOfAppIcon,
                    mWidthAndHeightOfView);
            mDrawStrategy.drawAppName(canvas, fraction, appName, colorOfAppName,
                    mWidthAndHeightOfView);
            mDrawStrategy.drawAppStatement(canvas, fraction, appStatement, colorOfAppStatement,
                    mWidthAndHeightOfView);
        } else if (device == 2) {
            mDrawStrategy.drawAppIcon(canvas, fraction, mDrawable, colorOfAppIcon,
                    mWidthAndHeightOfView);
            mDrawStrategy.drawAppName(canvas, fraction, appName, colorOfAppName,
                    mWidthAndHeightOfView);
        } else if (device == 1) {
            mDrawStrategy.drawAppIcon(canvas, fraction, mDrawable, colorOfAppIcon,
                    mWidthAndHeightOfView);
        }

    }

    /**
     * 显示动画
     *
     * @param mactivity 显示动画的界面
     */
    public void show(Ability mactivity) {
        SoftReference<Ability> softReference = new SoftReference<>(mactivity);
        final Ability activity = softReference.get();
        CommonDialog dialog = new CommonDialog(activity.getContext());
        dialog.setContentCustomComponent(this);
        dialog.show();
        AnimatorValue animatorValue = new AnimatorValue();
        animatorValue.setDuration(animationInterval - 50);
        animatorValue.setDelay(100);
        animatorValue.setLoopedCount(0);
        animatorValue.setCurveType(Animator.CurveType.LINEAR);
        animatorValue.setValueUpdateListener((animatorValue1, value) -> {
            setFraction(value);
        });
        animatorValue.start();
        //处理动画定时
        EventHandler handler = new EventHandler(EventRunner.getMainEventRunner()) {
            @Override
            protected void processEvent(InnerEvent event) {
                super.processEvent(event);
                if (event.eventId == FINISHANIMATION) {
                    moveAnimation(activity);
                    dialog.hide();
                }
            }
        };
        //动画定时器
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InnerEvent message = InnerEvent.get();
                message.eventId = FINISHANIMATION;
                handler.sendEvent(message);
            }
        }, animationInterval + 400);
    }

    /**
     * 隐藏动画view
     *
     * @param activity 当前活动
     */
    private void moveAnimation(Ability activity) {
        this.createAnimatorProperty()
                .scaleX(0)
                .scaleY(0)
                .alpha(0)
                .setDuration(animationFinishTime);
        mDelegateRecycleView.finishAnimation();
    }

    /**
     * 使用Builder模式创建对象
     */
    public static final class Builder implements DelegateRecycleView {
        /**
         * The constant mDrawStrategy.
         */
        public static DrawStrategy mDrawStrategy;
        /**
         * The M opening start animation.
         */
        OpeningStartAnimation mOpeningStartAnimation;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         * @param iconId  the icon id
         * @param dev     the dev
         */
        public Builder(Context context, int iconId, int dev) {
            mOpeningStartAnimation = new OpeningStartAnimation(context, iconId, dev);
            mOpeningStartAnimation.mDelegateRecycleView = this;
        }

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         * @param iconId  the icon id
         */
        public Builder(Context context, int iconId) {
            mOpeningStartAnimation = new OpeningStartAnimation(context, iconId);
            mOpeningStartAnimation.mDelegateRecycleView = this;
        }


        /**
         * 设置动画时间的方法
         *
         * @param animationInterval 动画时间
         * @return Builder对象 animation interval
         */
        public Builder setAnimationInterval(long animationInterval) {
            mOpeningStartAnimation.animationInterval = animationInterval;
            return this;
        }

        /**
         * 设置动画消失的时间
         *
         * @param animationFinishTime 动画消失的时间
         * @return Builder对象 animation finish time
         */
        public Builder setAnimationFinishTime(long animationFinishTime) {
            mOpeningStartAnimation.animationFinishTime = animationFinishTime;
            return this;
        }

        /**
         * 设置动画图标
         *
         * @param drawable 动画图标
         * @return Builder对象 app icon
         */
        public Builder setAppIcon(PixelMap drawable) {
            mOpeningStartAnimation.mDrawable = drawable;
            return this;
        }

        /**
         * 设置图标绘制辅助颜色
         *
         * @param colorOfAppIcon 辅助颜色
         * @return Builder对象 color of app icon
         */
        public Builder setColorOfAppIcon(int colorOfAppIcon) {
            mOpeningStartAnimation.colorOfAppIcon = colorOfAppIcon;
            return this;
        }

        /**
         * 设置要绘制的app名称
         *
         * @param appName app名称
         * @return Builder对象 app name
         */
        public Builder setAppName(String appName) {
            mOpeningStartAnimation.appName = appName;
            return this;
        }

        /**
         * 设置app名称的颜色
         *
         * @param colorOfAppName app名称颜色
         * @return Builder对象 color of app name
         */
        public Builder setColorOfAppName(int colorOfAppName) {
            mOpeningStartAnimation.colorOfAppName = colorOfAppName;
            return this;
        }

        /**
         * 设置app一句话描述
         *
         * @param appStatement app一句话描述
         * @return Builder对象 app statement
         */
        public Builder setAppStatement(String appStatement) {
            mOpeningStartAnimation.appStatement = appStatement;
            return this;
        }

        /**
         * 设置一句话描述的字体颜色
         *
         * @param colorOfAppStatement 字体颜色
         * @return Builder对象 color of app statement
         */
        public Builder setColorOfAppStatement(int colorOfAppStatement) {
            mOpeningStartAnimation.colorOfAppStatement = colorOfAppStatement;
            return this;
        }

        /**
         * 设置背景颜色
         *
         * @param colorOfBackground 背景颜色对应的int值
         * @return Builder对象 color of background
         */
        public Builder setColorOfBackground(int colorOfBackground) {
            mOpeningStartAnimation.colorOfBackground = colorOfBackground;
            return this;
        }

        /**
         * 开放绘制策略接口，可由用户自行定义
         *
         * @param drawStrategy 绘制接口
         * @return Builder对象 draw stategy
         */
        public Builder setDrawStategy(DrawStrategy drawStrategy) {
            mOpeningStartAnimation.mDrawStrategy = drawStrategy;
            mDrawStrategy = drawStrategy;
            return this;
        }

        /**
         * 创建开屏动画
         *
         * @return 创建出的开屏动画 opening start animation
         */
        public OpeningStartAnimation create() {
            return mOpeningStartAnimation;
        }

        @Override
        public void finishAnimation() {
            mOpeningStartAnimation = null;
        }
    }
}
