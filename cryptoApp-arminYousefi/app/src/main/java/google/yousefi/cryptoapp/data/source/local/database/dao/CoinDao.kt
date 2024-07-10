package google.yousefi.cryptoapp.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import kotlinx.coroutines.flow.Flow

/**
 * این رابط CoinDao برای دسترسی به داده‌های ارزها در پایگاه داده Room استفاده می‌شود.
 *
 * @Dao این برچسب برای تعریف اینترفیس به عنوان یک Data Access Object در Room استفاده می‌شود.
 */
@Dao
interface CoinDao {
    /**
     * دریافت همه‌ی ارزها از جدول Coin به صورت یک جریان داده.
     *
     * @return جریانی از لیستی از اشیاء Coin که از پایگاه داده دریافت می‌شود.
     */
    @Query("SELECT * FROM Coin")
    fun getCoins(): Flow<List<Coin>>

    /**
     * درج یک لیست از ارزها به جدول Coin.
     *
     * @param coins لیستی از اشیاء Coin برای درج به جدول Coin.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoins(coins: List<Coin>)

    /**
     * حذف همه‌ی ارزها از جدول Coin.
     */
    @Query("DELETE FROM Coin")
    fun deleteAllCoins()

    /**
     * به روزرسانی ارزها در جدول Coin به صورت یک تراکنش.
     * ابتدا تمام ارزهای موجود حذف می‌شوند و سپس ارزهای جدید وارد می‌شوند.
     *
     * @param coins لیستی از اشیاء Coin برای به روزرسانی در جدول Coin.
     */
    @Transaction
    fun updateCoins(coins: List<Coin>) {
        deleteAllCoins()
        insertCoins(coins)
    }
}

