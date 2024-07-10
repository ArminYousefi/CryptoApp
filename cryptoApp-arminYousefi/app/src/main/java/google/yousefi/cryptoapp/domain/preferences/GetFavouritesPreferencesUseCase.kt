package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.favourites.FavouritesPreferences
import google.yousefi.cryptoapp.data.source.local.preferences.favourites.FavouritesPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase برای به دست آوردن preferences مورد علاقه (FavouritesPreferences).
 *
 * @param favouritesPreferencesRepository ریپازیتوری مربوط به preferences مورد علاقه.
 */
class GetFavouritesPreferencesUseCase @Inject constructor(
    private val favouritesPreferencesRepository: FavouritesPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای دریافت preferences مورد علاقه اجرا می‌کند.
     *
     * @return یک Flow از نوع FavouritesPreferences که preferences مورد علاقه را نمایش می‌دهد.
     */
    operator fun invoke(): Flow<FavouritesPreferences> {
        return getFavouritesPreferences()
    }

    /**
     * یک متد خصوصی برای دریافت preferences مورد علاقه از ریپازیتوری مربوطه.
     *
     * @return یک Flow از نوع FavouritesPreferences که preferences مورد علاقه را نمایش می‌دهد.
     */
    private fun getFavouritesPreferences(): Flow<FavouritesPreferences> {
        return favouritesPreferencesRepository.favouritesPreferencesFlow
    }
}
