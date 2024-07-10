package google.yousefi.cryptoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // اگر برنامه در حالت DEBUG باشد، ابزار Timber برای لاگ‌ها فعال می‌شود
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree()) // استفاده از DebugTree برای Timber
            Timber.d("Timber DebugTree planted") // نمایش پیام درخت DebugTree که کاشته شده است
        }
    }
}

