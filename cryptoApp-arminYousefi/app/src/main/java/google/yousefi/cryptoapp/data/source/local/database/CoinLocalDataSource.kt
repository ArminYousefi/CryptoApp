package google.yousefi.cryptoapp.data.source.local.database

import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow

/**
 * این رابط CoinLocalDataSource وظیفه دسترسی به داده‌های محلی ارزها و موارد مرتبط با آن‌ها را داراست.
 * می‌توان از این رابط برای دریافت و به‌روزرسانی اطلاعات ارزها، اطلاعات ارزهای مورد علاقه، و شناسه‌های ارزهای مورد علاقه استفاده کرد.
 */
interface CoinLocalDataSource {
    /**
     * دریافت لیست ارزها به صورت جریانی (Flow).
     * @return جریانی از لیست ارزها
     */
    fun getCoins(): Flow<List<Coin>>

    /**
     * به‌روزرسانی اطلاعات ارزها در دیتابیس محلی.
     * @param coins لیستی از ارزها برای به‌روزرسانی
     */
    suspend fun updateCoins(coins: List<Coin>)

    /**
     * دریافت لیست ارزهای مورد علاقه به صورت جریانی (Flow).
     * @return جریانی از لیست ارزهای مورد علاقه
     */
    fun getFavouriteCoins(): Flow<List<FavouriteCoin>>

    /**
     * به‌روزرسانی اطلاعات ارزهای مورد علاقه در دیتابیس محلی.
     * @param favouriteCoins لیستی از ارزهای مورد علاقه برای به‌روزرسانی
     */
    suspend fun updateFavouriteCoins(favouriteCoins: List<FavouriteCoin>)

    /**
     * دریافت لیست شناسه‌های ارزهای مورد علاقه به صورت جریانی (Flow).
     * @return جریانی از لیست شناسه‌های ارزهای مورد علاقه
     */
    fun getFavouriteCoinIds(): Flow<List<FavouriteCoinId>>

    /**
     * بررسی اینکه آیا یک ارز مورد علاقه است یا خیر.
     * @param favouriteCoinId شناسه ارز مورد بررسی
     * @return جریانی از نتیجه بررسی (true یا false)
     */
    fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Boolean>

    /**
     * تغییر وضعیت مورد علاقه بودن یک ارز.
     * @param favouriteCoinId شناسه ارز مورد تغییر وضعیت
     */
    suspend fun toggleIsCoinFavourite(favouriteCoinId: FavouriteCoinId)
}

