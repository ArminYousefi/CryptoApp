package google.yousefi.cryptoapp.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import kotlinx.coroutines.flow.Flow

/**
 * این رابط FavouriteCoinDao برای دسترسی به داده‌های ارزهای مورد علاقه در پایگاه داده Room استفاده می‌شود.
 *
 * @Dao این برچسب برای تعریف اینترفیس به عنوان یک Data Access Object در Room استفاده می‌شود.
 */
@Dao
interface FavouriteCoinDao {
    /**
     * دریافت همه‌ی ارزهای مورد علاقه از جدول FavouriteCoin به صورت یک جریان داده.
     *
     * @return جریانی از لیستی از اشیاء FavouriteCoin که از پایگاه داده دریافت می‌شود.
     */
    @Query("SELECT * FROM FavouriteCoin")
    fun getFavouriteCoins(): Flow<List<FavouriteCoin>>

    /**
     * درج یک لیست از ارزهای مورد علاقه به جدول FavouriteCoin.
     *
     * @param favouriteCoins لیستی از اشیاء FavouriteCoin برای درج به جدول FavouriteCoin.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteCoins(favouriteCoins: List<FavouriteCoin>)

    /**
     * حذف همه‌ی ارزهای مورد علاقه از جدول FavouriteCoin.
     */
    @Query("DELETE FROM FavouriteCoin")
    fun deleteAllFavouriteCoins()

    /**
     * به روزرسانی ارزهای مورد علاقه در جدول FavouriteCoin به صورت یک تراکنش.
     * ابتدا تمام ارزهای موجود حذف می‌شوند و سپس ارزهای جدید وارد می‌شوند.
     *
     * @param favouriteCoins لیستی از اشیاء FavouriteCoin برای به روزرسانی در جدول FavouriteCoin.
     */
    @Transaction
    fun updateFavouriteCoins(favouriteCoins: List<FavouriteCoin>) {
        deleteAllFavouriteCoins()
        insertFavouriteCoins(favouriteCoins)
    }
}

