package google.yousefi.cryptoapp.ui.screen.market

import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import google.yousefi.cryptoapp.R
import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.domain.market.GetCoinsUseCase
import google.yousefi.cryptoapp.domain.preferences.GetMarketPreferencesUseCase
import google.yousefi.cryptoapp.domain.market.GetMarketStatsUseCase
import google.yousefi.cryptoapp.domain.preferences.GetUserPreferencesUseCase
import google.yousefi.cryptoapp.domain.market.UpdateCachedCoinsUseCase
import google.yousefi.cryptoapp.domain.preferences.UpdateMarketCoinSortUseCase
import google.yousefi.cryptoapp.ui.model.TimeOfDay
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

/**
 * ViewModel برای نمایش اطلاعات بازار ارزهای دیجیتال با استفاده از ابزار Hilt برای اینژکت کردن وابستگی‌ها.
 *
 * @param getCoinsUseCase UseCase برای دریافت لیست ارزهای دیجیتال.
 * @param getMarketStatsUseCase UseCase برای دریافت آمار بازار مربوط به ارزهای دیجیتال.
 * @param updateCachedCoinsUseCase UseCase برای به روزرسانی ارزهای دیجیتال در حافظه‌ی موقت.
 * @param getUserPreferencesUseCase UseCase برای دریافت تنظیمات کاربر.
 * @param getMarketPreferencesUseCase UseCase برای دریافت تنظیمات بازار مربوط به ارزهای دیجیتال.
 * @param updateMarketCoinSortUseCase UseCase برای به روزرسانی نوع مرتب‌سازی ارزهای دیجیتال در بازار.
 */
@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val getMarketStatsUseCase: GetMarketStatsUseCase,
    private val updateCachedCoinsUseCase: UpdateCachedCoinsUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val getMarketPreferencesUseCase: GetMarketPreferencesUseCase,
    private val updateMarketCoinSortUseCase: UpdateMarketCoinSortUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MarketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initialiseUiState()
    }

    /**
     * این متد برای اولین بارهایی که ViewModel ایجاد می‌شود، فراخوانی می‌شود تا وضعیت UI را مقداردهی اولیه کند.
     * در این متد از UseCase‌های مختلف برای دریافت لیست ارزهای دیجیتال، آمار بازار، و تنظیمات مربوط به ارزهای دیجیتال استفاده می‌شود.
     * اطلاعات دریافت شده به UI منعکس می‌شود.
     */
    fun initialiseUiState() {
        _uiState.update { it.copy(isLoading = true) }

        getCoinsUseCase().onEach { coinsResult ->
            when (coinsResult) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            coins = coinsResult.data.toImmutableList(),
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        val errorMessages = it.errorMessageIds + R.string.error_local_coins

                        it.copy(
                            errorMessageIds = errorMessages,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)

        getUserPreferencesUseCase().onEach { userPreferences ->
            updateCachedCoins(
                coinSort = getMarketPreferencesUseCase().first().coinSort,
                currency = userPreferences.currency
            )
        }.launchIn(viewModelScope)

        getMarketPreferencesUseCase().onEach { marketPreferences ->
            _uiState.update {
                it.copy(
                    coinSort = marketPreferences.coinSort
                )
            }

            updateCachedCoins(
                coinSort = marketPreferences.coinSort,
                currency = getUserPreferencesUseCase().first().currency
            )
        }.launchIn(viewModelScope)

        getCurrentHourFlow().onEach { currentHour ->
            val timeOfDay = calculateTimeOfDay(hour = currentHour)

            _uiState.update {
                it.copy(timeOfDay = timeOfDay)
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            val marketStatsResult = getMarketStatsUseCase()

            when (marketStatsResult) {
                is Result.Success -> {
                    val marketStats = marketStatsResult.data

                    _uiState.update {
                        it.copy(
                            marketCapChangePercentage24h = marketStats.marketCapChangePercentage24h
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        val errorMessages = it.errorMessageIds + R.string.error_market_stats

                        it.copy(
                            errorMessageIds = errorMessages
                        )
                    }
                }
            }
        }
    }

    /**
     * این متد برای انجام عملیات تازه‌سازی لیست ارزهای دیجیتال فراخوانی می‌شود.
     * در این متد، ابتدا وضعیت تازه‌سازی را به true تغییر می‌دهد، سپس با تأخیری 250 میلی‌ثانیه‌ای اقدام به
     * دریافت دوباره‌ی تنظیمات کاربر و بازار مربوط به ارزهای دیجیتال می‌کند و اطلاعات دریافت شده را برای به روزرسانی UI استفاده می‌کند.
     */
    fun pullRefreshCoins() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            delay(250.milliseconds)

            val userPreferences = getUserPreferencesUseCase().first()
            val marketPreferences = getMarketPreferencesUseCase().first()

            updateCachedCoins(
                coinSort = marketPreferences.coinSort,
                currency = userPreferences.currency
            )

            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    /**
     * این متد برای به‌روزرسانی نوع مرتب‌سازی ارزهای دیجیتال در بازار فراخوانی می‌شود.
     *
     * @param coinSort نوع مرتب‌سازی جدید برای ارزهای دیجیتال.
     */
    fun updateCoinSort(coinSort: CoinSort) {
        viewModelScope.launch {
            updateMarketCoinSortUseCase(coinSort = coinSort)
        }
    }

    /**
     * این متد برای حذف پیام خطاها از UI فراخوانی می‌شود.
     *
     * @param dismissedErrorMessageId شناسه پیام خطا که باید حذف شود.
     */
    fun dismissErrorMessage(@StringRes dismissedErrorMessageId: Int) {
        _uiState.update {
            val errorMessageIds = it.errorMessageIds.filter { errorMessageId ->
                errorMessageId != dismissedErrorMessageId
            }
            it.copy(errorMessageIds = errorMessageIds)
        }
    }

    /**
     * متد داخلی برای محاسبه زمان روز بر اساس ساعت فراخوانی می‌شود.
     *
     * @param hour ساعت فعلی.
     * @return TimeOfDay نتیجه محاسبه زمان روز بر اساس ساعت.
     */
    @VisibleForTesting
    fun calculateTimeOfDay(hour: Int): TimeOfDay {
        return when (hour) {
            in 4..11 -> TimeOfDay.Morning
            in 12..17 -> TimeOfDay.Afternoon
            else -> TimeOfDay.Evening
        }
    }

    /**
     * متد داخلی برای دریافت جریان جریان ساعت فعلی است.
     *
     * @return Flow<Int> جریانی از ساعت فعلی.
     */
    private fun getCurrentHourFlow(): Flow<Int> = flow {
        while (true) {
            val currentHour = LocalTime.now().hour
            emit(currentHour)
            delay(5.minutes.inWholeMilliseconds)
        }
    }

    /**
     * متد داخلی برای به‌روزرسانی ارزهای دیجیتال در حافظه‌ی موقت فراخوانی می‌شود.
     * اگر عملیات به درستی انجام نشود، پیام خطای مناسب به وضعیت UI اضافه می‌شود.
     *
     * @param coinSort نوع مرتب‌سازی برای ارزهای دیجیتال.
     * @param currency واحد پول بر
     * **/
    private suspend fun updateCachedCoins(coinSort: CoinSort, currency: Currency) {
        val result = updateCachedCoinsUseCase(coinSort = coinSort, currency = currency)

        if (result is Result.Error) {
            _uiState.update {
                val errorMessages = it.errorMessageIds + R.string.error_network_coins
                it.copy(errorMessageIds = errorMessages)
            }
        }
    }
}
