package ru.kamazz.drugstore.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kamazz.drugstore.R
import ru.kamazz.drugstore.databinding.ActivityMainBinding
import ru.kamazz.drugstore.models.AptekaItem
import ru.kamazz.drugstore.ui.adapter.MyAdapter
import ru.kamazz.drugstore.ui.dialogs.DialogCallback
import ru.kamazz.drugstore.ui.dialogs.EditDialog
import ru.kamazz.drugstore.utils.DataUtil
import ru.kamazz.drugstore.utils.FilterUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var searchRequest = ""
    private var selectedType = ""
    private var selectedSort = ""
    private lateinit var medicine1: List<AptekaItem>
    private lateinit var medicine2: List<AptekaItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicine1 = DataUtil.loadData(this)
        medicine2 = medicine1.toList()

        setupUI()
    }

    private fun setupUI() {
        val uniqueVidLekarstva = medicine1.map { it.vidLekarstva }.distinct().toMutableList()
        uniqueVidLekarstva.add(0, "Все")

        val sortOptions = arrayOf("Дешевле", "Дороже", "Производитель", "Обратный порядок")

        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, uniqueVidLekarstva)
        binding.s.adapter = typeAdapter
        val sortAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortOptions)
        binding.s1.adapter = sortAdapter

        val adapter = MyAdapter(medicine2) { item, position, view ->
            showPopupMenu(item, position, view)
        }
        binding.recycle.layoutManager = LinearLayoutManager(this)
        binding.recycle.adapter = adapter

        setupListeners(adapter)
    }

    private fun setupListeners(adapter: MyAdapter) {
        binding.sh.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRequest = s.toString()
                applyTransform(adapter)
            }
        })

        binding.s1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSort = parent?.getItemAtPosition(position).toString()
                applyTransform(adapter)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.s.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedType = parent?.getItemAtPosition(position).toString()
                applyTransform(adapter)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.addButton.setOnClickListener {
            EditDialog(this, null, object : DialogCallback {
                override fun onSave(item: AptekaItem) {
                    DataUtil.addItem(this@MainActivity, item)
                    medicine1 = DataUtil.loadData(this@MainActivity)
                    applyTransform(adapter)
                }
            }).show()
        }
        // Пример использования
//        infinitely {
//            adapter.notifyDataSetChanged()
//            Thread.sleep(1000)
//        }
    }

    // Функция высшего порядка
    private fun infinitely(loopBody: () -> Unit) {
        while (true) {
            loopBody()
        }
    }

    // Лямбда-расширение для преобразования стоимости
    private fun applyTransformation(adapter: MyAdapter) {
        val transform: String.(Int) -> String = { multiplier ->
            "${this.replace(" руб.", "").toInt() * multiplier} руб."
        }

        // Ниже пример использования
        // medicine1.firstOrNull()?.stoimostLekarstva?.transform(2)
    }

    private fun applyTransform(adapter: MyAdapter) {
        val sortedList = FilterUtil.filterAndSort(medicine1, searchRequest, selectedType, selectedSort)
        adapter.updateData(sortedList)
    }

    private fun showPopupMenu(item: AptekaItem, position: Int, view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.item_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_edit -> {
                    EditDialog(this, item, object : DialogCallback {
                        override fun onSave(updatedItem: AptekaItem) {
                            val originalPosition = medicine1.indexOfFirst { it == item }
                            if (originalPosition != -1) {
                                DataUtil.updateItem(this@MainActivity, originalPosition, updatedItem)
                                medicine1 = DataUtil.loadData(this@MainActivity)
                                applyTransform(binding.recycle.adapter as MyAdapter)
                            }
                        }
                    }).show()
                    true
                }
                R.id.menu_delete -> {
                    val originalPosition = medicine1.indexOfFirst { it == item }
                    if (originalPosition != -1) {
                        DataUtil.removeItem(this, originalPosition)
                        medicine1 = DataUtil.loadData(this)
                        applyTransform(binding.recycle.adapter as MyAdapter)
                    }
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}