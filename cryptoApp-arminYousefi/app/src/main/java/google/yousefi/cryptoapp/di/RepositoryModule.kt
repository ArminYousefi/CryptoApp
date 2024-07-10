package google.yousefi.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import google.yousefi.cryptoapp.data.mapper.CoinChartMapper
import google.yousefi.cryptoapp.data.mapper.CoinDetailsMapper
import google.yousefi.cryptoapp.data.mapper.CoinMapper
import google.yousefi.cryptoapp.data.mapper.CoinSearchResultsMapper
import google.yousefi.cryptoapp.data.mapper.FavouriteCoinMapper
import google.yousefi.cryptoapp.data.mapper.MarketStatsMapper
import google.yousefi.cryptoapp.data.repository.chart.CoinChartRepository
import google.yousefi.cryptoapp.data.repository.chart.CoinChartRepositoryImpl
import google.yousefi.cryptoapp.data.repository.coin.CoinRepository
import google.yousefi.cryptoapp.data.repository.coin.CoinRepositoryImpl
import google.yousefi.cryptoapp.data.repository.details.CoinDetailsRepository
import google.yousefi.cryptoapp.data.repository.details.CoinDetailsRepositoryImpl
import google.yousefi.cryptoapp.data.repository.favouriteCoin.FavouriteCoinRepository
import google.yousefi.cryptoapp.data.repository.favouriteCoin.FavouriteCoinRepositoryImpl
import google.yousefi.cryptoapp.data.repository.favouriteCoinId.FavouriteCoinIdRepository
import google.yousefi.cryptoapp.data.repository.favouriteCoinId.FavouriteCoinIdRepositoryImpl
import google.yousefi.cryptoapp.data.repository.marketStats.MarketStatsRepository
import google.yousefi.cryptoapp.data.repository.marketStats.MarketStatsRepositoryImpl
import google.yousefi.cryptoapp.data.repository.searchResults.CoinSearchResultsRepository
import google.yousefi.cryptoapp.data.repository.searchResults.CoinSearchResultsRepositoryImpl
import google.yousefi.cryptoapp.data.source.local.database.CoinLocalDataSource
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSource
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

/**
 * ماژول Hilt برای فراهم کردن پیاده‌سازی‌های مخازن مختلف.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * فراهم کردن مخزن سکه.
     *
     * @param coinNetworkDataSource منبع داده شبکه‌ای سکه.
     * @param coinLocalDataSource منبع داده محلی سکه.
     * @param coinMapper مبدل سکه.
     * @param ioDispatcher پخش‌کننده کوروتین برای عملیات ورودی/خروجی.
     * @return پیاده‌سازی مخزن سکه.
     */
    @Provides
    @Singleton
    fun provideCoinRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        coinLocalDataSource: CoinLocalDataSource,
        coinMapper: CoinMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoinRepository {
        return CoinRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            coinLocalDataSource = coinLocalDataSource,
            coinMapper = coinMapper,
            ioDispatcher = ioDispatcher
        )
    }

    /**
     * فراهم کردن مخزن سکه‌های مورد علاقه.
     *
     * @param coinNetworkDataSource منبع داده شبکه‌ای سکه.
     * @param coinLocalDataSource منبع داده محلی سکه.
     * @param favouriteCoinMapper مبدل سکه‌های مورد علاقه.
     * @param ioDispatcher پخش‌کننده کوروتین برای عملیات ورودی/خروجی.
     * @return پیاده‌سازی مخزن سکه‌های مورد علاقه.
     */
    @Provides
    @Singleton
    fun provideFavouriteCoinRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        coinLocalDataSource: CoinLocalDataSource,
        favouriteCoinMapper: FavouriteCoinMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FavouriteCoinRepository {
        return FavouriteCoinRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            coinLocalDataSource = coinLocalDataSource,
            favouriteCoinMapper = favouriteCoinMapper,
            ioDispatcher = ioDispatcher
        )
    }

    /**
     * فراهم کردن مخزن شناسه‌های سکه‌های مورد علاقه.
     *
     * @param coinLocalDataSource منبع داده محلی سکه.
     * @param ioDispatcher پخش‌کننده کوروتین برای عملیات ورودی/خروجی.
     * @return پیاده‌سازی مخزن شناسه‌های سکه‌های مورد علاقه.
     */
    @Provides
    @Singleton
    fun provideFavouriteCoinIdRepository(
        coinLocalDataSource: CoinLocalDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FavouriteCoinIdRepository {
        return FavouriteCoinIdRepositoryImpl(
            coinLocalDataSource = coinLocalDataSource,
            ioDispatcher = ioDispatcher
        )
    }

    /**
     * فراهم کردن مخزن جزئیات سکه.
     *
     * @param coinNetworkDataSource منبع داده شبکه‌ای سکه.
     * @param coinDetailsMapper مبدل جزئیات سکه.
     * @param ioDispatcher پخش‌کننده کوروتین برای عملیات ورودی/خروجی.
     * @return پیاده‌سازی مخزن جزئیات سکه.
     */
    @Provides
    @Singleton
    fun provideCoinDetailsRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        coinDetailsMapper: CoinDetailsMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoinDetailsRepository {
        return CoinDetailsRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            coinDetailsMapper = coinDetailsMapper,
            ioDispatcher = ioDispatcher
        )
    }

    /**
     * فراهم کردن مخزن نمودار سکه.
     *
     * @param coinNetworkDataSource منبع داده شبکه‌ای سکه.
     * @param coinChartMapper مبدل نمودار سکه.
     * @param ioDispatcher پخش‌کننده کوروتین برای عملیات ورودی/خروجی.
     * @return پیاده‌سازی مخزن نمودار سکه.
     */
    @Provides
    @Singleton
    fun provideCoinChartRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        coinChartMapper: CoinChartMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoinChartRepository {
        return CoinChartRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            coinChartMapper = coinChartMapper,
            ioDispatcher = ioDispatcher
        )
    }

    /**
     * فراهم کردن مخزن نتایج جستجوی سکه.
     *
     * @param coinNetworkDataSource منبع داده شبکه‌ای سکه.
     * @param coinSearchResultsMapper مبدل نتایج جستجوی سکه.
     * @param ioDispatcher پخش‌کننده کوروتین برای عملیات ورودی/خروجی.
     * @return پیاده‌سازی مخزن نتایج جستجوی سکه.
     */
    @Provides
    @Singleton
    fun provideCoinSearchResultsRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        coinSearchResultsMapper: CoinSearchResultsMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoinSearchResultsRepository {
        return CoinSearchResultsRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            coinSearchResultsMapper = coinSearchResultsMapper,
            ioDispatcher = ioDispatcher
        )
    }

    /**
     * فراهم کردن مخزن آمار بازار.
     *
     * @param coinNetworkDataSource منبع داده شبکه‌ای سکه.
     * @param marketStatsMapper مبدل آمار بازار.
     * @return پیاده‌سازی مخزن آمار بازار.
     */
    @Provides
    @Singleton
    fun providesMarketStatsRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        marketStatsMapper: MarketStatsMapper
    ): MarketStatsRepository {
        return MarketStatsRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            marketStatsMapper = marketStatsMapper
        )
    }
}
