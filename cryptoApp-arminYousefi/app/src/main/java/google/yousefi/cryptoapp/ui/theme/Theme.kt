package google.yousefi.cryptoapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import google.yousefi.cryptoapp.navigation.Screen

private val darkColorScheme = darkColorScheme(
    primary = dark_primary,                 // رنگ اصلی در تم تاریک
    primaryContainer = dark_primaryContainer, // رنگ ظرف اصلی در تم تاریک
    onPrimaryContainer = dark_onPrimaryContainer, // رنگ متن بر روی ظرف اصلی در تم تاریک
    background = dark_background,           // رنگ پس‌زمینه در تم تاریک
    onBackground = dark_onBackground,       // رنگ متن بر روی پس‌زمینه در تم تاریک
    surface = dark_surface,                 // رنگ سطح در تم تاریک
    onSurface = dark_onSurface,             // رنگ متن بر روی سطح در تم تاریک
    onSurfaceVariant = dark_onSurfaceVariant // رنگ متن بر روی نوع سطح در تم تاریک
)

/**
 * Composable که تم را برای اپلیکیشن تعریف می‌کند.
 *
 * @param navController NavHostController برای مدیریت ناوبری
 * @param systemUiController SystemUiController برای کنترل رنگ StatusBar و NavigationBar
 * @param content تابع Composable که محتوای اصلی تم را در بر می‌گیرد
 */
@Composable
fun AppTheme(
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    content: @Composable () -> Unit
) {
    // تنظیم رنگ StatusBar بر اساس پس‌زمینه تعیین شده در darkColorScheme
    LaunchedEffect(systemUiController) {
        systemUiController.setStatusBarColor(
            color = darkColorScheme.background,
            darkIcons = false
        )
    }

    // دریافت وضعیت فعلی ناوبری
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // تنظیم رنگ NavigationBar بر اساس مسیر فعلی
    LaunchedEffect(navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route
        val inNavigationBarScreen = currentRoute == Screen.NavigationBar.route

        // تعیین رنگ NavigationBar بر اساس وضعیت مسیر فعلی
        val gestureBarColor = if (inNavigationBarScreen) {
            darkColorScheme.primaryContainer
        } else {
            darkColorScheme.background
        }

        systemUiController.setNavigationBarColor(
            color = gestureBarColor,
            darkIcons = false
        )
    }

    // اعمال تم به محتوای اصلی با استفاده از MaterialTheme
    MaterialTheme(
        colorScheme = darkColorScheme, // تنظیمات رنگ تم
        shapes = AppShapes,            // تنظیمات اشکال
        typography = AppTypography,    // تنظیمات تایپوگرافی
        content = content              // محتوای اصلی تم
    )
}
