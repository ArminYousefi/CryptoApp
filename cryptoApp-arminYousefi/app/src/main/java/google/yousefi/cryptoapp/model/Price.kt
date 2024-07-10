package google.yousefi.cryptoapp.model

import google.yousefi.cryptoapp.common.toSanitisedBigDecimalOrZero
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Locale
import java.util.Currency as CurrencyCode

/**
 * این کلاس برای نمایش یک قیمت به همراه واحد پول مورد استفاده است و امکان مقایسه
 * آن با دیگر قیمت‌ها را فراهم می‌کند.
 *
 * @property price مقدار عددی قیمت، اگر `null` باشد مقدار پیش‌فرض آن صفر است.
 * @property currency واحد پول برای قیمت، به صورت پیش‌فرض USD است.
 * @property amount مقدار عددی قیمت به صورت عدد BigDecimal.
 * @property formattedAmount نمایش قیمت به صورت فرمت‌بندی شده بر اساس مقدار و با استفاده از نماد واحد پول مورد استفاده.
 */
data class Price(val price: String?, val currency: Currency = Currency.USD) : Comparable<Price> {
    val amount: BigDecimal = price.toSanitisedBigDecimalOrZero()

    private val currencyFormat: DecimalFormat = getCurrencyFormat()

    val formattedAmount: String = when {
        // Must be checked in this order
        price.isNullOrBlank() -> "${currency.symbol}--"
        amount in belowOneRange -> currencyFormat.format(amount)
        amount in belowMillionRange -> currencyFormat.format(amount)
        else -> formatLargeAmount()
    }

    /**
     * تولید یک فرمت‌دهی مناسب برای واحد پول فعلی بر اساس مقدار قیمت.
     *
     * @return فرمت‌دهی شده برای واحد پول فعلی.
     */
    private fun getCurrencyFormat(): DecimalFormat {
        val currencyFormat = DecimalFormat.getCurrencyInstance(Locale.US) as DecimalFormat

        val decimalPlaces = if (amount in belowOneRange) 6 else 2
        val currencyCode = try {
            CurrencyCode.getInstance(currency.name)
        } catch (e: IllegalArgumentException) {
            CurrencyCode.getInstance(Currency.USD.name)
        }

        currencyFormat.minimumFractionDigits = decimalPlaces
        currencyFormat.maximumFractionDigits = decimalPlaces
        currencyFormat.currency = currencyCode

        return currencyFormat
    }

    /**
     * فرمت‌دهی برای نمایش اعداد بزرگتر از یک میلیون به شکل مختصر.
     *
     * @return نمایش فرمت‌دهی شده برای اعداد بزرگتر از یک میلیون.
     */
    private fun formatLargeAmount(): String {
        val roundedAmount = amount.round(MathContext(5, roundingMode))

        val divisor = when (roundedAmount) {
            in millionRange -> million
            in billionRange -> billion
            in trillionRange -> trillion
            in quadrillionRange -> quadrillion
            else -> BigDecimal.ONE
        }

        val symbol = when (roundedAmount) {
            in millionRange -> "M"
            in billionRange -> "B"
            in trillionRange -> "T"
            in quadrillionRange -> "Q"
            else -> ""
        }

        val shortenedAmount = amount.divide(divisor, 2, roundingMode)
        return currencyFormat.format(shortenedAmount) + symbol
    }

    /**
     * مقایسه این قیمت با یک قیمت دیگر بر اساس مقدار عددی آن.
     *
     * @param other قیمت دیگر برای مقایسه.
     * @return عدد صحیح منفی، صفر یا مثبت که نشان‌دهنده رابطه بین دو قیمت است.
     */
    override fun compareTo(other: Price) = amount.compareTo(other.amount)

    companion object {
        private val million = BigDecimal("1000000")
        private val billion = BigDecimal("1000000000")
        private val trillion = BigDecimal("1000000000000")
        private val quadrillion = BigDecimal("1000000000000000")
        private val quintillion = BigDecimal("1000000000000000000")

        private val belowOneRange = BigDecimal("-1.00")..BigDecimal("1.00")
        private val belowMillionRange = BigDecimal("1.00")..<million
        private val millionRange = million..<billion
        private val billionRange = billion..<trillion
        private val trillionRange = trillion..<quadrillion
        private val quadrillionRange = quadrillion..<quintillion

        val roundingMode = RoundingMode.HALF_EVEN
    }
}
