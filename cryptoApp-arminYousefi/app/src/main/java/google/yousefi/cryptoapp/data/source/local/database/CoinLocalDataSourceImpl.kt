package google.yousefi.cryptoapp.data.source.local.database

import google.yousefi.cryptoapp.data.source.local.database.dao.CoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinIdDao
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow

/**
 * این کلاس CoinLocalDataSourceImpl به عنوان یک پیاده‌سازی از CoinLocalDataSource وظیفه دسترسی به دیتابیس محلی و انجام عملیات مورد نیاز را دارد.
 * این کلاس مستقیماً با DAOهای مختلف (CoinDao، FavouriteCoinDao و FavouriteCoinIdDao) کار می‌کند.
 */
class CoinLocalDataSourceImpl(
    private val coinDao: CoinDao,
    private val favouriteCoinDao: FavouriteCoinDao,
    private val favouriteCoinIdDao: FavouriteCoinIdDao
) : CoinLocalDataSource {

    /**
     * دریافت لیست ارزها به صورت جریانی (Flow) از DAO CoinDao.
     */
    override fun getCoins(): Flow<List<Coin>> {
        return coinDao.getCoins()
    }

    /**
     * به‌روزرسانی اطلاعات ارزها در دیتابیس محلی از طریق DAO CoinDao.
     */
    override suspend fun updateCoins(coins: List<Coin>) {
        coinDao.updateCoins(coins)
    }

    /**
     * دریافت لیست ارزهای مورد علاقه به صورت جریانی (Flow) از DAO FavouriteCoinDao.
     */
    override fun getFavouriteCoins(): Flow<List<FavouriteCoin>> {
        return favouriteCoinDao.getFavouriteCoins()
    }

    /**
     * به‌روزرسانی اطلاعات ارزهای مورد علاقه در دیتابیس محلی از طریق DAO FavouriteCoinDao.
     */
    override suspend fun updateFavouriteCoins(favouriteCoins: List<FavouriteCoin>) {
        favouriteCoinDao.updateFavouriteCoins(favouriteCoins)
    }

    /**
     * دریافت لیست شناسه‌های ارزهای مورد علاقه به صورت جریانی (Flow) از DAO FavouriteCoinIdDao.
     */
    override fun getFavouriteCoinIds(): Flow<List<FavouriteCoinId>> {
        return favouriteCoinIdDao.getFavouriteCoinIds()
    }

    /**
     * بررسی اینکه آیا یک ارز با شناسه favouriteCoinId در لیست ارزهای مورد علاقه است یا خیر، از طریق DAO FavouriteCoinIdDao.
     */
    override fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Boolean> {
        return favouriteCoinIdDao.isCoinFavourite(coinId = favouriteCoinId.id)
    }

    /**
     * تغییر وضعیت مورد علاقه بودن یک ارز با شناسه favouriteCoinId در دیتابیس محلی از طریق DAO FavouriteCoinIdDao.
     */
    override suspend fun toggleIsCoinFavourite(favouriteCoinId: FavouriteCoinId) {
        val isCoinFavourite = favouriteCoinIdDao.isCoinFavouriteOneShot(favouriteCoinId.id)

        if (isCoinFavourite) {
            favouriteCoinIdDao.delete(favouriteCoinId)
        } else {
            favouriteCoinIdDao.insert(favouriteCoinId)
        }
    }
}
