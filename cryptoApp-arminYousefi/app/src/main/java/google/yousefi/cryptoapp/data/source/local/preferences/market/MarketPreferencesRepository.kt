package google.yousefi.cryptoapp.data.source.local.preferences.market

import androidx.datastore.core.DataStore
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * یک کلاس مسئول برای مدیریت تنظیمات بازار استفاده می‌شود.
 *
 * @property marketPreferencesDataStore DataStore است که تنظیمات بازار را نگهداری می‌کند.
 */
class MarketPreferencesRepository @Inject constructor(
    private val marketPreferencesDataStore: DataStore<MarketPreferences>
) {
    /**
     * یک Flow از تنظیمات بازار را فراهم می‌کند که به صورت asynchronous تغییرات را پخش می‌کند.
     *
     * @return یک Flow از نوع MarketPreferences که شامل تنظیمات فعلی بازار است.
     */
    val marketPreferencesFlow: Flow<MarketPreferences> = marketPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading market preferences", exception)
                emit(MarketPreferences())
            } else {
                throw exception
            }
        }

    /**
     * برای به روزرسانی نوع مرتب‌سازی ارزها در تنظیمات بازار استفاده می‌شود.
     *
     * @param coinSort نوع جدید مرتب‌سازی ارزها که باید به روزرسانی شود.
     * @throws IOException در صورت بروز خطا در خواندن یا نوشتن تنظیمات، این خطا ایجاد می‌شود.
     */
    @Throws(IOException::class)
    suspend fun updateCoinSort(coinSort: CoinSort) {
        if (coinSort != marketPreferencesFlow.first().coinSort) {
            marketPreferencesDataStore.updateData { currentPreferences ->
                currentPreferences.copy(coinSort = coinSort)
            }
        }
    }
}
