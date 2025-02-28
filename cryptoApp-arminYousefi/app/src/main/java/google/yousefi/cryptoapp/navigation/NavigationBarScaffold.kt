package google.yousefi.cryptoapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import google.yousefi.cryptoapp.ui.screen.favourites.FavouritesScreen
import google.yousefi.cryptoapp.ui.screen.market.MarketScreen
import google.yousefi.cryptoapp.ui.screen.search.SearchScreen
import kotlinx.collections.immutable.persistentListOf

/**
 * این تابع برای نمایش یک صفحه اصلی شامل نوار ناوبری و محتوای داخلی استفاده می‌شود.
 *
 * @param startScreen صفحه اولیه که باید به آن وارد شویم.
 * @param onNavigateDetails تابع برای ناوبری به صفحه جزئیات سکه با گرفتن شناسه سکه.
 * @param onNavigateSettings تابع برای ناوبری به صفحه تنظیمات.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
fun NavigationBarScaffold(
    startScreen: NavigationBarScreen,
    onNavigateDetails: (String) -> Unit,
    onNavigateSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val navigationBarScreens = remember {
        persistentListOf(
            NavigationBarScreen.Market,
            NavigationBarScreen.Favourites,
            NavigationBarScreen.Search
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showNavigationBar = navigationBarScreens.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showNavigationBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    tonalElevation = 0.dp
                ) {
                    navigationBarScreens.forEach { screen ->
                        AddNavigationBarItem(
                            screen = screen,
                            currentDestination = currentDestination,
                            navController = navController
                        )
                    }
                }
            }
        },
        content = { scaffoldPadding ->
            NavigationBarNavHost(
                navController = navController,
                startScreen = startScreen,
                onNavigateDetails = onNavigateDetails,
                onNavigateSettings = onNavigateSettings,
                modifier = Modifier.padding(scaffoldPadding)
            )
        },
        modifier = modifier
    )
}


/**
 * این تابع برای نمایش NavHost برای ناوبری بین صفحات مختلف در نوار ناوبری استفاده می‌شود.
 *
 * @param navController کنترل کننده ناوبری برای اداره و پیگیری استک صفحات.
 * @param startScreen صفحه اولیه که به آن وارد شویم.
 * @param onNavigateDetails تابع برای ناوبری به صفحه جزئیات سکه با گرفتن شناسه سکه.
 * @param onNavigateSettings تابع برای ناوبری به صفحه تنظیمات.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
private fun NavigationBarNavHost(
    navController: NavHostController,
    startScreen: NavigationBarScreen,
    onNavigateDetails: (String) -> Unit,
    onNavigateSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        composable(route = NavigationBarScreen.Market.route) {
            MarketScreen(
                onNavigateDetails = onNavigateDetails,
                onNavigateSettings = onNavigateSettings
            )
        }
        composable(route = NavigationBarScreen.Favourites.route) {
            FavouritesScreen(onNavigateDetails = onNavigateDetails)
        }
        composable(route = NavigationBarScreen.Search.route) {
            SearchScreen(onNavigateDetails = onNavigateDetails)
        }
    }
}


/**
 * این تابع Composable برای افزودن آیتم‌های مختلف به نوار ناوبری استفاده می‌شود.
 *
 * @param screen صفحه ای که باید به نوار ناوبری اضافه شود.
 * @param currentDestination مقصد کنونی که توسط NavHost مدیریت می‌شود.
 * @param navController کنترل کننده ناوبری برای اداره و پیگیری استک صفحات.
 * @param modifier Modifier برای تنظیمات ویژگی‌های ظاهری Composable.
 */
@Composable
private fun RowScope.AddNavigationBarItem(
    screen: NavigationBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val selected = currentDestination?.hierarchy?.any { destination ->
        destination.route == screen.route
    } == true

    NavigationBarItem(
        label = {
            Text(
                text = stringResource(screen.nameResourceId),
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        },
        icon = {
            Icon(
                imageVector = when (screen) {
                    NavigationBarScreen.Market -> Icons.Rounded.BarChart
                    NavigationBarScreen.Favourites -> Icons.Rounded.Favorite
                    NavigationBarScreen.Search -> Icons.Rounded.Search
                },
                contentDescription = null
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSurface,
            selectedTextColor = MaterialTheme.colorScheme.onSurface,
            indicatorColor = MaterialTheme.colorScheme.background,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier
    )
}
