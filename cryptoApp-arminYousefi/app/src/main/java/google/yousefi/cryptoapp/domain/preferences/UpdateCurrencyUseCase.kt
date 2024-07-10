package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferencesRepository
import javax.inject.Inject

/**
 * UseCase برای به روزرسانی واحد پول (currency) در preferences کاربر.
 *
 * @param userPreferencesRepository ریپازیتوری مربوط به preferences کاربر.
 */
class UpdateCurrencyUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای به روزرسانی واحد پول اجرا می‌کند.
     *
     * @param currency واحد پول جدید برای به روزرسانی.
     */
    suspend operator fun invoke(currency: Currency) {
        updateCurrency(currency = currency)
    }

    /**
     * یک متد خصوصی برای به روزرسانی واحد پول در ریپازیتوری مربوطه.
     *
     * @param currency واحد پول جدید برای به روزرسانی.
     */
    private suspend fun updateCurrency(currency: Currency) {
        userPreferencesRepository.updateCurrency(currency = currency)
    }
}

