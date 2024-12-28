package ru.kamazz.drugstore.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import ru.kamazz.drugstore.R
import ru.kamazz.drugstore.models.AptekaItem

class EditDialog(
    private val context: Context,
    private val initialItem: AptekaItem?,
    private val callback: DialogCallback
) {
    fun show() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_item, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.nameInput)
        val producerInput = dialogView.findViewById<EditText>(R.id.producerInput)
        val typeInput = dialogView.findViewById<EditText>(R.id.typeInput)
        val formInput = dialogView.findViewById<EditText>(R.id.formInput)
        val volumeInput = dialogView.findViewById<EditText>(R.id.volumeInput)
        val priceInput = dialogView.findViewById<EditText>(R.id.priceInput)

        initialItem?.let {
            nameInput.setText(it.nameLekarstva)
            producerInput.setText(it.proizvoditelLekarstva)
            typeInput.setText(it.vidLekarstva)
            formInput.setText(it.formaLekarstva)
            volumeInput.setText(it.obQemLekarstva)
            priceInput.setText(it.stoimostLekarstva.replace(" руб.", ""))
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle(if (initialItem == null) "Добавить лекарство" else "Редактировать лекарство")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val name = nameInput.text.toString()
                val producer = producerInput.text.toString()
                val type = typeInput.text.toString()
                val form = formInput.text.toString()
                val volume = volumeInput.text.toString()
                val price = priceInput.text.toString()

                if (name.isNotEmpty() && producer.isNotEmpty() && type.isNotEmpty() &&
                    form.isNotEmpty() && volume.isNotEmpty() && price.isNotEmpty()
                ) {
                    val newItem = AptekaItem(
                        nameLekarstva = name,
                        proizvoditelLekarstva = producer,
                        vidLekarstva = type,
                        formaLekarstva = form,
                        obQemLekarstva = volume,
                        stoimostLekarstva = "$price руб."
                    )
                    callback.onSave(newItem)
                } else {
                    Toast.makeText(context, "Не все поля заполнены!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .create()

        dialog.show()
    }
}