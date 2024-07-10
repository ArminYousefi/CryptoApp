package google.yousefi.cryptoapp.ui.screen.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.domain.search.GetCoinSearchResultsUseCase
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

/**
 * ViewModel برای جستجو و نمایش نتایج جستجوی ارزهای دیجیتال با استفاده از ابزار Hilt برای اینژکت کردن وابستگی‌ها.
 *
 * @param getCoinSearchResultsUseCase UseCase برای دریافت نتایج جستجو بر اساس کوئری ورودی.
 */
@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCoinSearchResultsUseCase: GetCoinSearchResultsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    init {
        initialiseUiState()
    }

    /**
     * این متد برای اولین بارهایی که ViewModel ایجاد می‌شود، فراخوانی می‌شود تا وضعیت UI را مقداردهی اولیه کند.
     * در این متد از کوئری جستجو شده استفاده شده و با تأخیری 350 میلی‌ثانیه‌ای اطلاعات جستجو به روزرسانی می‌شود.
     */
    fun initialiseUiState() {
        snapshotFlow { searchQuery }
            .debounce(350L)
            .onEach { query ->
                if (query.isNotBlank()) {
                    _uiState.update {
                        it.copy(isSearching = true)
                    }

                    val result = getCoinSearchResultsUseCase(query)

                    when (result) {
                        is Result.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = result.message,
                                    isSearching = false
                                )
                            }
                        }

                        is Result.Success -> {
                            val searchResults = result.data.toPersistentList()

                            _uiState.update {
                                it.copy(
                                    searchResults = searchResults,
                                    queryHasNoResults = searchResults.isEmpty(),
                                    isSearching = false,
                                    errorMessage = null
                                )
                            }
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            searchResults = persistentListOf(),
                            queryHasNoResults = false,
                            isSearching = false,
                            errorMessage = null
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    /**
     * متد برای به روزرسانی کوئری جستجو فراخوانی می‌شود.
     *
     * @param newQuery کوئری جدید برای جستجو.
     */
    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }
}
