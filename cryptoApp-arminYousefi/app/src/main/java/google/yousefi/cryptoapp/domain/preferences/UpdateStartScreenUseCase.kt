package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.global.StartScreen
import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferencesRepository
import javax.inject.Inject

/**
 * UseCase برای به روزرسانی صفحه شروع در تنظیمات کاربری.
 *
 * @param userPreferencesRepository ریپازیتوری مربوط به تنظیمات کاربری.
 */
class UpdateStartScreenUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای به روزرسانی صفحه شروع در تنظیمات کاربری اجرا می‌کند.
     *
     * @param startScreen نوع صفحه شروع جدید.
     */
    suspend operator fun invoke(startScreen: StartScreen) {
        updateStartScreen(startScreen = startScreen)
    }

    /**
     * یک متد خصوصی برای به روزرسانی صفحه شروع در ریپازیتوری مربوطه.
     *
     * @param startScreen نوع صفحه شروع جدید.
     */
    private suspend fun updateStartScreen(startScreen: StartScreen) {
        userPreferencesRepository.updateStartScreen(startScreen = startScreen)
    }
}
