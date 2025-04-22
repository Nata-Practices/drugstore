package ru.kamazz.drugstore.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.kamazz.drugstore.models.AptekaItem
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Утилита для работы с данными.
 */
object DataUtil {

    private const val FILE_NAME = "data.json" // Название файла, содержащего данные о лекарствах

    // Бинарный оператор +=
    operator fun plusAssign(context: Context) {
        loadData(context)
    }

    /**
     * Загружает данные из JSON-файла.
     * Если файл отсутствует, копирует его из папки assets.
     *
     * @param context Контекст приложения.
     * @return Список объектов AptekaItem.
     */
    fun loadData(context: Context): List<AptekaItem> {
        val jsonFile = File(context.filesDir, FILE_NAME)
        if (!jsonFile.exists()) {
            copyFromAssets(context)
        }
        return try {
            val json = jsonFile.readText(Charsets.UTF_8)
            val type = object : TypeToken<List<AptekaItem>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Добавляет новый элемент в список и сохраняет изменения в JSON-файл.
     *
     * @param context Контекст приложения.
     * @param newItem Новый элемент для добавления.
     */
    fun addItem(context: Context, newItem: AptekaItem) {
        val items = loadData(context).toMutableList()
        items.add(newItem)
        saveData(context, items)
    }

    /**
     * Обновляет существующий элемент в списке и сохраняет изменения в JSON-файл.
     *
     * @param context Контекст приложения.
     * @param index Индекс элемента для обновления.
     * @param updatedItem Обновлённый элемент.
     * @throws IndexOutOfBoundsException Если индекс находится за пределами списка.
     */
    fun updateItem(context: Context, index: Int, updatedItem: AptekaItem) {
        val items = loadData(context).toMutableList()
        if (index in items.indices) {
            items[index] = updatedItem
            saveData(context, items)
        } else {
            throw IndexOutOfBoundsException("Индекс $index не существует.")
        }
    }

    /**
     * Удаляет элемент из списка и сохраняет изменения в JSON-файл.
     *
     * @param context Контекст приложения.
     * @param index Индекс элемента для удаления.
     * @throws IndexOutOfBoundsException Если индекс находится за пределами списка.
     */
    fun removeItem(context: Context, index: Int) {
        val items = loadData(context).toMutableList()
        if (index in items.indices) {
            items.removeAt(index)
            saveData(context, items)
        } else {
            throw IndexOutOfBoundsException("Индекс $index не существует.")
        }
    }

    /**
     * Сохраняет список данных в JSON-файл.
     *
     * @param context Контекст приложения.
     * @param items Список элементов для сохранения.
     */
    private fun saveData(context: Context, items: List<AptekaItem>) {
        val jsonFile = File(context.filesDir, FILE_NAME)
        val json = Gson().toJson(items)
        try {
            FileWriter(jsonFile).use { writer ->
                writer.write(json)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Копирует JSON-файл из папки assets во внутреннее хранилище приложения.
     *
     * @param context Контекст приложения.
     */
    private fun copyFromAssets(context: Context) {
        val assetManager = context.assets
        try {
            val inputStream = assetManager.open(FILE_NAME)
            val jsonFile = File(context.filesDir, FILE_NAME)
            jsonFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}