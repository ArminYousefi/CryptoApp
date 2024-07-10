package google.yousefi.cryptoapp.data.source.remote

import google.yousefi.cryptoapp.data.source.remote.model.CoinChartApiModel
import google.yousefi.cryptoapp.data.source.remote.model.CoinDetailsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.CoinSearchResultsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.CoinsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.FavouriteCoinsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.MarketStatsApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * رابط API برای دریافت اطلاعات سکه‌ها.
 */
interface CoinApi {
    /**
     * دریافت لیستی از سکه‌ها.
     *
     * @param currencyUUID شناسه یکتای ارز مرجع برای تبدیل قیمت‌ها. مقدار پیش‌فرض "yhjMzLPhuIDl".
     * @param orderBy معیاری که براساس آن سکه‌ها مرتب‌سازی می‌شوند. مقدار پیش‌فرض "marketCap".
     * @param timePeriod بازه زمانی که داده‌ها براساس آن محاسبه می‌شوند. مقدار پیش‌فرض "24h".
     * @param orderDirection جهت مرتب‌سازی سکه‌ها. مقدار پیش‌فرض "desc".
     * @param limit حداکثر تعداد سکه‌هایی که باید دریافت شود. مقدار پیش‌فرض "100".
     * @return یک پاسخ شامل مدل داده سکه‌ها.
     */
    @GET("coins")
    suspend fun getCoins(
        @Query("referenceCurrencyUuid") currencyUUID: String = "yhjMzLPhuIDl",
        @Query("orderBy") orderBy: String = "marketCap",
        @Query("timePeriod") timePeriod: String = "24h",
        @Query("orderDirection") orderDirection: String = "desc",
        @Query("limit") limit: String = "100"
    ): Response<CoinsApiModel>

    /**
     * دریافت لیستی از سکه‌های مورد علاقه.
     *
     * @param coinIds لیستی از شناسه‌های یکتای سکه‌های مورد علاقه.
     * @param currencyUUID شناسه یکتای ارز مرجع برای تبدیل قیمت‌ها. مقدار پیش‌فرض "yhjMzLPhuIDl".
     * @param orderBy معیاری که براساس آن سکه‌ها مرتب‌سازی می‌شوند. مقدار پیش‌فرض "marketCap".
     * @param timePeriod بازه زمانی که داده‌ها براساس آن محاسبه می‌شوند. مقدار پیش‌فرض "24h".
     * @param orderDirection جهت مرتب‌سازی سکه‌ها. مقدار پیش‌فرض "desc".
     * @param limit حداکثر تعداد سکه‌هایی که باید دریافت شود. مقدار پیش‌فرض "100".
     * @return یک پاسخ شامل مدل داده سکه‌های مورد علاقه.
     */
    @GET("coins")
    suspend fun getFavouriteCoins(
        @Query("uuids[]") coinIds: List<String>,
        @Query("referenceCurrencyUuid") currencyUUID: String = "yhjMzLPhuIDl",
        @Query("orderBy") orderBy: String = "marketCap",
        @Query("timePeriod") timePeriod: String = "24h",
        @Query("orderDirection") orderDirection: String = "desc",
        @Query("limit") limit: String = "100"
    ): Response<FavouriteCoinsApiModel>

    /**
     * دریافت جزئیات یک سکه.
     *
     * @param coinId شناسه یکتای سکه.
     * @param currencyUUID شناسه یکتای ارز مرجع برای تبدیل قیمت‌ها. مقدار پیش‌فرض "yhjMzLPhuIDl".
     * @return یک پاسخ شامل مدل داده جزئیات سکه.
     */
    @GET("coin/{coinId}")
    suspend fun getCoinDetails(
        @Path("coinId") coinId: String,
        @Query("referenceCurrencyUuid") currencyUUID: String = "yhjMzLPhuIDl"
    ): Response<CoinDetailsApiModel>

    /**
     * دریافت داده‌های نمودار یک سکه.
     *
     * @param coinId شناسه یکتای سکه.
     * @param currencyUUID شناسه یکتای ارز مرجع برای تبدیل قیمت‌ها. مقدار پیش‌فرض "yhjMzLPhuIDl".
     * @param chartPeriod بازه زمانی که نمودار براساس آن محاسبه می‌شود. مقدار پیش‌فرض "24h".
     * @return یک پاسخ شامل مدل داده نمودار سکه.
     */
    @GET("coin/{coinId}/history")
    suspend fun getCoinChart(
        @Path("coinId") coinId: String,
        @Query("referenceCurrencyUuid") currencyUUID: String = "yhjMzLPhuIDl",
        @Query("timePeriod") chartPeriod: String = "24h"
    ): Response<CoinChartApiModel>

    /**
     * دریافت نتایج جستجوی سکه‌ها.
     *
     * @param searchQuery عبارت جستجو. مقدار پیش‌فرض رشته خالی است.
     * @param currencyUUID شناسه یکتای ارز مرجع برای تبدیل قیمت‌ها. مقدار پیش‌فرض "yhjMzLPhuIDl".
     * @return یک پاسخ شامل مدل داده نتایج جستجوی سکه‌ها.
     */
    @GET("search-suggestions")
    suspend fun getCoinSearchResults(
        @Query("query") searchQuery: String = "",
        @Query("referenceCurrencyUuid") currencyUUID: String = "yhjMzLPhuIDl"
    ): Response<CoinSearchResultsApiModel>

    /**
     * دریافت آمار بازار.
     *
     * @return یک پاسخ شامل مدل داده آمار بازار.
     */
    @GET("stats/coins")
    suspend fun getMarketStats(): Response<MarketStatsApiModel>
}

