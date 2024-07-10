package google.yousefi.cryptoapp.model

/**
 * این کلاس برای نمایش آمار بازار مانند درصد تغییر سرمایه‌بازار در 24 ساعت گذشته می‌باشد.
 *
 * @property marketCapChangePercentage24h درصد تغییر سرمایه‌بازار در 24 ساعت گذشته.
 */
data class MarketStats(
    val marketCapChangePercentage24h: Percentage
)
