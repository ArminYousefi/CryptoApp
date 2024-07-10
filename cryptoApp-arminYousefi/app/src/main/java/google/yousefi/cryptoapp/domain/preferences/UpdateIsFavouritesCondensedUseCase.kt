package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.favourites.FavouritesPreferencesRepository
import javax.inject.Inject

/**
 * UseCase برای به روزرسانی وضعیت فشرده‌سازی لیست سکه‌های مورد علاقه کاربر.
 *
 * @param favouritesPreferencesRepository ریپازیتوری مربوط به ترجیحات کاربر در مورد سکه‌های مورد علاقه.
 */
class UpdateIsFavouritesCondensedUseCase @Inject constructor(
    private val favouritesPreferencesRepository: FavouritesPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای به روزرسانی وضعیت فشرده‌سازی لیست سکه‌های مورد علاقه اجرا می‌کند.
     *
     * @param isCondensed وضعیت فشرده‌سازی جدید برای لیست سکه‌های مورد علاقه.
     */
    suspend operator fun invoke(isCondensed: Boolean) {
        updateIsFavouritesCondensed(isCondensed = isCondensed)
    }

    /**
     * یک متد خصوصی برای به روزرسانی وضعیت فشرده‌سازی در ریپازیتوری مربوطه.
     *
     * @param isCondensed وضعیت فشرده‌سازی جدید برای لیست سکه‌های مورد علاقه.
     */
    private suspend fun updateIsFavouritesCondensed(isCondensed: Boolean) {
        favouritesPreferencesRepository.updateIsFavouritesCondensed(isCondensed = isCondensed)
    }
}

