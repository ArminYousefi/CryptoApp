package google.yousefi.cryptoapp.domain.market

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.coin.CoinRepository
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * یک UseCase برای دریافت لیست سکه‌ها از حافظه نهان است.
 *
 * @param coinRepository ریپازیتوری مربوط به لیست سکه‌ها.
 */
class GetCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را بدون پارامتر اجرا می‌کند.
     *
     * @return یک Flow از نوع Result<List<Coin>> که لیست سکه‌ها را به صورت پیوسته بازمی‌گرداند.
     */
    operator fun invoke(): Flow<Result<List<Coin>>> {
        return getCoins()
    }

    /**
     * یک متد خصوصی برای دریافت لیست سکه‌ها از حافظه نهان.
     *
     * @return یک Flow از نوع Result<List<Coin>> که حاوی لیست سکه‌های مورد علاقه است یا خطا در صورت وقوع خطا.
     */
    private fun getCoins(): Flow<Result<List<Coin>>> {
        return coinRepository.getCachedCoins()
    }
}

