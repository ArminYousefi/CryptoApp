package google.yousefi.cryptoapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import google.yousefi.cryptoapp.common.Constants
import google.yousefi.cryptoapp.data.source.local.database.CoinDatabase
import google.yousefi.cryptoapp.data.source.local.database.dao.CoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinIdDao
import javax.inject.Singleton

/**
 * ماژول Hilt برای فراهم کردن پیاده‌سازی پایگاه داده Room و DAO های مرتبط.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    /**
     * فراهم کردن نمونه‌ای از CoinDao.
     *
     * @param database نمونه‌ای از CoinDatabase.
     * @return نمونه‌ای از CoinDao.
     */
    @Provides
    @Singleton
    fun provideCoinDao(database: CoinDatabase): CoinDao {
        return database.coinDao()
    }

    /**
     * فراهم کردن نمونه‌ای از FavouriteCoinDao.
     *
     * @param database نمونه‌ای از CoinDatabase.
     * @return نمونه‌ای از FavouriteCoinDao.
     */
    @Provides
    @Singleton
    fun provideFavouriteCoinDao(database: CoinDatabase): FavouriteCoinDao {
        return database.favouriteCoinDao()
    }

    /**
     * فراهم کردن نمونه‌ای از FavouriteCoinIdDao.
     *
     * @param database نمونه‌ای از CoinDatabase.
     * @return نمونه‌ای از FavouriteCoinIdDao.
     */
    @Provides
    @Singleton
    fun provideFavouriteCoinIdDao(database: CoinDatabase): FavouriteCoinIdDao {
        return database.favouriteCoinIdDao()
    }

    /**
     * فراهم کردن نمونه‌ای از CoinDatabase.
     *
     * @param context نمونه‌ای از Context.
     * @return نمونه‌ای از CoinDatabase.
     */
    @Provides
    @Singleton
    fun provideCoinDatabase(@ApplicationContext context: Context): CoinDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CoinDatabase::class.java,
            Constants.COIN_DATABASE_NAME
        ).build()
    }
}
