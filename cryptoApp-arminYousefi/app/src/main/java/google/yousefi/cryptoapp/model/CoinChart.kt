package google.yousefi.cryptoapp.model

import java.math.BigDecimal
import kotlinx.collections.immutable.ImmutableList

/**
 * این کلاس برای نمایش نمودار قیمت ارز می‌باشد.
 *
 * @property prices لیست قیمت‌ها برای نمودار. این ورودی نباید تغییرپذیر باشد.
 * @property minPrice حداقل قیمت در بازه زمانی مشخص.
 * @property maxPrice حداکثر قیمت در بازه زمانی مشخص.
 * @property periodPriceChangePercentage درصد تغییر قیمت در بازه زمانی مشخص.
 */
data class CoinChart(
    val prices: ImmutableList<BigDecimal>,
    val minPrice: Price,
    val maxPrice: Price,
    val periodPriceChangePercentage: Percentage
)

