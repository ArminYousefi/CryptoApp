package google.yousefi.cryptoapp.data.source.local.preferences.common

import androidx.annotation.StringRes
import google.yousefi.cryptoapp.R

enum class CoinSort(@StringRes val nameId: Int) {
    /**
     * برای نمایش نوع ارز بر اساس مقدار بازار.
     *
     * @param nameId رشته‌ی منابع مورد استفاده برای نام این نوع ارز.
     * @return یک عنصر از enum CoinSort که نوع ارز بازار است.
     */
    MarketCap(R.string.market_coin_sort_market_cap),

    /**
     * برای نمایش نوع ارز بر اساس محبوبیت.
     *
     * @param nameId رشته‌ی منابع مورد استفاده برای نام این نوع ارز.
     * @return یک عنصر از enum CoinSort که نوع ارز محبوب است.
     */
    Popular(R.string.market_coin_sort_popular),

    /**
     * برای نمایش نوع ارزهای برتر از لحاظ رشد.
     *
     * @param nameId رشته‌ی منابع مورد استفاده برای نام این نوع ارز.
     * @return یک عنصر از enum CoinSort که نوع ارزهای برتر از لحاظ رشد است.
     */
    Gainers(R.string.market_coin_sort_gainers),

    /**
     * برای نمایش نوع ارزهای برتر از لحاظ افت.
     *
     * @param nameId رشته‌ی منابع مورد استفاده برای نام این نوع ارز.
     * @return یک عنصر از enum CoinSort که نوع ارزهای برتر از لحاظ افت است.
     */
    Losers(R.string.market_coin_sort_losers),

    /**
     * برای نمایش نوع ارزهای جدیدتر.
     *
     * @param nameId رشته‌ی منابع مورد استفاده برای نام این نوع ارز.
     * @return یک عنصر از enum CoinSort که نوع ارزهای جدیدتر است.
     */
    Newest(R.string.market_coin_sort_newest)
}
