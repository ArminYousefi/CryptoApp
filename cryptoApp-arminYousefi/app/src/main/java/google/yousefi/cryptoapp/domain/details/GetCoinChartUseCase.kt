package google.yousefi.cryptoapp.domain.details

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferencesRepository
import google.yousefi.cryptoapp.data.repository.chart.CoinChartRepository
import google.yousefi.cryptoapp.model.CoinChart
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

/**
 * UseCase برای دریافت نمودار قیمت سکه.
 *
 * @param coinChartRepository ریپازیتوری برای دسترسی به داده‌های نمودار سکه.
 * @param userPreferencesRepository ریپازیتوری برای دسترسی به ترجیحات کاربر.
 */
class GetCoinChartUseCase @Inject constructor(
    private val coinChartRepository: CoinChartRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را اجرا می‌کند.
     *
     * @param coinId شناسه سکه.
     * @param chartPeriod دوره زمانی نمودار.
     * @return جریان از نوع Result که نمودار سکه را در بر دارد.
     */
    operator fun invoke(coinId: String, chartPeriod: String): Flow<Result<CoinChart>> {
        return getCoinChart(coinId = coinId, chartPeriod = chartPeriod)
    }

    /**
     * دریافت نمودار سکه با توجه به ترجیحات کاربر.
     *
     * @param coinId شناسه سکه.
     * @param chartPeriod دوره زمانی نمودار.
     * @return جریان از نوع Result که نمودار سکه را در بر دارد.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCoinChart(coinId: String, chartPeriod: String): Flow<Result<CoinChart>> {
        return userPreferencesRepository.userPreferencesFlow.flatMapLatest { userPreferences ->
            coinChartRepository.getCoinChart(
                coinId = coinId,
                chartPeriod = chartPeriod,
                currency = userPreferences.currency
            )
        }
    }
}
