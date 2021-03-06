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
        appName = "??????APP??????";
        appStatement = "???????????????";
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
        appName = "??????APP??????";
        appStatement = "???????????????";
        mDrawable = getPixelMapFromResource(context, iconId);
        addDrawTask(this);
    }

    private PixelMap getPixelMapFromResource(Context context, int resourceId) {
        InputStream inputStream = null;
        try {
            // ?????????????????????ImageSource??????
            inputStream = context.getResourceManager().getResource(resourceId);
            ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
            srcOpts.formatHint = "image/png";
            ImageSource imageSource = ImageSource.create(inputStream, srcOpts);
            // ??????????????????
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
     * ????????????????????????
     *
     * @param fraction ?????????
     */
    private void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        canvas.drawColor(colorOfBackground, Canvas.PorterDuffMode.SRC_OVER); //???????????????
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
     * ????????????
     *
     * @param mactivity ?????????????????????
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
        //??????????????????
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
        //???????????????
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
     * ????????????view
     *
     * @param activity ????????????
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
     * ??????Builder??????????????????
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
         * ???????????????????????????
         *
         * @param animationInterval ????????????
         * @return Builder?????? animation interval
         */
        public Builder setAnimationInterval(long animationInterval) {
            mOpeningStartAnimation.animationInterval = animationInterval;
            return this;
        }

        /**
         * ???????????????????????????
         *
         * @param animationFinishTime ?????????????????????
         * @return Builder?????? animation finish time
         */
        public Builder setAnimationFinishTime(long animationFinishTime) {
            mOpeningStartAnimation.animationFinishTime = animationFinishTime;
            return this;
        }

        /**
         * ??????????????????
         *
         * @param drawable ????????????
         * @return Builder?????? app icon
         */
        public Builder setAppIcon(PixelMap drawable) {
            mOpeningStartAnimation.mDrawable = drawable;
            return this;
        }

        /**
         * ??????????????????????????????
         *
         * @param colorOfAppIcon ????????????
         * @return Builder?????? color of app icon
         */
        public Builder setColorOfAppIcon(int colorOfAppIcon) {
            mOpeningStartAnimation.colorOfAppIcon = colorOfAppIcon;
            return this;
        }

        /**
         * ??????????????????app??????
         *
         * @param appName app??????
         * @return Builder?????? app name
         */
        public Builder setAppName(String appName) {
            mOpeningStartAnimation.appName = appName;
            return this;
        }

        /**
         * ??????app???????????????
         *
         * @param colorOfAppName app????????????
         * @return Builder?????? color of app name
         */
        public Builder setColorOfAppName(int colorOfAppName) {
            mOpeningStartAnimation.colorOfAppName = colorOfAppName;
            return this;
        }

        /**
         * ??????app???????????????
         *
         * @param appStatement app???????????????
         * @return Builder?????? app statement
         */
        public Builder setAppStatement(String appStatement) {
            mOpeningStartAnimation.appStatement = appStatement;
            return this;
        }

        /**
         * ????????????????????????????????????
         *
         * @param colorOfAppStatement ????????????
         * @return Builder?????? color of app statement
         */
        public Builder setColorOfAppStatement(int colorOfAppStatement) {
            mOpeningStartAnimation.colorOfAppStatement = colorOfAppStatement;
            return this;
        }

        /**
         * ??????????????????
         *
         * @param colorOfBackground ?????????????????????int???
         * @return Builder?????? color of background
         */
        public Builder setColorOfBackground(int colorOfBackground) {
            mOpeningStartAnimation.colorOfBackground = colorOfBackground;
            return this;
        }

        /**
         * ???????????????????????????????????????????????????
         *
         * @param drawStrategy ????????????
         * @return Builder?????? draw stategy
         */
        public Builder setDrawStategy(DrawStrategy drawStrategy) {
            mOpeningStartAnimation.mDrawStrategy = drawStrategy;
            mDrawStrategy = drawStrategy;
            return this;
        }

        /**
         * ??????????????????
         *
         * @return ???????????????????????? opening start animation
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
