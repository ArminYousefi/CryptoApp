package google.yousefi.cryptoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import google.yousefi.cryptoapp.data.source.local.preferences.favourites.FavouritesPreferences
import google.yousefi.cryptoapp.data.source.local.preferences.favourites.FavouritesPreferencesSerializer
import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferences
import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferencesSerializer
import google.yousefi.cryptoapp.data.source.local.preferences.market.MarketPreferences
import google.yousefi.cryptoapp.data.source.local.preferences.market.MarketPreferencesSerializer
import javax.inject.Singleton

/**
 * ماژول Hilt برای فراهم کردن DataStore های مربوط به ترجیحات کاربر، سکه‌های مورد علاقه، و بازار.
 */
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    /**
     * فراهم کردن DataStore برای ترجیحات کاربر.
     *
     * @param appContext زمینه برنامه.
     * @return DataStore از نوع UserPreferences.
     */
    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { appContext.dataStoreFile("user_preferences.pb") },
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { UserPreferences() }
            )
        )
    }

    /**
     * فراهم کردن DataStore برای ترجیحات سکه‌های مورد علاقه.
     *
     * @param appContext زمینه برنامه.
     * @return DataStore از نوع FavouritesPreferences.
     */
    @Provides
    @Singleton
    fun provideFavouritesPreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<FavouritesPreferences> {
        return DataStoreFactory.create(
            serializer = FavouritesPreferencesSerializer,
            produceFile = { appContext.dataStoreFile("favourites_preferences.pb") },
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { FavouritesPreferences() }
            )
        )
    }

    /**
     * فراهم کردن DataStore برای ترجیحات بازار.
     *
     * @param appContext زمینه برنامه.
     * @return DataStore از نوع MarketPreferences.
     */
    @Provides
    @Singleton
    fun provideMarketPreferences(
        @ApplicationContext appContext: Context
    ): DataStore<MarketPreferences> {
        return DataStoreFactory.create(
            serializer = MarketPreferencesSerializer,
            produceFile = { appContext.dataStoreFile("market_preferences.pb") },
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { MarketPreferences() }
            )
        )
    }
}
