package ua.kpi.industrial_mvvm.data

/**
 * Sealed interface для реактивного представлення загального стану користувацького інтерфейсу (UI State).
 * Використовується в архітектурному патерні MVVM для передачі стану з ViewModel до Composable UI.
 */
sealed interface IndustrialState {

    /**
     * Стан завантаження, вимірювання або перерахунку параметрів установки.
     */
    data object Loading : IndustrialState

    /**
     * Стан успішної норми роботи установки.
     *
     * @property params Поточні розраховані електричні параметри.
     * @property statusMessage Текстове повідомлення про стан обладнання.
     */
    data class Success(
        val params: ElectricalParams,
        val statusMessage: String
    ) : IndustrialState

    /**
     * Стан аварії або перевищення критичних параметрів установки.
     *
     * @property errorMessage Опис виявленої несправності або аварійного режиму.
     */
    data class Error(
        val errorMessage: String
    ) : IndustrialState
}
