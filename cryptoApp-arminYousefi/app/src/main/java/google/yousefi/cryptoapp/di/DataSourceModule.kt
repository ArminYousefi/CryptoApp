package google.yousefi.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import google.yousefi.cryptoapp.data.source.local.database.CoinLocalDataSource
import google.yousefi.cryptoapp.data.source.local.database.CoinLocalDataSourceImpl
import google.yousefi.cryptoapp.data.source.local.database.dao.CoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinIdDao
import google.yousefi.cryptoapp.data.source.remote.CoinApi
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSource
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSourceImpl
import javax.inject.Singleton

/**
 * ماژول Hilt برای فراهم کردن منابع داده‌های شبکه‌ای و محلی.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /**
     * فراهم کردن نمونه‌ای از منبع داده شبکه‌ای.
     *
     * @param coinApi رابط API برای ارتباط با سرور.
     * @return نمونه‌ای از CoinNetworkDataSource.
     */
    @Provides
    @Singleton
    fun provideCoinNetworkDataSource(coinApi: CoinApi): CoinNetworkDataSource {
        return CoinNetworkDataSourceImpl(coinApi = coinApi)
    }

    /**
     * فراهم کردن نمونه‌ای از منبع داده محلی.
     *
     * @param favouriteCoinIdDao DAO برای شناسه‌های سکه‌های مورد علاقه.
     * @param coinDao DAO برای اطلاعات سکه‌ها.
     * @param favouriteCoinDao DAO برای سکه‌های مورد علاقه.
     * @return نمونه‌ای از CoinLocalDataSource.
     */
    @Provides
    @Singleton
    fun provideCoinLocalDataSource(
        favouriteCoinIdDao: FavouriteCoinIdDao,
        coinDao: CoinDao,
        favouriteCoinDao: FavouriteCoinDao
    ): CoinLocalDataSource {
        return CoinLocalDataSourceImpl(
            favouriteCoinIdDao = favouriteCoinIdDao,
            coinDao = coinDao,
            favouriteCoinDao = favouriteCoinDao
        )
    }
}
