package google.yousefi.cryptoapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

/**
 * مدل داده برای نتایج جستجوی سکه‌ها از API است.
 *
 * @property coinsSearchResultsData داده‌های نتایج جستجو که شامل لیستی از نتایج جستجوی سکه‌ها است.
 */
data class CoinSearchResultsApiModel(
    @SerializedName("data")
    val coinsSearchResultsData: CoinSearchResultsData?
)

/**
 * مدل داده برای داده‌های نتایج جستجوی سکه‌ها است.
 *
 * @property coinSearchResults لیستی از نتایج جستجوی سکه‌ها.
 */
data class CoinSearchResultsData(
    @SerializedName("coins")
    val coinSearchResults: List<CoinSearchResult?>?
)

/**
 * مدل داده برای نتیجه جستجوی یک سکه است.
 *
 * @property id شناسه یکتای سکه.
 * @property symbol نماد سکه.
 * @property name نام سکه.
 * @property imageUrl URL تصویر آیکون سکه.
 */
data class CoinSearchResult(
    @SerializedName("uuid")
    val id: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("iconUrl")
    val imageUrl: String?
)
