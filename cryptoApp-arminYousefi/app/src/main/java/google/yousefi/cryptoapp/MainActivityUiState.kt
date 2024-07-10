package google.yousefi.cryptoapp

import google.yousefi.cryptoapp.navigation.NavigationBarScreen

data class MainActivityUiState(
    val startScreen: NavigationBarScreen = NavigationBarScreen.Market, // صفحه‌ی شروع برای نوار ناوبری، پیش‌فرض "Market"
    val isLoading: Boolean = false // وضعیت بارگذاری، پیش‌فرض "false"
)
