package ru.kamazz.drugstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kamazz.drugstore.R
import ru.kamazz.drugstore.models.AptekaItem

class MyAdapter(private var items: List<AptekaItem>,
                private val onItemLongClick: (AptekaItem, Int, View) -> Unit
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.t1)
        val textView2: TextView = itemView.findViewById(R.id.t2)
        val textView3: TextView = itemView.findViewById(R.id.t3)
        val textView4: TextView = itemView.findViewById(R.id.t34)
        val textView5: TextView = itemView.findViewById(R.id.t5)
        val textView6: TextView = itemView.findViewById(R.id.t6)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView1.text = "Название: ${items[position].nameLekarstva}"
        holder.textView2.text = "Производитель: ${items[position].proizvoditelLekarstva}"
        holder.textView3.text = "Вид лекарства: ${items[position].vidLekarstva}"
        holder.textView4.text = "Форма лекарства: ${items[position].formaLekarstva}"
        holder.textView5.text = "Объём лекарства: ${items[position].obQemLekarstva}"
        holder.textView6.text = "Стоимость: ${items[position].stoimostLekarstva}"

        holder.itemView.setOnLongClickListener {
            onItemLongClick(item, position, it)
            true
        }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<AptekaItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}