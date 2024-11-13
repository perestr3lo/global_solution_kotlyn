package com.example.gs_kotlyn.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_kotlyn.R
import com.example.gs_kotlyn.model.DicaModel


class DicasAdapter(private val onItemRemoved: (DicaModel) -> Unit) :
    RecyclerView.Adapter<DicasAdapter.ItemViewHolder>() {

    // Lista de itens que serão exibidos no RecyclerView.
    private var items = listOf<DicaModel>()

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Referências para as views de cada item.
        val textView = view.findViewById<TextView>(R.id.textViewItem)
        val button = view.findViewById<ImageButton>(R.id.imageButton)


        fun bind(dica: DicaModel) {
            // Define o texto do TextView para o nome do item.
            textView.text = dica.title
            textView.text = dica.description
            button.setOnClickListener {
                onItemRemoved(dica)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Infla o layout do item.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tip, parent, false)
        // Cria e retorna um novo ViewHolder.
        return ItemViewHolder(view)
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    fun updateDicas(newItems: List<DicaModel>) {
        // Atualiza a lista de itens.
        items = newItems
        // Notifica o RecyclerView que os dados mudaram.
        notifyDataSetChanged()
    }
}