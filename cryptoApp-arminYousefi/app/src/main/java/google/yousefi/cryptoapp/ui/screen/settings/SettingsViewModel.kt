package google.yousefi.cryptoapp.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.preferences.global.StartScreen
import google.yousefi.cryptoapp.domain.preferences.GetUserPreferencesUseCase
import google.yousefi.cryptoapp.domain.preferences.UpdateCurrencyUseCase
import google.yousefi.cryptoapp.domain.preferences.UpdateStartScreenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel برای مدیریت تنظیمات کاربری از جمله صفحه شروع و واحد پول استفاده شده است.
 *
 * @param getUserPreferencesUseCase UseCase برای دریافت تنظیمات کاربری فعلی.
 * @param updateStartScreenUseCase UseCase برای به روزرسانی صفحه شروع انتخاب شده توسط کاربر.
 * @param updateCurrencyUseCase UseCase برای به روزرسانی واحد پول فعلی انتخاب شده توسط کاربر.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val updateStartScreenUseCase: UpdateStartScreenUseCase,
    private val updateCurrencyUseCase: UpdateCurrencyUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        initialiseUiState()
    }

    /**
     * متدی که هنگام ایجاد ViewModel فراخوانی می‌شود تا وضعیت اولیه UI را مقداردهی کند و تنظیمات کاربری را دریافت کند.
     */
    fun initialiseUiState() {
        val userPreferencesFlow = getUserPreferencesUseCase()

        userPreferencesFlow.onEach { userPreferences ->
            _uiState.update {
                it.copy(
                    startScreen = userPreferences.startScreen,
                    currency = userPreferences.currency,
                    isLoading = false,
                    errorMessage = null
                )
            }
        }.catch {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Error getting user preferences"
                )
            }
        }.launchIn(viewModelScope)
    }

    /**
     * متد برای به روزرسانی واحد پول انتخاب شده توسط کاربر فراخوانی می‌شود.
     *
     * @param currency واحد پول جدید برای به روزرسانی.
     */
    fun updateCurrency(currency: Currency) {
        viewModelScope.launch {
            updateCurrencyUseCase(currency = currency)
        }
    }

    /**
     * متد برای به روزرسانی صفحه شروع انتخاب شده توسط کاربر فراخوانی می‌شود.
     *
     * @param startScreen صفحه شروع جدید برای به روزرسانی.
     */
    fun updateStartScreen(startScreen: StartScreen) {
        viewModelScope.launch {
            updateStartScreenUseCase(startScreen = startScreen)
        }
    }

    /**
     * متد برای نمایش یا مخفی کردن صفحه واحد پول استفاده می‌شود.
     *
     * @param showSheet نمایش یا مخفی کردن صفحه واحد پول.
     */
    fun updateIsCurrencySheetShown(showSheet: Boolean) {
        if (isAnyBottomSheetOpen() && showSheet) return

        _uiState.update { it.copy(isCurrencySheetShown = showSheet) }
    }

    /**
     * متد برای نمایش یا مخفی کردن صفحه شروع استفاده می‌شود.
     *
     * @param showSheet نمایش یا مخفی کردن صفحه شروع.
     */
    fun updateIsStartScreenSheetShown(showSheet: Boolean) {
        if (isAnyBottomSheetOpen() && showSheet) return

        _uiState.update { it.copy(isStartScreenSheetShown = showSheet) }
    }

    /**
     * چک می‌کند که آیا هر کدام از صفحات پایینی باز است یا خیر.
     */
    private fun isAnyBottomSheetOpen(): Boolean {
        return _uiState.value.isCurrencySheetShown || _uiState.value.isStartScreenSheetShown
    }
}
