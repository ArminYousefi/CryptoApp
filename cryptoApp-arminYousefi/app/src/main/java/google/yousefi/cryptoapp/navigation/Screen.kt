package google.yousefi.cryptoapp.navigation

import androidx.annotation.StringRes
import google.yousefi.cryptoapp.R

/**
 * این sealed class برای نمایش صفحات مختلف در نوار ناوبری استفاده می‌شود.
 *
 * @property route مسیر یکتای صفحه برای استفاده در ناوبری.
 * @property nameResourceId شناسه منبع متنی برای نام صفحه.
 */
sealed class NavigationBarScreen(
    val route: String,
    @StringRes val nameResourceId: Int
) {
    /**
     * صفحه بازار.
     */
    data object Market : NavigationBarScreen(
        route = "market_screen",
        nameResourceId = R.string.market_screen
    )

    /**
     * صفحه علاقه‌مندی‌ها.
     */
    data object Favourites : NavigationBarScreen(
        route = "favourites_screen",
        nameResourceId = R.string.favourites_screen
    )

    /**
     * صفحه جستجو.
     */
    data object Search : NavigationBarScreen(
        route = "search_screen",
        nameResourceId = R.string.search_screen
    )
}


/**
 * این sealed class برای تعریف صفحات مختلف در ناوبری استفاده می‌شود.
 *
 * @property route مسیر یکتای صفحه برای استفاده در ناوبری.
 */
sealed class Screen(val route: String) {
    /**
     * صفحه جزئیات.
     */
    data object Details : Screen("details_screen")

    /**
     * صفحه تنظیمات.
     */
    data object Settings : Screen("settings_screen")

    /**
     * صفحه ناوبری.
     */
    data object NavigationBar : Screen("navigation_bar_screen")
}

