package ru.kamazz.drugstore

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kamazz.drugstore.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var searchRequest = ""
    var selectedType = ""
    var selectedSort = ""

    data class AptekaItem(
        val nameLekarstva: String,
        val proizvoditelLekarstva: String,
        val vidLekarstva: String,
        val formaLekarstva: String,
        val obQemLekarstva: String,
        val stoimostLekarstva: String
    )
    val medicine1 = listOf(
        AptekaItem(
            nameLekarstva = "Ибупрофен",
            proizvoditelLekarstva = "Фармацевтическая Компания №2",
            vidLekarstva = "Обезболивающее",
            formaLekarstva = "Таблетки",
            obQemLekarstva = "200 мг",
            stoimostLekarstva = "100 руб."
        ),
        AptekaItem(
            nameLekarstva = "Амоксициллин",
            proizvoditelLekarstva = "Фармацевтическая Компания №3",
            vidLekarstva = "Антибиотик",
            formaLekarstva = "Капсулы",
            obQemLekarstva = "500 мг",
            stoimostLekarstva = "200 руб."
        ),
        AptekaItem(
            nameLekarstva = "Диклофенак",
            proizvoditelLekarstva = "Фармацевтическая Компания №4",
            vidLekarstva = "Противовоспалительное",
            formaLekarstva = "Гель",
            obQemLekarstva = "50 г",
            stoimostLekarstva = "150 руб."
        ),
        AptekaItem(
            nameLekarstva = "Цетиризин",
            proizvoditelLekarstva = "Фармацевтическая Компания №5",
            vidLekarstva = "Антигистаминное",
            formaLekarstva = "Таблетки",
            obQemLekarstva = "10 мг",
            stoimostLekarstva = "120 руб."
        ),
        AptekaItem(
            nameLekarstva = "Аскорбиновая кислота",
            proizvoditelLekarstva = "Фармацевтическая Компания №6",
            vidLekarstva = "Витамины и минералы",
            formaLekarstva = "Таблетки",
            obQemLekarstva = "100 мг",
            stoimostLekarstva = "80 руб."
        ),
        AptekaItem(
            nameLekarstva = "Ацикловир",
            proizvoditelLekarstva = "Фармацевтическая Компания №7",
            vidLekarstva = "Антивирусное",
            formaLekarstva = "Крем",
            obQemLekarstva = "5 г",
            stoimostLekarstva = "130 руб."
        ),
        AptekaItem(
            nameLekarstva = "Флуконазол",
            proizvoditelLekarstva = "Фармацевтическая Компания №8",
            vidLekarstva = "Противогрибковое",
            formaLekarstva = "Капсулы",
            obQemLekarstva = "150 мг",
            stoimostLekarstva = "90 руб."
        ),
        AptekaItem(
            nameLekarstva = "Парацетамол",
            proizvoditelLekarstva = "Фармацевтическая Компания №1",
            vidLekarstva = "Антипиретик",
            formaLekarstva = "Таблетки",
            obQemLekarstva = "500 мг",
            stoimostLekarstva = "50 руб."
        ),
        AptekaItem(
            nameLekarstva = "Лизиноприл",
            proizvoditelLekarstva = "Фармацевтическая Компания №9",
            vidLekarstva = "Антигипертензивное",
            formaLekarstva = "Таблетки",
            obQemLekarstva = "10 мг",
            stoimostLekarstva = "170 руб."
        ),
        AptekaItem(
            nameLekarstva = "Флуоксетин",
            proizvoditelLekarstva = "Фармацевтическая Компания №10",
            vidLekarstva = "Антидепрессант",
            formaLekarstva = "Капсулы",
            obQemLekarstva = "20 мг",
            stoimostLekarstva = "300 руб."
        )
    )

    var medicine2 = medicine1.map { it.copy() }

    class MyAdapter(private val items: List<AptekaItem>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView1: TextView = itemView.findViewById(R.id.t1)
            val textView2: TextView = itemView.findViewById(R.id.t2)
            val textView3: TextView = itemView.findViewById(R.id.t3)
            val textView34: TextView = itemView.findViewById(R.id.t34)
            val textView5: TextView = itemView.findViewById(R.id.t5)
            val textView6: TextView = itemView.findViewById(R.id.t6)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView1.text = "Название: " + items[position].nameLekarstva
            holder.textView2.text = "Производитель: " + items[position].proizvoditelLekarstva
            holder.textView3.text = "Вид лекарства: " + items[position].vidLekarstva
            holder.textView34.text = "Форма лекарства: " + items[position].formaLekarstva
            holder.textView5.text = "Объём лекарства: " + items[position].obQemLekarstva
            holder.textView6.text = "Стоимость: " + items[position].stoimostLekarstva
        }

        override fun getItemCount() = items.size
    }

    private fun applyTransform() {
        medicine2 = medicine1.filter { item ->
            // Фильтрация по типу лекарства
            (selectedType == "Все" || item.vidLekarstva == selectedType) &&
                    // Поиск по названию лекарства
                    item.nameLekarstva.contains(searchRequest, ignoreCase = true)
        }.sortedWith(compareBy {
            // Сортировка по цене
            val price = it.stoimostLekarstva.replace(" руб.", "").toInt()
            if (selectedSort == "Дешевле") price else -price
        })

        val a2 = MyAdapter(medicine2)
        binding.recycle.layoutManager = LinearLayoutManager(this)
        binding.recycle.adapter = a2
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uniqueVidLekarstva = medicine1.map { it.vidLekarstva }.distinct().toMutableList()
        uniqueVidLekarstva.add(0, "Все")
        val i1 = arrayOf("Дешевле", "Дороже")
        val a = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, uniqueVidLekarstva)
        a.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        val a1 = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, i1)
        a1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.s.setAdapter(a)
        binding.s1.setAdapter(a1)
        val a2 = MyAdapter(medicine2)
        binding.recycle.layoutManager = LinearLayoutManager(this)
        binding.recycle.adapter = a2

        binding.sh.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Здесь можно выполнить действия после изменения текста
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Здесь можно выполнить действия перед изменением текста
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchRequest = s.toString()
                applyTransform()
            }
        })

        binding.s1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedSort = parent.getItemAtPosition(position).toString()
                applyTransform()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Здесь можно выполнить действия, когда ничего не выбрано
            }
        }

        binding.s.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedType = parent.getItemAtPosition(position).toString()
                applyTransform()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Здесь можно выполнить действия, когда ничего не выбрано
            }
        }
    }
}