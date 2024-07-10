package google.yousefi.cryptoapp.ui.previewdata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import google.yousefi.cryptoapp.model.SearchCoin
import google.yousefi.cryptoapp.ui.previewdata.CoinSearchPreviewData.searchResults
import google.yousefi.cryptoapp.ui.screen.search.SearchUiState
import kotlinx.collections.immutable.persistentListOf

/**
 * ارائه‌دهنده‌ی پیش‌نمایش برای وضعیت UI جستجو.
 */
class SearchUiStatePreviewProvider : PreviewParameterProvider<SearchUiState> {
    /**
     * توالی مقادیر مختلف برای نمایش وضعیت‌های مختلف UI جستجو.
     */
    override val values = sequenceOf(
        SearchUiState(
            searchResults = searchResults,
            queryHasNoResults = false
        ),
        SearchUiState(
            searchResults = persistentListOf(),
            queryHasNoResults = true
        ),
        SearchUiState(
            errorMessage = "Error searching coins"
        ),
        SearchUiState(
            isSearching = true
        )
    )
}
/**
 * شیء `CoinSearchPreviewData` شامل نتایج جستجو برای استفاده در پیش‌نمایش UI.
 */
private object CoinSearchPreviewData {
    val searchResults = persistentListOf(
        SearchCoin(
            id = "bitcoin",
            symbol = "BTC",
            name = "Bitcoin",
            imageUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg"
        ),
        SearchCoin(
            id = "ethereum",
            symbol = "ETH",
            name = "Ethereum",
            imageUrl = "https://cdn.coinranking.com/rk4RKHOuW/eth.svg"
        ),
        SearchCoin(
            id = "tether",
            symbol = "USDT",
            name = "Tether",
            imageUrl = "https://cdn.coinranking.com/mgHqwlCLj/usdt.svg"
        )
    )
}
