# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

##-keep public class sun.nio.ch.**
#-dontwarn sun.nio.ch.**
##
##-keep public class java.beans.**
#-dontwarn java.beans.**
##
##-keep public class sun.misc.**
#-dontwarn sun.misc.**


-keep class com.esotericsoftware.kryo.io.* { public *; }
-dontwarn com.esotericsoftware.kryo.io.**
-keep class com.esotericsoftware.kryo.serializers.* { public *; }
-dontwarn com.esotericsoftware.kryo.serializers.**
-keep class com.esotericsoftware.kryo.util.* { public *; }
-dontwarn com.esotericsoftware.kryo.util.**
-keep class org.objenesis.instantiator.sun.* { public *; }
-dontwarn org.objenesis.instantiator.sun.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn okio.**

-dontwarn org.jetbrains.kotlin.**
-dontwarn org.jetbrains.anko.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.**
-dontwarn com.fasterxml.**
-dontwarn io.sneakspeak.**
-dontwarn com.google.**

-keepnames class io.sneakspeak.**
-keepclassmembernames class io.sneakspeak.**




##---------------Begin: proguard configuration common for all Android apps ----------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService


# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}


-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}


-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}


# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keep public class * {
    public protected *;
}


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature


# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }


# NomnomApplication classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }


##---------------End: proguard configuration for Gson  ----------
