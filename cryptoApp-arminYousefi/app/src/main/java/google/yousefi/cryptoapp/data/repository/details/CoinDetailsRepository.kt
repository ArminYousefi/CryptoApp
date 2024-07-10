package google.yousefi.cryptoapp.data.repository.details

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.model.CoinDetails
import kotlinx.coroutines.flow.Flow

/**
 * رابط CoinDetailsRepository برای ارتباط با منبع داده و دریافت جزئیات سکه مورد نظر استفاده می‌شود.
 */
interface CoinDetailsRepository {

    /**
     * متدی برای دریافت جزئیات سکه با استفاده از شناسه سکه و واحد پولی.
     *
     * @param coinId شناسه سکه برای دریافت جزئیات.
     * @param currency واحد پولی که برای نمایش قیمت‌ها استفاده می‌شود.
     * @return Flow<Result<CoinDetails>> جریانی که نتیجه دریافت جزئیات سکه را ارائه می‌دهد.
     */
    fun getCoinDetails(coinId: String, currency: Currency): Flow<Result<CoinDetails>>
}
