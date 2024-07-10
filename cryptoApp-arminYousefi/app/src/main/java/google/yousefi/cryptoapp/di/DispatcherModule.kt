package google.yousefi.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * ماژول Hilt برای فراهم کردن Dispatcher های مختلف Coroutine.
 */
@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    /**
     * فراهم کردن Dispatcher پیش‌فرض.
     *
     * @return CoroutineDispatcher پیش‌فرض.
     */
    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    /**
     * فراهم کردن Dispatcher ورودی/خروجی (IO).
     *
     * @return CoroutineDispatcher برای عملیات ورودی/خروجی.
     */
    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * فراهم کردن Dispatcher اصلی.
     *
     * @return CoroutineDispatcher اصلی.
     */
    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

/**
 * انوتیشن برای مشخص کردن Dispatcher پیش‌فرض.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

/**
 * انوتیشن برای مشخص کردن Dispatcher ورودی/خروجی (IO).
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

/**
 * انوتیشن برای مشخص کردن Dispatcher اصلی.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher
