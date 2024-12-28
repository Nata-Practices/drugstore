package ru.kamazz.drugstore.models

/**
 * Класс данных, представляющий элемент лекарства.
 *
 * @property nameLekarstva Название лекарства.
 * @property proizvoditelLekarstva Производитель лекарства.
 * @property vidLekarstva Тип лекарства (например, обезболивающее, антибиотик).
 * @property formaLekarstva Форма выпуска лекарства (например, таблетки, капсулы).
 * @property obQemLekarstva Объем или дозировка лекарства.
 * @property stoimostLekarstva Стоимость лекарства в виде строки (например, "200 руб.").
 */
data class AptekaItem(
    val nameLekarstva: String,
    val proizvoditelLekarstva: String,
    val vidLekarstva: String,
    val formaLekarstva: String,
    val obQemLekarstva: String,
    val stoimostLekarstva: String
)