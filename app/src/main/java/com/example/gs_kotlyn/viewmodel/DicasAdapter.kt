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

    private var items = listOf<DicaModel>()
    private var itensFiltrados = listOf<DicaModel>() // Nova lista para itens filtrados

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.textViewItem)
        val textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
        val button = view.findViewById<ImageButton>(R.id.imageButton)

        fun bind(dica: DicaModel) {
            textView.text = dica.title
            textViewDescription.text = dica.description
            button.setOnClickListener {
                onItemRemoved(dica)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tip, parent, false)
        return ItemViewHolder(view)
    }

    // Atualizar para usar itensFiltrados
    override fun getItemCount(): Int = itensFiltrados.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itensFiltrados[position]
        holder.bind(item)
    }

    fun updateDicas(newItems: List<DicaModel>) {
        items = newItems
        itensFiltrados = newItems // Atualiza também a lista filtrada
        notifyDataSetChanged()
    }

    // Novo método para filtrar itens
    fun filter(query: String) {
        itensFiltrados = if (query.isEmpty()) {
            items // Se a busca estiver vazia, mostra todos os itens
        } else {
            items.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}