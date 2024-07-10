-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# نگه داشتن مسیر های مربوط به retrofit
-keepclasseswithmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# نگه داری typeClass ها و موارد مربوط به gson
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken

# نگه داری enum های SharedPreferences
-keep public enum google.yousefi.cryptoapp.data.source.local.preferences.**{
    *;
}

# تبدیل از   ImmutableList<BigDecimal> به ImmutableList<Double>.
-keep,allowobfuscation,allowshrinking class kotlinx.collections.immutable.ImmutableList
