package google.yousefi.cryptoapp.ui.previewdata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import google.yousefi.cryptoapp.data.source.local.preferences.global.StartScreen
import google.yousefi.cryptoapp.ui.screen.settings.SettingsUiState

/**
 * ارائه‌دهنده‌ی پیش‌نمایش برای وضعیت UI تنظیمات.
 */
class SettingsUiStatePreviewProvider : PreviewParameterProvider<SettingsUiState> {

    /**
     * ارائه‌دهنده‌ی پیش‌نمایش برای وضعیت UI تنظیمات.
     */
    override val values = sequenceOf(
        SettingsUiState(
            startScreen = StartScreen.Favourites
        ),
        SettingsUiState(
            errorMessage = "No internet connection"
        ),
        SettingsUiState(
            isLoading = true
        )
    )
}
