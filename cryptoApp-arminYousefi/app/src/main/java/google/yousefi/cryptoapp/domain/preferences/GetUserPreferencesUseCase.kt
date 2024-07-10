package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferences
import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase برای به دست آوردن preferences کاربر (UserPreferences).
 *
 * @param userPreferencesRepository ریپازیتوری مربوط به preferences کاربر.
 */
class GetUserPreferencesUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای دریافت preferences کاربر اجرا می‌کند.
     *
     * @return یک Flow از نوع UserPreferences که preferences کاربر را نمایش می‌دهد.
     */
    operator fun invoke(): Flow<UserPreferences> {
        return getUserPreferences()
    }

    /**
     * یک متد خصوصی برای دریافت preferences کاربر از ریپازیتوری مربوطه.
     *
     * @return یک Flow از نوع UserPreferences که preferences کاربر را نمایش می‌دهد.
     */
    private fun getUserPreferences(): Flow<UserPreferences> {
        return userPreferencesRepository.userPreferencesFlow
    }
}

