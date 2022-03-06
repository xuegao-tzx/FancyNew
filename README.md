# FancyNew

**本项目是基于开源项目FancyView进行ohos化的移植和改进的，可以通过Github地址（ https://github.com/wongzy/FancyView ）追踪到原项目版本**

#### 项目介绍

- 项目名称：FancyNew
- 所属系列：ohos的第三方组件适配移植
- 功能：应用打开动画
- 项目移植状态：完成
- 调用差异：新增、更改部分语法，详见下方介绍
- 项目作者和维护人：田梓萱
- 联系方式：xcl@xuegao-tzx.top
- 原项目Doc地址：https://github.com/wongzy/FancyView
- 原项目基线版本：无release版本
- 编程语言：Java
- 外部库依赖：无

#### 功能介绍与差异：

  1. 支持自动根据设备状态改变背景颜色[默认]
  2. 支持用户自定义显示数据项[全显示(默认)、只显示图标、只显示图标和APP名字]
  3. 兼容API5和6，支持开箱即用
  4. 由于鸿蒙不提供获取APP名字途径，故不显示APP包名，需要手动调用进行设置，详见Demo示例
  5. 设置动画图标有2种写法，详见Demo示例


#### 安装教程

##### 方案一：

  1. 下载依赖库 FancyNew.har。
  2. 启动 DevEco Studio，将编译的har包，导入工程目录“entry->libs”下。
  3. 在moudle级别下的build.gradle文件中添加依赖，在dependences标签中增加对libs目录下har包的引用。
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar', '*.har'])
	……
}
```
  4.在导入的har包上点击右键，选择“Add as Library”对包进行引用，选择需要引用的模块，并点击“OK”即引用成功。

##### 方案二：
  1. 克隆本仓库到本地
  2. 启动 DevEco Studio，左上角点击：文件-新建-导入模块
  3. 在应用模块的build.gradle的dependencies闭包中，添加如下代码:
```
 dependencies {
     implementation project(':fancynew')
 }
```

#### 使用说明
 1、使用方法
```java
OpeningStartAnimation openingStartAnimation =  new OpeningStartAnimation.Builder(this,ResourceTable.Media_icon,1)//设置图标`ResourceTable.Media_icon`以及要显示的模块数量`1`[模块数量可不填(默认全部显示)]
		.setDrawStategy(new NormalDrawStrategy())//设置动画效果
		.setAppIcon(getPixelMapFromResource(ResourceTable.Media_icon))//设置动画图标
		.setAppName("测试APP")//设置APP名字
		.setColorOfBackground(Color.rgb(255,0,0))//设置背景颜色
		.setColorOfAppIcon(Color.rgb(255,0,0))//设置图标圈线的颜色
		.setColorOfAppName(Color.rgb(0,237,255))//设置APP名字颜色
		.setColorOfAppStatement(Color.rgb(68,255,0))//设置下方一句话的颜色
		.create();
		openingStartAnimation.show(this);
```
 2、除此之外，还可以设置文字、图标、一句话描述、动画时间等等，也可以自定义应用打开动画，开放了策略接口，像这样。
```java
OpeningStartAnimation openingStartAnimation =  new OpeningStartAnimation.Builder(this,ResourceTable.Media_icon,1)
		.setDrawStategy(new DrawStrategy() {
			@Override
			public void drawAppName(Canvas canvas, float fraction, String name, int colorOfAppName, WidthAndHeightOfView widthAndHeightOfView) {

			}

			@Override
			public void drawAppIcon(Canvas canvas, float fraction, PixelMap icon, int colorOfIcon, WidthAndHeightOfView widthAndHeightOfView) {

			}

			@Override
			public void drawAppStatement(Canvas canvas, float fraction, String statement, int colorOfStatement, WidthAndHeightOfView widthAndHeightOfView) {

			}
		})
		.create();
```

#### 混淆相关
  1. fancynew的包不建议单独混淆，如确实需要混淆请务必保留类名，加入代码如下:
```json
-dontwarn com.xcl.fancynew.**
-keep class com.xcl.fancynew.** { *; }
```
  2. 整体项目中混淆无要求，你随意

#### 版权和许可信息
- Apache Licence
