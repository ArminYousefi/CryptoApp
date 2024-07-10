package google.yousefi.cryptoapp.ui.previewdata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.model.Price

/**
 * ارائه‌دهنده‌ی پیش‌نمایش برای موارد پارامتری از نوع Coin.
 */
class CoinPreviewProvider : PreviewParameterProvider<Coin> {

    /**
     * مقادیر پیش‌فرض برای پیش‌نمایش، شامل سکانس‌های مختلف از نوع Coin.
     */
    override val values = sequenceOf(
        Coin(
            id = "bitcoin",
            symbol = "BTC",
            name = "Bitcoin",
            imageUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
            currentPrice = Price("29446.336548759988"),
            priceChangePercentage24h = Percentage("0.76833")
        ),
        Coin(
            id = "ethereum",
            symbol = "ETH",
            name = "Ethereum",
            imageUrl = "https://cdn.coinranking.com/rk4RKHOuW/eth.svg",
            currentPrice = Price("1875.473083380222"),
            priceChangePercentage24h = Percentage("-1.11008")
        ),
        Coin(
            id = "tether",
            symbol = "USDT",
            name = "Tether",
            imageUrl = "https://cdn.coinranking.com/mgHqwlCLj/usdt.svg",
            currentPrice = Price("1.00"),
            priceChangePercentage24h = Percentage("0.00")
        )
    )
}
