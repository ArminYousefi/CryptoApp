package google.yousefi.cryptoapp.data.mapper

import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.remote.model.CoinDetailsApiModel
import google.yousefi.cryptoapp.model.CoinDetails
import google.yousefi.cryptoapp.model.Price
import java.text.NumberFormat
import java.time.DateTimeException
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

/**
 * کلاس CoinDetailsMapper برای تبدیل مدل API به مدل داخلی برنامه استفاده می‌شود.
 *
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinDetailsMapper @Inject constructor() {

    companion object {
        /**
         * فرمت‌کننده تاریخ برای تبدیل تاریخ‌ها به الگوی "d MMM yyyy" با زبان انگلیسی.
         */
        private val dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.US)

        /**
         * فرمت‌کننده اعداد برای افزودن جداکننده‌های گروهی در اعداد.
         */
        private val numberGroupingFormat = NumberFormat.getNumberInstance(Locale.US).apply {
            isGroupingUsed = true
        }
    }

    /**
     * متدی برای تبدیل مدل API به مدل CoinDetails.
     *
     * @param apiModel مدل API که شامل داده‌های جزئیات سکه است.
     * @param currency واحد پولی که برای قیمت‌ها استفاده می‌شود.
     * @return CoinDetails مدل CoinDetails که شامل داده‌های معتبر و تبدیل شده است.
     */
    fun mapApiModelToModel(apiModel: CoinDetailsApiModel, currency: Currency): CoinDetails {
        val coinDetails = apiModel.coinDetailsDataHolder?.coinDetailsData

        return CoinDetails(
            id = coinDetails?.id.orEmpty(),
            name = coinDetails?.name.orEmpty(),
            symbol = coinDetails?.symbol.orEmpty(),
            imageUrl = coinDetails?.imageUrl.orEmpty(),
            currentPrice = Price(coinDetails?.currentPrice, currency = currency),
            marketCap = Price(coinDetails?.marketCap, currency = currency),
            marketCapRank = coinDetails?.marketCapRank.orEmpty(),
            volume24h = Price(coinDetails?.volume24h, currency = currency),
            circulatingSupply = formatNumberOrEmpty(coinDetails?.supply?.circulatingSupply),
            // API only supports ATH in USD
            allTimeHigh = Price(coinDetails?.allTimeHigh?.price, currency = Currency.USD),
            allTimeHighDate = epochToDateOrEmpty(coinDetails?.allTimeHigh?.timestamp),
            listedDate = epochToDateOrEmpty(coinDetails?.listedAt)
        )
    }

    /**
     * متدی برای تبدیل مقدار epoch به رشته تاریخ. در صورت وجود خطا یا مقدار نامعتبر، رشته خالی باز می‌گردد.
     *
     * @param epochSecond مقدار epoch که باید تبدیل شود.
     * @return String رشته تاریخ به فرمت مشخص، یا رشته خالی در صورت وجود خطا یا مقدار نامعتبر.
     */
    private fun epochToDateOrEmpty(epochSecond: Long?): String {
        try {
            if (epochSecond == null || epochSecond < 0) return ""

            val epochInstant = Instant.ofEpochSecond(epochSecond)
                .atZone(ZoneId.systemDefault())

            return dateFormatter.format(epochInstant)
        } catch (e: DateTimeException) {
            return ""
        }
    }

    /**
     * متدی برای فرمت کردن عدد به رشته با جداکننده‌های گروهی. در صورت نامعتبر بودن ورودی، رشته خالی باز می‌گردد.
     *
     * @param numberString رشته عددی که باید فرمت شود.
     * @return String رشته عدد فرمت شده، یا رشته خالی در صورت نامعتبر بودن ورودی.
     */
    private fun formatNumberOrEmpty(numberString: String?): String {
        val number = numberString?.toDoubleOrNull() ?: return ""

        return numberGroupingFormat.format(number)
    }
}
