package google.yousefi.cryptoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import google.yousefi.cryptoapp.navigation.AppNavHost
import google.yousefi.cryptoapp.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // نصب صفحه اولیه برنامه

        super.onCreate(savedInstanceState)

        // تنظیم محتوا برای این اکتیویتی با استفاده از Compose
        setContent {
            val navController = rememberNavController() // ایجاد NavController برای مدیریت ناوبری

            // استفاده از AppTheme برای تنظیم تم برنامه
            AppTheme(navController = navController) {
                val viewModel: MainActivityViewModel = hiltViewModel() // دریافت ViewModel با استفاده از Hilt
                val uiState by viewModel.uiState.collectAsStateWithLifecycle() // دریافت وضعیت UI به صورت state

                // اگر بارگذاری اطلاعات انجام شده باشد، AppNavHost را نمایش بده
                if (!uiState.isLoading) {
                    AppNavHost(
                        navController = navController,
                        navigationBarStartScreen = uiState.startScreen
                    )
                }
            }
        }
    }
}
