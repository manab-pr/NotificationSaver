# NotificationSaver ProGuard Configuration
-keep class com.mpm.notificationsaver.** { *; }

# Keep Room database classes
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Keep Compose classes
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep notification listener service
-keep class * extends android.service.notification.NotificationListenerService
-keep class com.mpm.notificationsaver.NotificationListener { *; }

# Security: Remove debugging information
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Kotlin specific
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**

# Remove source file attribute for security
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable