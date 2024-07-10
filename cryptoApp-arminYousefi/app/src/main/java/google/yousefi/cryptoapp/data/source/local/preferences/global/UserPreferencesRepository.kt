package google.yousefi.cryptoapp.data.source.local.preferences.global

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * یک کلاس مسئول برای مدیریت تنظیمات ترجیحات کاربر استفاده می‌شود.
 *
 * @property userPreferencesDataStore DataStore است که تنظیمات ترجیحات کاربر را نگهداری می‌کند.
 */
class UserPreferencesRepository @Inject constructor(
    private val userPreferencesDataStore: DataStore<UserPreferences>
) {
    /**
     * یک Flow از تنظیمات ترجیحات کاربر را فراهم می‌کند که به صورت asynchronous تغییرات را پخش می‌کند.
     *
     * @return یک Flow از نوع UserPreferences که شامل تنظیمات فعلی ترجیحات کاربر است.
     */
    val userPreferencesFlow: Flow<UserPreferences> = userPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e("Error reading user preferences", exception)
                emit(UserPreferences())
            } else {
                throw exception
            }
        }

    /**
     * برای به روزرسانی ارز پیش‌فرض کاربر استفاده می‌شود.
     *
     * @param currency ارز جدید که باید به روزرسانی شود.
     * @throws IOException در صورت بروز خطا در خواندن یا نوشتن تنظیمات، این خطا ایجاد می‌شود.
     */
    @Throws(IOException::class)
    suspend fun updateCurrency(currency: Currency) {
        if (currency != userPreferencesFlow.first().currency) {
            userPreferencesDataStore.updateData { currentPreferences ->
                currentPreferences.copy(currency = currency)
            }
        }
    }

    /**
     * برای به روزرسانی صفحه شروع پیش‌فرض کاربر استفاده می‌شود.
     *
     * @param startScreen صفحه شروع جدید که باید به روزرسانی شود.
     * @throws IOException در صورت بروز خطا در خواندن یا نوشتن تنظیمات، این خطا ایجاد می‌شود.
     */
    @Throws(IOException::class)
    suspend fun updateStartScreen(startScreen: StartScreen) {
        if (startScreen != userPreferencesFlow.first().startScreen) {
            userPreferencesDataStore.updateData { currentPreferences ->
                currentPreferences.copy(startScreen = startScreen)
            }
        }
    }
}

