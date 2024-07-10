package google.yousefi.cryptoapp.ui.screen.search

import google.yousefi.cryptoapp.model.SearchCoin
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * وضعیت UI برای صفحه‌ی جستجو شامل نتایج جستجو، وضعیت عدم وجود نتایج، پیغام خطا و وضعیت جستجوی فعال.
 *
 * @property searchResults لیستی از نتایج جستجو
 * @property queryHasNoResults وضعیت عدم وجود نتایج برای کوئری جستجو
 * @property errorMessage پیغام خطا در صورت وجود
 * @property isSearching وضعیت جستجوی فعال بودن
 */
data class SearchUiState(
    val searchResults: ImmutableList<SearchCoin> = persistentListOf(),
    val queryHasNoResults: Boolean = false,
    val errorMessage: String? = null,
    val isSearching: Boolean = false
)
