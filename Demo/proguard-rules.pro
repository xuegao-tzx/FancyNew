-dontwarn
# 代码混淆压缩比，在0~7之间
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 在读取依赖的库文件时，不要略过那些非public类成员
-dontskipnonpubliclibraryclassmembers
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 不做预校验，preverify是proguard的四个步骤之一，去掉这一步能够加快混淆速度。
-dontpreverify
-verbose
# google推荐算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# 保留注解、内部类、泛型、匿名类
-keepattributes *Annotation*,Exceptions,InnerClasses,Signature,EnclosingMethod
# 重命名抛出异常时的文件名称
-renamesourcefileattribute SourceFile
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
-dontwarn javax.annotation.**
# 保留本地native方法不被混淆
-keepclasseswithmembernames,allowshrinking class * {
native <methods>;
}
# 保留枚举类不被混淆
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
# 忽略继承
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 保留HMS相关接入[AnalyticsKit SDK和依赖SDK的混淆配置]
-ignorewarnings
-repackageclasses
-keep class com.huawei.agconnect.**{*;}
-keep class com.huawei.hms.analytics.**{*;}
-keep class com.huawei.hms.push.**{*;}
-keep class com.huawei.hms.**{*;}
# HMS接口服务
-keepattributes Exceptions
-keep interface com.huawei.hms.analytics.type.HAEventType{*;}
-keep interface com.huawei.hms.analytics.type.HAParamType{*;}
-keep class com.huawei.hms.analytics.HiAnalyticsInstance{*;}
-keep class com.huawei.hms.analytics.HiAnalytics{*;}
-keep class com.huawei.hianalytics.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.harmony.**{*;}
-keep class com.huawei.mylibrary.**{*;}
# 保留HarmonyOS应用/服务入口类
-keep public class * extends *.aafwk.ability.Ability
-keep public class * extends *.ace.ability.AceAbility
-keep public class * extends *.aafwk.ability.AbilitySlice
-keep public class * extends *.aafwk.ability.AbilityPackage
-dontwarn java.lang.invoke.**
-dontwarn javax.naming.**
-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions
# 保留配置文件
-printmapping mapping.txt
