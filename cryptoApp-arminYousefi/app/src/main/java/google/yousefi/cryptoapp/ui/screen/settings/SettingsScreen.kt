package google.yousefi.cryptoapp.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Launch
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.CurrencyPound
import androidx.compose.material.icons.rounded.Euro
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import google.yousefi.cryptoapp.BuildConfig
import google.yousefi.cryptoapp.R
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.preferences.global.StartScreen
import google.yousefi.cryptoapp.ui.component.ErrorState
import google.yousefi.cryptoapp.ui.component.LoadingIndicator
import google.yousefi.cryptoapp.ui.previewdata.SettingsUiStatePreviewProvider
import google.yousefi.cryptoapp.ui.screen.settings.component.CurrencyBottomSheet
import google.yousefi.cryptoapp.ui.screen.settings.component.SettingsItem
import google.yousefi.cryptoapp.ui.screen.settings.component.StartScreenBottomSheet
import google.yousefi.cryptoapp.ui.theme.AppTheme
import kotlinx.coroutines.launch

/**
 * صفحه‌ی تنظیمات که شامل تنظیمات مختلف مانند ارزش، نمایش وضعیت ارزش، صفحه‌ی شروع و نمایش وضعیت صفحه‌ی شروع است.
 *
 * @param onNavigateUp تابعی که برای بازگشت به صفحه‌ی قبلی فراخوانی می‌شود
 * @param viewModel ViewModel استفاده شده برای ارتباط با داده‌های تنظیمات
 */
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onUpdateCurrency = { currency ->
            viewModel.updateCurrency(currency)
        },
        onUpdateIsCurrencySheetShown = { showSheet ->
            viewModel.updateIsCurrencySheetShown(showSheet)
        },
        onUpdateStartScreen = { startScreen ->
            viewModel.updateStartScreen(startScreen)
        },
        onUpdateIsStartScreenSheetShown = { showSheet ->
            viewModel.updateIsStartScreenSheetShown(showSheet)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onNavigateUp: () -> Unit,
    onUpdateCurrency: (Currency) -> Unit,
    onUpdateIsCurrencySheetShown: (Boolean) -> Unit,
    onUpdateStartScreen: (StartScreen) -> Unit,
    onUpdateIsStartScreenSheetShown: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencySheetState = rememberModalBottomSheetState()
    val startScreenSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SettingsTopBar(onNavigateUp = onNavigateUp)
        },
        modifier = modifier.fillMaxSize()
    ) { scaffoldPadding ->
        when {
            uiState.isLoading -> {
                LoadingIndicator(modifier = Modifier.padding(scaffoldPadding))
            }

            uiState.errorMessage != null -> {
                ErrorState(
                    message = stringResource(R.string.error_state_settings),
                    modifier = Modifier.padding(scaffoldPadding)
                )
            }

            else -> {
                SettingsContent(
                    currency = uiState.currency,
                    onUpdateIsCurrencySheetShown = onUpdateIsCurrencySheetShown,
                    startScreen = uiState.startScreen,
                    onUpdateIsStartScreenSheetShown = onUpdateIsStartScreenSheetShown,
                    modifier = Modifier.padding(scaffoldPadding)
                )

                if (uiState.isCurrencySheetShown) {
                    CurrencyBottomSheet(
                        sheetState = currencySheetState,
                        selectedCurrency = uiState.currency,
                        onCurrencySelected = { currency ->
                            onUpdateCurrency(currency)

                            scope.launch {
                                currencySheetState.hide()
                            }.invokeOnCompletion {
                                if (!currencySheetState.isVisible) {
                                    onUpdateIsCurrencySheetShown(false)
                                }
                            }
                        },
                        onDismissRequest = { onUpdateIsCurrencySheetShown(false) }
                    )
                }

                if (uiState.isStartScreenSheetShown) {
                    StartScreenBottomSheet(
                        sheetState = startScreenSheetState,
                        selectedStartScreen = uiState.startScreen,
                        onStartScreenSelected = { startScreen ->
                            onUpdateStartScreen(startScreen)

                            scope.launch {
                                startScreenSheetState.hide()
                            }.invokeOnCompletion {
                                if (!startScreenSheetState.isVisible) {
                                    onUpdateIsStartScreenSheetShown(false)
                                }
                            }
                        },
                        onDismissRequest = { onUpdateIsStartScreenSheetShown(false) })
                }
            }
        }
    }
}

@Composable
fun SettingsContent(
    currency: Currency,
    onUpdateIsCurrencySheetShown: (Boolean) -> Unit,
    startScreen: StartScreen,
    onUpdateIsStartScreenSheetShown: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.settings_group_preferences),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
        )

        SettingsItem(
            title = stringResource(R.string.settings_title_currency),
            subtitle = stringResource(currency.nameId),
            leadingIcon = when (currency) {
                Currency.USD -> Icons.Rounded.AttachMoney
                Currency.GBP -> Icons.Rounded.CurrencyPound
                Currency.EUR -> Icons.Rounded.Euro
            },
            trailingIcon = Icons.Rounded.ChevronRight,
            onClick = { onUpdateIsCurrencySheetShown(true) }
        )

        SettingsItem(
            title = stringResource(R.string.settings_title_start_screen),
            subtitle = stringResource(startScreen.nameId),
            leadingIcon = when (startScreen) {
                StartScreen.Market -> Icons.Rounded.BarChart
                StartScreen.Favourites -> Icons.Rounded.Favorite
                StartScreen.Search -> Icons.Rounded.Search
            },
            trailingIcon = Icons.Rounded.ChevronRight,
            onClick = { onUpdateIsStartScreenSheetShown(true) }
        )

        HorizontalDivider(color = MaterialTheme.colorScheme.primaryContainer)

        Text(
            text = stringResource(R.string.settings_group_about),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
        )

        SettingsItem(
            title = stringResource(R.string.settings_title_version),
            subtitle = BuildConfig.VERSION_NAME,
            leadingIcon = Icons.Rounded.Smartphone,
            onClick = {}
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings_screen),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.cd_top_bar_back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun SettingsScreenPreview(
    @PreviewParameter(SettingsUiStatePreviewProvider::class) uiState: SettingsUiState
) {
    AppTheme {
        SettingsScreen(
            uiState = uiState,
            onNavigateUp = {},
            onUpdateCurrency = {},
            onUpdateIsCurrencySheetShown = {},
            onUpdateStartScreen = {},
            onUpdateIsStartScreenSheetShown = {}
        )
    }
}
