package google.yousefi.cryptoapp.model

/**
 * این کلاس برای نگهداری اطلاعات سکه‌های جستجو شده مانند شناسه، نام، نماد و تصویر آن‌ها است.
 *
 * @property id شناسه یکتای سکه.
 * @property name نام سکه.
 * @property symbol نماد سکه.
 * @property imageUrl آدرس تصویر نمایش داده شده برای سکه.
 */
data class SearchCoin(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String
)
