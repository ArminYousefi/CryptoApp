package google.yousefi.cryptoapp.ui.screen.favourites

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import google.yousefi.cryptoapp.R
import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import google.yousefi.cryptoapp.domain.favourites.GetFavouriteCoinIdsUseCase
import google.yousefi.cryptoapp.domain.favourites.GetFavouriteCoinsUseCase
import google.yousefi.cryptoapp.domain.preferences.GetFavouritesPreferencesUseCase
import google.yousefi.cryptoapp.domain.preferences.GetUserPreferencesUseCase
import google.yousefi.cryptoapp.domain.favourites.UpdateCachedFavouriteCoinsUseCase
import google.yousefi.cryptoapp.domain.preferences.UpdateFavouritesCoinSortUseCase
import google.yousefi.cryptoapp.domain.preferences.UpdateIsFavouritesCondensedUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/**
 * ViewModel برای نمایش ارزهای مورد علاقه با استفاده از ابزار Hilt برای اینژکت کردن وابستگی‌ها.
 *
 * @param getFavouriteCoinsUseCase UseCase برای دریافت لیست ارزهای مورد علاقه.
 * @param updateCachedFavouriteCoinsUseCase UseCase برای به روزرسانی ارزهای مورد علاقه در حافظه‌ی موقت.
 * @param getFavouriteCoinIdsUseCase UseCase برای دریافت شناسه‌های ارزهای مورد علاقه.
 * @param getUserPreferencesUseCase UseCase برای دریافت تنظیمات کاربر.
 * @param getFavouritesPreferencesUseCase UseCase برای دریافت تنظیمات مربوط به ارزهای مورد علاقه.
 * @param updateIsFavouritesCondensedUseCase UseCase برای به روزرسانی وضعیت فشرده‌سازی ارزهای مورد علاقه.
 * @param updateFavouritesCoinSortUseCase UseCase برای به روزرسانی نوع مرتب‌سازی ارزهای مورد علاقه.
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouriteCoinsUseCase: GetFavouriteCoinsUseCase,
    private val updateCachedFavouriteCoinsUseCase: UpdateCachedFavouriteCoinsUseCase,
    private val getFavouriteCoinIdsUseCase: GetFavouriteCoinIdsUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val getFavouritesPreferencesUseCase: GetFavouritesPreferencesUseCase,
    private val updateIsFavouritesCondensedUseCase: UpdateIsFavouritesCondensedUseCase,
    private val updateFavouritesCoinSortUseCase: UpdateFavouritesCoinSortUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouritesUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * این متد برای اولین بارهایی که ViewModel ایجاد می‌شود، فراخوانی می‌شود تا وضعیت UI را مقداردهی اولیه کند.
     * در این متد از UseCase‌های مختلف برای دریافت اطلاعات ارزهای مورد علاقه، شناسه‌های ارزهای مورد علاقه و تنظیمات
     * مربوط به ارزهای مورد علاقه استفاده می‌شود و اطلاعات در UI نمایش داده می‌شود.
     */
    fun initialiseUiState() {
        _uiState.update { it.copy(isLoading = true) }

        val favouriteCoinIdsFlow = getFavouriteCoinIdsUseCase()
        val userPreferencesFlow = getUserPreferencesUseCase()
        val favouritesPreferencesFlow = getFavouritesPreferencesUseCase()

        combine(
            favouriteCoinIdsFlow,
            userPreferencesFlow,
            favouritesPreferencesFlow
        ) { favouriteCoinIdsResult, userPreferences, favouritesPreferences ->
            when (favouriteCoinIdsResult) {
                is Result.Success -> {
                    updateCachedFavouriteCoins(
                        coinIds = favouriteCoinIdsResult.data,
                        currency = userPreferences.currency,
                        coinSort = favouritesPreferences.coinSort
                    )
                }

                is Result.Error -> {
                    addErrorMessage(R.string.error_local_favourite_coin_ids)
                }
            }
        }.launchIn(viewModelScope)

        getFavouritesPreferencesUseCase().onEach { favouritesPreferences ->
            _uiState.update {
                it.copy(
                    isFavouritesCondensed = favouritesPreferences.isFavouritesCondensed,
                    coinSort = favouritesPreferences.coinSort
                )
            }
        }.launchIn(viewModelScope)

        getFavouriteCoinsUseCase().onEach { favouriteCoinsResult ->
            when (favouriteCoinsResult) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            favouriteCoins = favouriteCoinsResult.data.toImmutableList(),
                            isFavouriteCoinsEmpty = favouriteCoinsResult.data.isEmpty()
                        )
                    }
                }

                is Result.Error -> {
                    addErrorMessage(R.string.error_local_favourite_coins)
                }
            }

            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    /**
     * این متد برای انجام عملیات تازه‌سازی ارزهای مورد علاقه فراخوانی می‌شود.
     * در این متد، ابتدا وضعیت تازه‌سازی را به true تغییر می‌دهد، سپس با تأخیری 250 میلی‌ثانیه‌ای اقدام به
     * دریافت دوباره‌ی شناسه‌های ارزهای مورد علاقه، تنظیمات کاربر و تنظیمات مربوط به ارزهای مورد علاقه می‌کند.
     * سپس اطلاعات دریافت شده را برای به روزرسانی UI استفاده می‌کند.
     */
    fun pullRefreshFavouriteCoins() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            delay(250.milliseconds)

            val favouriteCoinIdsResult = getFavouriteCoinIdsUseCase().first()
            val userPreferences = getUserPreferencesUseCase().first()
            val favouritesPreferences = getFavouritesPreferencesUseCase().first()

            when (favouriteCoinIdsResult) {
                is Result.Success -> {
                    updateCachedFavouriteCoins(
                        coinIds = favouriteCoinIdsResult.data,
                        currency = userPreferences.currency,
                        coinSort = favouritesPreferences.coinSort
                    )
                }

                is Result.Error -> {
                    addErrorMessage(R.string.error_local_favourite_coin_ids)
                }
            }

            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    /**
     * این متد برای حذف پیام خطاها از UI فراخوانی می‌شود.
     *
     * @param dismissedErrorMessageId شناسه پیام خطا که باید حذف شود.
     */
    fun dismissErrorMessage(@StringRes dismissedErrorMessageId: Int) {
        _uiState.update {
            val errorMessageIds = it.errorMessageIds.filterNot { errorMessageId ->
                errorMessageId == dismissedErrorMessageId
            }
            it.copy(errorMessageIds = errorMessageIds)
        }
    }

    /**
     * این متد برای به‌روزرسانی وضعیت فشرده‌سازی ارزهای مورد علاقه فراخوانی می‌شود.
     *
     * @param isCondensed مقدار boolean که نشان دهنده وضعیت فشرده‌سازی است.
     */
    fun updateIsFavouritesCondensed(isCondensed: Boolean) {
        viewModelScope.launch {
            updateIsFavouritesCondensedUseCase(isCondensed = isCondensed)
        }
    }

    /**
     * این متد برای به‌روزرسانی نوع مرتب‌سازی ارزهای مورد علاقه فراخوانی می‌شود.
     *
     * @param coinSort نوع مرتب‌سازی جدید برای ارزهای مورد علاقه.
     */
    fun updateCoinSort(coinSort: CoinSort) {
        viewModelScope.launch {
            updateFavouritesCoinSortUseCase(coinSort = coinSort)
        }
    }

    /**
     * این متد برای به‌روزرسانی ارزهای مورد علاقه در حافظه‌ی موقت فراخوانی می‌شود.
     * اگر عملیات به درستی انجام نشود، پیام خطای مناسب به وضعیت UI اضافه می‌شود.
     *
     * @param coinIds لیستی از شناسه‌های ارزهای مورد علاقه.
     * @param currency واحد پولی برای نمایش ارزها.
     * @param coinSort نوع مرتب‌سازی برای ارزهای مورد علاقه.
     */
    private suspend fun updateCachedFavouriteCoins(
        coinIds: List<FavouriteCoinId>,
        currency: Currency,
        coinSort: CoinSort
    ) {
        val updateCachedFavouriteCoinResult = updateCachedFavouriteCoinsUseCase(
            coinIds = coinIds,
            currency = currency,
            coinSort = coinSort
        )

        if (updateCachedFavouriteCoinResult is Result.Error) {
            addErrorMessage(R.string.error_network_favourite_coins)
        }
    }

    /**
     * این متد برای اضافه کردن پیام خطا به وضعیت UI فراخوانی می‌شود.
     *
     * @param errorMessageId شناسه پیام خطا که باید به وضعیت UI اضافه شود.
     */
    private fun addErrorMessage(@StringRes errorMessageId: Int) {
        _uiState.update {
            val errorMessages = it.errorMessageIds + errorMessageId
            it.copy(errorMessageIds = errorMessages)
        }
    }
}
