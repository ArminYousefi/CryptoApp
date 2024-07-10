package google.yousefi.cryptoapp.data.source.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * این کلاس FavouriteCoinId به عنوان یک Entity در پایگاه داده Room برای نگهداری شناسه‌های ارزهای دیجیتال مورد علاقه استفاده می‌شود.
 *
 * @Entity برچسبی است که برای نشان دادن این که این کلاس به عنوان یک Entity در Room استفاده می‌شود، استفاده می‌شود.
 * @param id شناسه یکتای ارز دیجیتال.
 */
@Entity
data class FavouriteCoinId(
    @PrimaryKey
    val id: String
)
