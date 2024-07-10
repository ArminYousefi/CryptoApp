package google.yousefi.cryptoapp.common

import java.math.BigDecimal

/**
 * متدی برای تبدیل رشته به BigDecimal پس از حذف کاما و فضاهای خالی. در صورت وجود خطا یا مقدار null، صفر باز می‌گردد.
 *
 * @receiver String? رشته ورودی که ممکن است null باشد.
 * @return BigDecimal مقدار BigDecimal به دست آمده از رشته، یا صفر در صورت وجود خطا یا مقدار null.
 */
fun String?.toSanitisedBigDecimalOrZero(): BigDecimal {
    return try {
        if (this != null) {
            val sanitisedString = this
                .filterNot { it == ',' }
                .trim()

            BigDecimal(sanitisedString)
        } else {
            BigDecimal.ZERO
        }
    } catch (e: NumberFormatException) {
        BigDecimal.ZERO
    }
}

/**
 * متدی برای تبدیل رشته به BigDecimal پس از حذف کاما و فضاهای خالی. در صورت وجود خطا یا مقدار null، مقدار null باز می‌گردد.
 *
 * @receiver String? رشته ورودی که ممکن است null باشد.
 * @return BigDecimal? مقدار BigDecimal به دست آمده از رشته، یا null در صورت وجود خطا یا مقدار null.
 */
fun String?.toSanitisedBigDecimalOrNull(): BigDecimal? {
    return try {
        if (this != null) {
            val sanitisedString = this
                .filterNot { it == ',' }
                .trim()

            BigDecimal(sanitisedString)
        } else {
            null
        }
    } catch (e: NumberFormatException) {
        null
    }
}

/**
 * متدی برای یافتن کمترین مقدار در یک لیست از BigDecimal ها. در صورت خالی بودن لیست، صفر باز می‌گردد.
 *
 * @receiver List<BigDecimal> لیست BigDecimal ها.
 * @return BigDecimal کمترین مقدار در لیست، یا صفر در صورت خالی بودن لیست.
 */
fun List<BigDecimal>.minOrZero(): BigDecimal {
    return this.minOrNull() ?: BigDecimal.ZERO
}

/**
 * متدی برای یافتن بیشترین مقدار در یک لیست از BigDecimal ها. در صورت خالی بودن لیست، صفر باز می‌گردد.
 *
 * @receiver List<BigDecimal> لیست BigDecimal ها.
 * @return BigDecimal بیشترین مقدار در لیست، یا صفر در صورت خالی بودن لیست.
 */
fun List<BigDecimal>.maxOrZero(): BigDecimal {
    return this.maxOrNull() ?: BigDecimal.ZERO
}

