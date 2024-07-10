package google.yousefi.cryptoapp.model

/**
 * این کلاس برای نگهداری جزئیات ارز مانند شناسه، نام، نماد، تصویر، قیمت کنونی،
 * سرمایه‌بازار، رتبه سرمایه‌بازار، حجم معاملات 24 ساعته، تأمین موجود، بالاترین قیمت تاریخی و
 * تاریخ لیست شدن می‌باشد.
 *
 * @property id شناسه یکتای ارز.
 * @property name نام ارز.
 * @property symbol نماد ارز.
 * @property imageUrl آدرس تصویر نمایش داده شده ارز.
 * @property currentPrice قیمت کنونی ارز.
 * @property marketCap سرمایه‌بازار کل ارز.
 * @property marketCapRank رتبه سرمایه‌بازار ارز.
 * @property volume24h حجم معاملات 24 ساعته ارز.
 * @property circulatingSupply تأمین معاملاتی ارز.
 * @property allTimeHigh بالاترین قیمت تاریخی ارز.
 * @property allTimeHighDate تاریخ دستیابی به بالاترین قیمت تاریخی.
 * @property listedDate تاریخ لیست شدن ارز در بورس یا تبادل.
 */
data class CoinDetails(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val currentPrice: Price,
    val marketCap: Price,
    val marketCapRank: String,
    val volume24h: Price,
    val circulatingSupply: String,
    val allTimeHigh: Price,
    val allTimeHighDate: String,
    val listedDate: String
)

