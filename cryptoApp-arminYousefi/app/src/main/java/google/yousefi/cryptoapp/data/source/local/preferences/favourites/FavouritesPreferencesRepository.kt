package google.yousefi.cryptoapp.data.source.local.preferences.favourites

import androidx.datastore.core.DataStore
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * یک کلاس مسئول برای مدیریت تنظیمات مربوط به لیست علاقه‌مندی‌ها استفاده می‌شود.
 *
 * @property favouritesPreferencesDataStore DataStore است که تنظیمات لیست علاقه‌مندی‌ها را نگهداری می‌کند.
 */
class FavouritesPreferencesRepository @Inject constructor(
    private val favouritesPreferencesDataStore: DataStore<FavouritesPreferences>
) {
    /**
     * یک Flow از تنظیمات لیست علاقه‌مندی‌ها را فراهم می‌کند که به صورت asynchronous تغییرات را پخش می‌کند.
     *
     * @return یک Flow از نوع FavouritesPreferences که شامل تنظیمات فعلی لیست علاقه‌مندی‌ها است.
     */
    val favouritesPreferencesFlow: Flow<FavouritesPreferences> = favouritesPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e("Error reading favourites preferences", exception)
                emit(FavouritesPreferences())
            } else {
                throw exception
            }
        }

    /**
     * برای به روزرسانی وضعیت فشرده‌سازی لیست علاقه‌مندی‌ها استفاده می‌شود.
     *
     * @param isCondensed وضعیت جدید فشرده‌سازی که باید به روزرسانی شود.
     */
    suspend fun updateIsFavouritesCondensed(isCondensed: Boolean) {
        if (isCondensed != favouritesPreferencesFlow.first().isFavouritesCondensed) {
            favouritesPreferencesDataStore.updateData { currentPreferences ->
                currentPreferences.copy(isFavouritesCondensed = isCondensed)
            }
        }
    }

    /**
     * برای به روزرسانی نوع مرتب‌سازی ارزهای مورد علاقه استفاده می‌شود.
     *
     * @param coinSort نوع جدید مرتب‌سازی ارزهای مورد علاقه که باید به روزرسانی شود.
     */
    suspend fun updateCoinSort(coinSort: CoinSort) {
        if (coinSort != favouritesPreferencesFlow.first().coinSort) {
            favouritesPreferencesDataStore.updateData { currentPreferences ->
                currentPreferences.copy(coinSort = coinSort)
            }
        }
    }
}
