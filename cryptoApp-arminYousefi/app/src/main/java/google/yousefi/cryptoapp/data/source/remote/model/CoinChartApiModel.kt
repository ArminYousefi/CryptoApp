package google.yousefi.cryptoapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

/**
 * مدل داده برای اطلاعات نمودار سکه از API است.
 *
 * @property coinChartData اطلاعات نمودار سکه که شامل تغییرات قیمت و قیمت‌های گذشته است.
 */
data class CoinChartApiModel(
    @SerializedName("data")
    val coinChartData: CoinChartData?
)

/**
 * مدل داده برای اطلاعات نمودار یک سکه است.
 *
 * @property priceChangePercentage درصد تغییر قیمت اخیر.
 * @property pastPrices لیستی از قیمت‌های گذشته برای این سکه.
 */
data class CoinChartData(
    @SerializedName("change")
    val priceChangePercentage: String?,
    @SerializedName("history")
    val pastPrices: List<PastPrice?>?
)

/**
 * مدل داده برای قیمت گذشته یک سکه در تاریخ خاصی.
 *
 * @property amount مقدار قیمت در تاریخ مشخص شده.
 */
data class PastPrice(
    @SerializedName("price")
    val amount: String?
)

