package google.yousefi.cryptoapp.data.repository.searchResults

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.model.SearchCoin

/**
 * رابط CoinSearchResultsRepository برای ارتباط با منبع داده‌های جستجوی سکه و دریافت نتایج جستجو مورد نیاز استفاده می‌شود.
 */
interface CoinSearchResultsRepository {

    /**
     * متدی برای دریافت نتایج جستجو بر اساس یک رشته جستجو از منبع داده.
     *
     * @param searchQuery رشته جستجو برای جستجوی سکه‌های مورد نظر.
     * @return Result<List<SearchCoin>> نتیجه دریافت نتایج جستجو یا خطا در صورت وجود مشکل.
     */
    suspend fun getCoinSearchResults(searchQuery: String): Result<List<SearchCoin>>
}

