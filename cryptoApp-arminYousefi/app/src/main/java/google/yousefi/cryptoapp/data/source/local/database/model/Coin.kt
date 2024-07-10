package google.yousefi.cryptoapp.data.source.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.model.Price

/**
 * این کلاس Coin به عنوان یک Entity در پایگاه داده Room برای نمایش اطلاعات یک ارز دیجیتال مورد استفاده قرار می‌گیرد.
 *
 * @Entity برچسبی است که برای نشان دادن این که این کلاس به عنوان یک Entity در Room استفاده می‌شود، استفاده می‌شود.
 * @param id شناسه یکتای ارز دیجیتال.
 * @param name نام ارز دیجیتال.
 * @param symbol نماد ارز دیجیتال.
 * @param imageUrl آدرس URL تصویر مرتبط با ارز دیجیتال.
 * @param currentPrice قیمت فعلی ارز دیجیتال.
 * @param priceChangePercentage24h درصد تغییر قیمت در 24 ساعت گذشته برای ارز دیجیتال.
 */
@Entity
data class Coin(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val currentPrice: Price,
    val priceChangePercentage24h: Percentage
)
