package google.yousefi.cryptoapp.data.repository.chart

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.mapper.CoinChartMapper
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSource
import google.yousefi.cryptoapp.di.IoDispatcher
import google.yousefi.cryptoapp.model.CoinChart
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

/**
 * کلاس CoinChartRepositoryImpl برای اجرای رابط CoinChartRepository و ارتباط با منبع داده شبکه سکه‌ها استفاده می‌شود.
 *
 * @property coinNetworkDataSource منبع داده شبکه برای دریافت اطلاعات نمودار سکه از API.
 * @property coinChartMapper Mapper برای تبدیل مدل API به مدل داخلی CoinChart.
 * @property ioDispatcher Dispatcher برای اجرای کدهای مربوط به شبکه در یک thread جداگانه.
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinChartRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinChartMapper: CoinChartMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoinChartRepository {

    /**
     * متدی برای دریافت نمودار سکه با استفاده از شناسه سکه، دوره نمودار و واحد پولی.
     *
     * @param coinId شناسه سکه برای دریافت نمودار.
     * @param chartPeriod دوره زمانی نمودار مانند "24h", "7d" و غیره.
     * @param currency واحد پولی که برای نمایش قیمت‌ها استفاده می‌شود.
     * @return Flow<Result<CoinChart>> جریانی که نتیجه دریافت نمودار سکه را ارائه می‌دهد.
     */
    override fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Flow<Result<CoinChart>> = flow {
        val response = coinNetworkDataSource.getCoinChart(
            coinId = coinId,
            chartPeriod = chartPeriod,
            currency = currency
        )

        val body = response.body()

        if (response.isSuccessful && body?.coinChartData != null) {
            val coinChart = coinChartMapper.mapApiModelToModel(body, currency = currency)
            emit(Result.Success(coinChart))
        } else {
            Timber.e("getCoinChart unsuccessful retrofit response ${response.message()}")
            emit(Result.Error("Unable to fetch coin chart"))
        }
    }.catch { e ->
        Timber.e("getCoinChart error ${e.message}")
        emit(Result.Error("Unable to fetch coin chart"))
    }.flowOn(ioDispatcher)
}
