package google.yousefi.cryptoapp.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import google.yousefi.cryptoapp.common.Constants.PARAM_COIN_ID
import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import google.yousefi.cryptoapp.domain.details.GetCoinChartUseCase
import google.yousefi.cryptoapp.domain.details.GetCoinDetailsUseCase
import google.yousefi.cryptoapp.domain.favourites.IsCoinFavouriteUseCase
import google.yousefi.cryptoapp.domain.favourites.ToggleIsCoinFavouriteUseCase
import google.yousefi.cryptoapp.ui.model.ChartPeriod
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel برای نمایش جزئیات ارز با استفاده از ابزار Hilt برای اینژکت کردن وابستگی‌ها.
 *
 * @param getCoinDetailsUseCase UseCase برای دریافت جزئیات ارز بر اساس شناسه آن.
 * @param getCoinChartUseCase UseCase برای دریافت نمودار ارز بر اساس شناسه آن و دوره‌ی زمانی مشخص شده.
 * @param isCoinFavouriteUseCase UseCase برای بررسی اینکه آیا ارز مورد نظر در لیست علاقه‌مندی‌ها است یا خیر.
 * @param toggleIsCoinFavouriteUseCase UseCase برای تغییر وضعیت علاقه‌مندی ارز.
 * @param savedStateHandle Handle ذخیره وضعیت برای نگهداری شناسه ارز.
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val getCoinChartUseCase: GetCoinChartUseCase,
    private val isCoinFavouriteUseCase: IsCoinFavouriteUseCase,
    private val toggleIsCoinFavouriteUseCase: ToggleIsCoinFavouriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val coinId = savedStateHandle.get<String>(PARAM_COIN_ID)
    private val chartPeriodFlow = MutableStateFlow(ChartPeriod.Day)

    init {
        initialiseUiState()
    }

    /**
     * این متد برای به روزرسانی وضعیت UI ViewModel فراخوانی می‌شود.
     * اگر شناسه ارز معتبر نباشد، وضعیت UI به وضعیت خطا تغییر می‌کند.
     * سپس از UseCase‌های مناسب برای دریافت جزئیات ارز، نمودار ارز و وضعیت علاقه‌مندی استفاده می‌شود.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun initialiseUiState() {
        if (coinId == null) {
            _uiState.update { DetailsUiState.Error("شناسه ارز نامعتبر است") }
            return
        }

        val coinDetailsFlow = getCoinDetailsUseCase(coinId = coinId)
        val coinChartFlow = chartPeriodFlow.flatMapLatest { chartPeriod ->
            getCoinChartUseCase(
                coinId = coinId,
                chartPeriod = chartPeriod.stringName
            )
        }
        val isCoinFavouriteFlow = isCoinFavouriteUseCase(
            favouriteCoinId = FavouriteCoinId(id = coinId)
        )

        combine(
            coinDetailsFlow,
            coinChartFlow,
            isCoinFavouriteFlow
        ) { coinDetailsResult, coinChartResult, isCoinFavouriteResult ->
            when {
                coinDetailsResult is Result.Error -> {
                    _uiState.update { DetailsUiState.Error(coinDetailsResult.message) }
                }

                coinChartResult is Result.Error -> {
                    _uiState.update { DetailsUiState.Error(coinChartResult.message) }
                }

                isCoinFavouriteResult is Result.Error -> {
                    _uiState.update { DetailsUiState.Error(isCoinFavouriteResult.message) }
                }

                coinDetailsResult is Result.Success &&
                    coinChartResult is Result.Success &&
                    isCoinFavouriteResult is Result.Success -> {
                    _uiState.update {
                        DetailsUiState.Success(
                            coinDetails = coinDetailsResult.data,
                            coinChart = coinChartResult.data,
                            chartPeriod = chartPeriodFlow.value,
                            isCoinFavourite = isCoinFavouriteResult.data
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * این متد برای به‌روزرسانی دوره‌ی زمانی نمودار فراخوانی می‌شود.
     *
     * @param chartPeriod دوره‌ی زمانی جدید برای نمودار.
     */
    fun updateChartPeriod(chartPeriod: ChartPeriod) {
        chartPeriodFlow.value = chartPeriod
    }

    /**
     * این متد برای تغییر وضعیت علاقه‌مندی ارز فراخوانی می‌شود.
     * اگر شناسه ارز نامعتبر باشد، هیچ عملی انجام نمی‌دهد.
     */
    fun toggleIsCoinFavourite() {
        if (coinId == null) return

        viewModelScope.launch {
            val favouriteCoinId = FavouriteCoinId(id = coinId)
            toggleIsCoinFavouriteUseCase(favouriteCoinId = favouriteCoinId)
        }
    }
}

