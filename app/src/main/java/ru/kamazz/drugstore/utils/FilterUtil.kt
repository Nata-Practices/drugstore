package ru.kamazz.drugstore.utils

import ru.kamazz.drugstore.models.AptekaItem

/**
 * Утилита для фильтрации и сортировки списка лекарств.
 */
object FilterUtil {

    // Унарный оператор для инвертирования списка
    operator fun List<AptekaItem>.unaryMinus() = this.reversed()

    // Расширяющий метод для сортировки по цене
    fun List<AptekaItem>.sortByPrice() = this.sortedBy {
        it.stoimostLekarstva.replace(" руб.", "").toIntOrNull() ?: 0
    }

    /**
     * Фильтрует и сортирует список лекарств в соответствии с указанными параметрами.
     *
     * @param items Список объектов AptekaItem для обработки.
     * @param searchRequest Строка для поиска по названию лекарства.
     * @param selectedType Выбранный тип лекарства (например, "Все", "Антибиотик").
     * @param selectedSort Критерий сортировки ("Дешевле", "Дороже", "Производитель").
     * @return Отфильтрованный и отсортированный список объектов AptekaItem.
     */
    fun filterAndSort(
        items: List<AptekaItem>,
        searchRequest: String,
        selectedType: String,
        selectedSort: String
    ): List<AptekaItem> {
        val filteredList = items.filter { item ->
            (selectedType == "Все" || item.vidLekarstva == selectedType) &&
                    item.nameLekarstva.contains(searchRequest, ignoreCase = true)
        }

        return when (selectedSort) {
            "Дешевле" -> filteredList.sortedBy {
                it.stoimostLekarstva.replace(" руб.", "").toIntOrNull() ?: Int.MAX_VALUE
            }
            "Дороже" -> filteredList.sortedByDescending {
                it.stoimostLekarstva.replace(" руб.", "").toIntOrNull() ?: Int.MIN_VALUE
            }
            "Производитель" -> filteredList.sortedBy {
                it.proizvoditelLekarstva.replace("Фармацевтическая Компания №", "").toIntOrNull()
            }
            "Обратный порядок" -> -filteredList
            else -> filteredList
        }
    }

    // Инфиксный метод для объединения списков
    infix fun List<AptekaItem>.mergeWith(other: List<AptekaItem>) = this + other

    // Инфиксный метод для проверки типа
    infix fun List<AptekaItem>.containsType(type: String) = any { it.vidLekarstva == type }
}
