package google.yousefi.cryptoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import google.yousefi.cryptoapp.data.source.local.preferences.global.StartScreen
import google.yousefi.cryptoapp.domain.preferences.GetUserPreferencesUseCase
import google.yousefi.cryptoapp.navigation.NavigationBarScreen
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase // UseCase برای دریافت ترجیحات کاربر
) : ViewModel() {

    // وضعیت UI به صورت StateFlow که از جنس MutableStateFlow استفاده می‌شود
    private val _uiState = MutableStateFlow(MainActivityUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initialiseUiState() // فراخوانی تابع برای مقداردهی اولیه وضعیت UI
    }

    // تابع خصوصی برای مقداردهی اولیه وضعیت UI
    private fun initialiseUiState() {
        // بروزرسانی وضعیت به حالت بارگذاری
        _uiState.update { it.copy(isLoading = true) }

        // فراخوانی UseCase برای دریافت ترجیحات کاربر
        getUserPreferencesUseCase()
            .onEach { userPreferences ->
                // بروزرسانی وضعیت UI با توجه به ترجیحات دریافتی از کاربر
                _uiState.update {
                    it.copy(
                        startScreen = when (userPreferences.startScreen) {
                            StartScreen.Market -> NavigationBarScreen.Market
                            StartScreen.Favourites -> NavigationBarScreen.Favourites
                            StartScreen.Search -> NavigationBarScreen.Search
                        },
                        isLoading = false // تغییر وضعیت به غیر از بارگذاری
                    )
                }
            }.catch {
                // در صورت بروز خطا، بروزرسانی وضعیت به حالت بارگذاری ناموفق
                _uiState.update { it.copy(isLoading = false) }
            }.launchIn(viewModelScope) // اجرای پیمایش در محدوده ViewModel
    }
}
