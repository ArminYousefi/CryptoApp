package google.yousefi.cryptoapp.domain.search

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.searchResults.CoinSearchResultsRepository
import google.yousefi.cryptoapp.model.SearchCoin
import javax.inject.Inject

/**
 * UseCase برای دریافت نتایج جستجوی سکه‌ها.
 *
 * @param coinSearchResultsRepository ریپازیتوری مربوط به نتایج جستجوی سکه‌ها.
 */
class GetCoinSearchResultsUseCase @Inject constructor(
    private val coinSearchResultsRepository: CoinSearchResultsRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای دریافت نتایج جستجوی سکه‌ها اجرا می‌کند.
     *
     * @param searchQuery کوئری جستجو برای سکه‌ها.
     * @return نتیجه‌ای از نوع Result<List<SearchCoin>> که حاوی نتایج جستجو است.
     */
    suspend operator fun invoke(searchQuery: String): Result<List<SearchCoin>> {
        return getCoinSearchResults(searchQuery = searchQuery)
    }

    /**
     * یک متد خصوصی برای دریافت نتایج جستجو از ریپازیتوری مربوطه.
     *
     * @param searchQuery کوئری جستجو برای سکه‌ها.
     * @return نتیجه‌ای از نوع Result<List<SearchCoin>> که حاوی نتایج جستجو است.
     */
    private suspend fun getCoinSearchResults(searchQuery: String): Result<List<SearchCoin>> {
        return coinSearchResultsRepository.getCoinSearchResults(searchQuery = searchQuery)
    }
}
