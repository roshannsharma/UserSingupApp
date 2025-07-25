# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#########################################
# ViewModel / LiveData (Jetpack)
#########################################
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class * {
    @androidx.lifecycle.* <methods>;
}
-keepclassmembers class * {
    @androidx.annotation.Keep <fields>;
}
-keepclassmembers class * {
    @androidx.annotation.Keep <methods>;
}

#########################################
# Retrofit + Gson
#########################################
-keep class com.google.gson.** { *; }
-keep class com.squareup.retrofit2.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes RuntimeVisibleAnnotations
-keepattributes AnnotationDefault

# Keep model classes used by Gson (annotated with @SerializedName)
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

#########################################
# OkHttp
#########################################
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

#########################################
# Glide
#########################################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.** { *; }
-keep interface com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**

#########################################
# RxJava3
#########################################
-keep class io.reactivex.rxjava3.** { *; }
-dontwarn io.reactivex.rxjava3.**

#########################################
# General AndroidX + Keep ViewModels
#########################################
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keepclassmembers class * {
    @androidx.lifecycle.ViewModel <methods>;
}

#########################################
# Multidex
#########################################
-dontwarn android.support.multidex.**

#########################################
# Optional: Prevent removal of DataBinding-generated classes
#########################################
-keep class **.databinding.*Binding { *; }

