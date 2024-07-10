package google.yousefi.cryptoapp.model

import google.yousefi.cryptoapp.common.toSanitisedBigDecimalOrZero
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

/**
 * این کلاس برای نمایش یک درصد و عملیات مرتبط با آن مانند محاسبه مقدار، گرد کردن، تشخیص
 * مثبت یا منفی بودن و فرمت‌بندی می‌باشد.
 *
 * @property percentage درصد مورد نظر برای نمایش، اگر `null` باشد به صورت پیش‌فرض برابر است با "-- %".
 * @property amount مقدار درصد به صورت عددی، اگر ورودی `null` باشد، مقدار پیش‌فرض آن صفر است.
 * @property roundedAmount مقدار گرد شده درصد با دقت دو رقم اعشار.
 * @property isPositive نشان‌دهنده مثبت بودن مقدار گرد شده درصد است.
 * @property isNegative نشان‌دهنده منفی بودن مقدار گرد شده درصد است.
 * @property formattedAmount مقدار درصد فرمت‌بندی شده بر اساس شرایط مختلف اعمال شده، مانند مثبت یا منفی بودن.
 */
data class Percentage(private val percentage: String?) {
    companion object {
        private val percentageFormat: NumberFormat =
            NumberFormat.getPercentInstance(Locale.US).apply {
                isGroupingUsed = true
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }
    }

    val amount: BigDecimal = percentage.toSanitisedBigDecimalOrZero()

    private val roundedAmount: BigDecimal = amount.setScale(2, RoundingMode.HALF_EVEN)
    val isPositive: Boolean = roundedAmount.signum() > 0
    val isNegative: Boolean = roundedAmount.signum() < 0

    val formattedAmount: String =
        when {
            percentage == null -> "-- %"
            isNegative -> percentageFormat.format(amount.divide(BigDecimal("100")))
            else -> "+" + percentageFormat.format(amount.divide(BigDecimal("100")))
        }
}
