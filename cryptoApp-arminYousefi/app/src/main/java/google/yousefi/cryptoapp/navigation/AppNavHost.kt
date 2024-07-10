package google.yousefi.cryptoapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import google.yousefi.cryptoapp.ui.screen.details.DetailsScreen
import google.yousefi.cryptoapp.ui.screen.settings.SettingsScreen

/**
 * این تابع برای نمایش و مدیریت ناوبری در اپلیکیشن استفاده می‌شود، با استفاده از NavHost و Composable های مختلف.
 *
 * @param navigationBarStartScreen صفحه اولیه نوار ناوبری که به آن وارد می‌شویم. پیش‌فرض `NavigationBarScreen.Market` می‌باشد.
 * @param navController کنترل کننده ناوبری که برای ناوبری در سطح بالاتر استفاده می‌شود.
 */
@Composable
fun AppNavHost(
    navigationBarStartScreen: NavigationBarScreen = NavigationBarScreen.Market,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NavigationBar.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        // Composable برای نمایش نوار ناوبری
        composable(route = Screen.NavigationBar.route) {
            NavigationBarScaffold(
                startScreen = navigationBarStartScreen,
                onNavigateDetails = { coinId: String ->
                    navController.navigate(Screen.Details.route + "/$coinId")
                },
                onNavigateSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        // Composable برای نمایش صفحه جزئیات سکه با استفاده از route پویا
        composable(
            route = Screen.Details.route + "/{coinId}",
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) {
            DetailsScreen(onNavigateUp = { navController.navigateUp() })
        }
        // Composable برای نمایش صفحه تنظیمات
        composable(
            route = Screen.Settings.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) {
            SettingsScreen(onNavigateUp = { navController.navigateUp() })
        }
    }
}
