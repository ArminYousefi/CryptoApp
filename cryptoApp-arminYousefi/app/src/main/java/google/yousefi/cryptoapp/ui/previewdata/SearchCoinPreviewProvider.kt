package google.yousefi.cryptoapp.ui.previewdata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import google.yousefi.cryptoapp.model.SearchCoin


/**
 * ارائه‌دهنده‌ی پیش‌نمایش برای مقادیر جستجوی سکه SearchCoin.
 */
class SearchCoinPreviewProvider : PreviewParameterProvider<SearchCoin> {
    /**
     * توالی مقادیر مختلف برای نمایش سکه‌های مختلف در جستجو.
     */
    override val values = sequenceOf(
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
