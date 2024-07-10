package google.yousefi.cryptoapp.data.repository.searchResults

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.mapper.CoinSearchResultsMapper
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSource
import google.yousefi.cryptoapp.di.IoDispatcher
import google.yousefi.cryptoapp.model.SearchCoin
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * کلاس CoinSearchResultsRepositoryImpl برای اجرای رابط CoinSearchResultsRepository و دریافت نتایج جستجوی سکه از منبع داده‌های شبکه استفاده می‌شود.
 *
 * @property coinNetworkDataSource منبع داده شبکه برای دریافت نتایج جستجوی سکه.
 * @property coinSearchResultsMapper Mapper برای تبدیل مدل‌های داده‌ای شبکه به مدل‌های داده‌ای داخلی.
 * @property ioDispatcher Dispatcher برای اجرای عملیات‌ها در محیط نخ اصلی (IO).
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinSearchResultsRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinSearchResultsMapper: CoinSearchResultsMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoinSearchResultsRepository {

    /**
     * متدی برای دریافت نتایج جستجو بر اساس یک رشته جستجو از منبع داده شبکه.
     *
     * @param searchQuery رشته جستجو برای جستجوی سکه‌های مورد نظر.
     * @return Result<List<SearchCoin>> نتیجه دریافت نتایج جستجو یا خطا در صورت وجود مشکل.
     */
    override suspend fun getCoinSearchResults(searchQuery: String): Result<List<SearchCoin>> {
        return try {
            withContext(ioDispatcher) {
                val response = coinNetworkDataSource.getCoinSearchResults(searchQuery = searchQuery)
                val body = response.body()

                if (response.isSuccessful && body?.coinsSearchResultsData != null) {
                    val coinSearchResults = coinSearchResultsMapper.mapApiModelToModel(body)
                    Result.Success(coinSearchResults)
                } else {
                    Timber.e(
                        "getCoinSearchResults unsuccessful retrofit response ${response.message()}"
                    )
                    Result.Error("Unable to fetch coin search results")
                }
            }
        } catch (e: Exception) {
            Timber.e("getCoinSearchResults error ${e.message}")
            Result.Error("Unable to fetch coin search results")
        }
    }
}
