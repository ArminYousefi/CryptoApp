package google.yousefi.cryptoapp.ui.screen.details

import google.yousefi.cryptoapp.model.CoinChart
import google.yousefi.cryptoapp.model.CoinDetails
import google.yousefi.cryptoapp.ui.model.ChartPeriod

/**
 * وضعیت UI برای صفحه‌ی جزئیات شامل انواع مختلف مانند موفقیت، خطا و بارگذاری.
 */
sealed interface DetailsUiState {
    /**
     * وضعیت موفقیت که شامل جزئیات سکه، نمودار سکه، دوره نمودار و وضعیت محبوب بودن سکه است.
     *
     * @property coinDetails جزئیات سکه
     * @property coinChart نمودار سکه
     * @property chartPeriod دوره نمودار
     * @property isCoinFavourite وضعیت محبوب بودن سکه
     */
    data class Success(
        val coinDetails: CoinDetails,
        val coinChart: CoinChart,
        val chartPeriod: ChartPeriod,
        val isCoinFavourite: Boolean
    ) : DetailsUiState

    /**
     * وضعیت خطا که شامل پیغام خطا است.
     *
     * @property message پیغام خطا
     */
    data class Error(val message: String?) : DetailsUiState

    /**
     * وضعیت بارگذاری
     */
    data object Loading : DetailsUiState
}
