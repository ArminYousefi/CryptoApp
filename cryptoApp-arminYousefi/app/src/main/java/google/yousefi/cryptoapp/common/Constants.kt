package google.yousefi.cryptoapp.common

/**
 * شیء Constants برای نگهداری مقادیر ثابت که در کل برنامه استفاده می‌شوند.
 */
object Constants {
    /**
     * URL پایه برای ارتباط با API سایت Coin Ranking.
     */
    const val BASE_URL = "https://api.coinranking.com/v2/"

    /**
     * نام پارامتر مربوط به شناسه سکه در درخواست‌های API.
     */
    const val PARAM_COIN_ID = "coinId"

    /**
     * نام پایگاه داده برای ذخیره اطلاعات سکه‌ها.
     */
    const val COIN_DATABASE_NAME = "Coin.db"
}

