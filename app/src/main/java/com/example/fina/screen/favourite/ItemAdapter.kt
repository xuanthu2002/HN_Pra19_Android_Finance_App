package com.example.fina.screen.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fina.R
import com.example.fina.utils.OnItemRecyclerViewClickListener
import com.example.fina.utils.base.ItemBase

class ItemAdapter(
    private val items: List<ItemBase>,
    private var selectedItem: ItemBase,
    private val onItemSelected: OnItemRecyclerViewClickListener<ItemBase>,
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_layout, parent, false)
        return ViewHolder(view, onItemSelected)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(items[position], item == selectedItem)
    }

    override fun getItemCount(): Int = items.size

    public class ViewHolder(itemView: View, listener: OnItemRecyclerViewClickListener<ItemBase>) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textViewContent)
        private lateinit var content: ItemBase

        init {
            itemView.setOnClickListener {
                listener.onItemClick(content)
            }
        }

        fun bind(
            item: ItemBase,
            isSelected: Boolean,
        ) {
            textView.text = item.getDisplayName(itemView.context)
            itemView.setBackgroundResource(
                if (isSelected) R.drawable.selected_item_background else R.drawable.default_item_background,
            )
            content = item
        }
    }
}
