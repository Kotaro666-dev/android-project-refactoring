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

# 通常ビルド時に発生した以下のエラー対応
# Caused by: java.lang.ClassNotFoundException: Didn't find class "jp.co.yumemi.android.code_check.item" on path: DexPathList[[zip file "/data/app/jp.co.yumemi.android.codecheck.release-jmN5-D-aVQFnwrBlTGNlUg==/base.apk"],nativeLibraryDirectories=[/data/app/jp.co.yumemi.android.codecheck.release-jmN5-D-aVQFnwrBlTGNlUg==/lib/arm64, /system/lib64, /product/lib64]]
# 背景: コードの圧縮時に該当クラスファイルが削除されてしまっていた可能性がある
# 参考資料: https://developer.android.com/studio/build/shrink-code#keep-code

-keep public class jp.co.yumemi.android.code_check.model.GithubRepository