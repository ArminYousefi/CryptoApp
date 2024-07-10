package google.yousefi.cryptoapp.domain.details

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.details.CoinDetailsRepository
import google.yousefi.cryptoapp.data.source.local.preferences.global.UserPreferencesRepository
import google.yousefi.cryptoapp.model.CoinDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 * UseCase برای دریافت جزئیات یک سکه.
 *
 * @param coinDetailsRepository ریپازیتوری برای دسترسی به داده‌های جزئیات سکه.
 * @param userPreferencesRepository ریپازیتوری برای دسترسی به ترجیحات کاربر.
 */
class GetCoinDetailsUseCase @Inject constructor(
    private val coinDetailsRepository: CoinDetailsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را اجرا می‌کند.
     *
     * @param coinId شناسه سکه.
     * @return جریان از نوع Result که جزئیات سکه را در بر دارد.
     */
    operator fun invoke(coinId: String): Flow<Result<CoinDetails>> {
        return getCoinDetails(coinId = coinId)
    }

    /**
     * دریافت جزئیات سکه با توجه به ترجیحات کاربر.
     *
     * @param coinId شناسه سکه.
     * @return جریان از نوع Result که جزئیات سکه را در بر دارد.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCoinDetails(coinId: String): Flow<Result<CoinDetails>> {
        return userPreferencesRepository.userPreferencesFlow.flatMapLatest { userPreferences ->
            coinDetailsRepository.getCoinDetails(
                coinId = coinId,
                currency = userPreferences.currency
            )
        }
    }
}
