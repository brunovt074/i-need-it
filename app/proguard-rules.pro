-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes InnerClasses

# kotlinx-serialization
-keepclassmembers class kotlinx.serialization.json.** { *** *; }
-keepclasseswithmembers class **$$serializer { CREATOR <fields>; }
-keep,includedescriptorclasses class com.brunovt.ineedit.**$$serializer { *; }
-keepclassmembers @kotlinx.serialization.Serializable class com.brunovt.ineedit.** {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Room — consumer rules are included automatically; keep entity fields
-keep class com.brunovt.ineedit.data.local.** { *; }

# Hilt — consumer rules included automatically via hilt-android

# Compose runtime (consumer rules included via compose-runtime)
-keep class androidx.compose.** { *; }

# kotlinx-datetime
-dontwarn kotlinx.datetime.**

# kotlinx-collections-immutable
-keep class kotlinx.collections.immutable.** { *; }
-dontwarn kotlinx.collections.immutable.**
