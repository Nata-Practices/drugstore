package ru.kamazz.drugstore.ui.dialogs

import ru.kamazz.drugstore.models.AptekaItem

interface DialogCallback {
    fun onSave(item: AptekaItem)
}