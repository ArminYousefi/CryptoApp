package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.market.MarketPreferences
import google.yousefi.cryptoapp.data.source.local.preferences.market.MarketPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase برای به دست آوردن preferences بازار (MarketPreferences).
 *
 * @param marketPreferencesRepository ریپازیتوری مربوط به preferences بازار.
 */
class GetMarketPreferencesUseCase @Inject constructor(
    private val marketPreferencesRepository: MarketPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای دریافت preferences بازار اجرا می‌کند.
     *
     * @return یک Flow از نوع MarketPreferences که preferences بازار را نمایش می‌دهد.
     */
    operator fun invoke(): Flow<MarketPreferences> {
        return getMarketPreferences()
    }

    /**
     * یک متد خصوصی برای دریافت preferences بازار از ریپازیتوری مربوطه.
     *
     * @return یک Flow از نوع MarketPreferences که preferences بازار را نمایش می‌دهد.
     */
    private fun getMarketPreferences(): Flow<MarketPreferences> {
        return marketPreferencesRepository.marketPreferencesFlow
    }
}

