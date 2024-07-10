package google.yousefi.cryptoapp.data.repository.details

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.mapper.CoinDetailsMapper
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSource
import google.yousefi.cryptoapp.di.IoDispatcher
import google.yousefi.cryptoapp.model.CoinDetails
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

/**
 * کلاس CoinDetailsRepositoryImpl برای اجرای رابط CoinDetailsRepository و ارتباط با منبع داده شبکه سکه‌ها استفاده می‌شود.
 *
 * @property coinNetworkDataSource منبع داده شبکه برای دریافت اطلاعات جزئیات سکه از API.
 * @property coinDetailsMapper Mapper برای تبدیل مدل API به مدل داخلی CoinDetails.
 * @property ioDispatcher Dispatcher برای اجرای کدهای مربوط به شبکه در یک thread جداگانه.
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinDetailsRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinDetailsMapper: CoinDetailsMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoinDetailsRepository {

    /**
     * متدی برای دریافت جزئیات سکه با استفاده از شناسه سکه و واحد پولی.
     *
     * @param coinId شناسه سکه برای دریافت جزئیات.
     * @param currency واحد پولی که برای نمایش قیمت‌ها استفاده می‌شود.
     * @return Flow<Result<CoinDetails>> جریانی که نتیجه دریافت جزئیات سکه را ارائه می‌دهد.
     */
    override fun getCoinDetails(coinId: String, currency: Currency): Flow<Result<CoinDetails>> =
        flow {
            val response = coinNetworkDataSource.getCoinDetails(
                coinId = coinId,
                currency = currency
            )

            val body = response.body()

            if (response.isSuccessful && body?.coinDetailsDataHolder?.coinDetailsData != null) {
                val coinDetails = coinDetailsMapper.mapApiModelToModel(body, currency = currency)
                emit(Result.Success(coinDetails))
            } else {
                Timber.e("getCoinDetails unsuccessful retrofit response ${response.message()}")
                emit(Result.Error("Unable to fetch coin details"))
            }
        }.catch { e ->
            Timber.e("getCoinDetails exception ${e.message}")
            emit(Result.Error("Unable to fetch coin details"))
        }.flowOn(ioDispatcher)
}
