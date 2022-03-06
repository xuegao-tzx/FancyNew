package com.xcl.demo;

import com.xcl.fancynew.*;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.utils.Color;
import ohos.global.resource.NotExistException;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.IOException;
import java.io.InputStream;

/**
 * The type Main ability.
 *
 * @author Xcl
 * @version 1.2
 * @package com.xcl.fancynew
 */
public class MainAbility extends Ability implements Component.ClickedListener {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button button1, button2, button3, button4;
        button1 = (Button) findComponentById(ResourceTable.Id_button_normal);
        button2 = (Button) findComponentById(ResourceTable.Id_button_ryb);
        button3 = (Button) findComponentById(ResourceTable.Id_button_line);
        button4 = (Button) findComponentById(ResourceTable.Id_button_rotation);

        button1.setClickedListener(this);
        button2.setClickedListener(this);
        button3.setClickedListener(this);
        button4.setClickedListener(this);
    }

    private PixelMap getPixelMapFromResource(int resourceId) {
        InputStream inputStream = null;
        try {
            // 创建图像数据源ImageSource对象
            inputStream = getContext().getResourceManager().getResource(resourceId);
            ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
            srcOpts.formatHint = "image/png";
            ImageSource imageSource = ImageSource.create(inputStream, srcOpts);
            // 设置图片参数
            ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
            return imageSource.createPixelmap(decodingOptions);
        } catch (IOException | NotExistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(Component component) {
        switch (component.getId()) {
            case ResourceTable.Id_button_normal:
                OpeningStartAnimation openingStartAnimation = new OpeningStartAnimation.Builder(this, ResourceTable.Media_icon, 1)
                        .setDrawStategy(new NormalDrawStrategy())
                        .setAppIcon(getPixelMapFromResource(ResourceTable.Media_icon))
                        .setAppName("测试APP")
                        .setColorOfBackground(Color.rgb(255, 0, 0))
                        .setColorOfAppIcon(Color.rgb(255, 0, 0))
                        .setColorOfAppName(Color.rgb(0, 237, 255))
                        .setColorOfAppStatement(Color.rgb(68, 255, 0))
                        .create();
                openingStartAnimation.show(this);
                break;
            case ResourceTable.Id_button_ryb:
                OpeningStartAnimation openingStartAnimation1 = new OpeningStartAnimation.Builder(this, ResourceTable.Media_icon, 2)
                        .setDrawStategy(new RedYellowBlueDrawStrategy())
                        .setColorOfBackground(Color.BLACK.getValue())
                        .create();
                openingStartAnimation1.show(this);
                break;
            case ResourceTable.Id_button_line:
                OpeningStartAnimation openingStartAnimation2 = new OpeningStartAnimation.Builder(this, ResourceTable.Media_icon, 3)
                        .setDrawStategy(new LineDrawStrategy())
                        .setAppIcon(getPixelMapFromResource(ResourceTable.Media_icon))
                        .create();
                openingStartAnimation2.show(this);
                break;
            case ResourceTable.Id_button_rotation:
                OpeningStartAnimation openingStartAnimation3 = new OpeningStartAnimation.Builder(this, ResourceTable.Media_icon)
                        .setDrawStategy(new RotationDrawStrategy())
                        .create();
                openingStartAnimation3.show(this);
                break;
            default:
                break;
        }
    }
}
