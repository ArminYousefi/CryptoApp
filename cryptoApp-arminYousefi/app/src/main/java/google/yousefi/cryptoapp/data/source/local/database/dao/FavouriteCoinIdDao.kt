package google.yousefi.cryptoapp.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow

/**
 * این رابط FavouriteCoinIdDao برای دسترسی به داده‌های شناسه‌های ارزهای مورد علاقه در پایگاه داده Room استفاده می‌شود.
 *
 * @Dao برچسب برای تعریف اینترفیس به عنوان یک Data Access Object در Room استفاده می‌شود.
 */
@Dao
interface FavouriteCoinIdDao {
    /**
     * دریافت همه‌ی شناسه‌های ارزهای مورد علاقه از جدول FavouriteCoinId به صورت یک جریان داده.
     *
     * @return جریانی از لیستی از اشیاء FavouriteCoinId که از پایگاه داده دریافت می‌شود.
     */
    @Query("SELECT * FROM FavouriteCoinId")
    fun getFavouriteCoinIds(): Flow<List<FavouriteCoinId>>

    /**
     * بررسی اینکه آیا یک ارز با شناسه خاص مورد علاقه است یا خیر.
     *
     * @param coinId شناسه‌ی ارز برای بررسی وضعیت مورد علاقه بودن.
     * @return جریانی از نوع Boolean که نشان می‌دهد آیا ارز با این شناسه مورد علاقه است یا خیر.
     */
    @Query("SELECT COUNT(1) FROM FavouriteCoinId WHERE id = :coinId")
    fun isCoinFavourite(coinId: String): Flow<Boolean>

    /**
     * بررسی یک‌باره (One-shot) اینکه آیا یک ارز با شناسه خاص مورد علاقه است یا خیر.
     *
     * @param coinId شناسه‌ی ارز برای بررسی وضعیت مورد علاقه بودن.
     * @return مقدار Boolean که نشان می‌دهد آیا ارز با این شناسه مورد علاقه است یا خیر.
     */
    @Query("SELECT COUNT(1) FROM FavouriteCoinId WHERE id = :coinId")
    suspend fun isCoinFavouriteOneShot(coinId: String): Boolean

    /**
     * درج یک شناسه‌ی ارز مورد علاقه به جدول FavouriteCoinId.
     *
     * @param favouriteCoinId شناسه‌ی ارز مورد علاقه برای درج به جدول FavouriteCoinId.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favouriteCoinId: FavouriteCoinId)

    /**
     * حذف یک شناسه‌ی ارز مورد علاقه از جدول FavouriteCoinId.
     *
     * @param favouriteCoinId شناسه‌ی ارز مورد علاقه برای حذف از جدول FavouriteCoinId.
     */
    @Delete
    suspend fun delete(favouriteCoinId: FavouriteCoinId)
}
