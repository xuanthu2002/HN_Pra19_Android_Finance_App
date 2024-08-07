package com.example.fina.screen.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fina.R
import com.example.fina.utils.OnItemRecyclerViewClickListener
import com.example.fina.utils.base.ItemBase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialogFragment(
    private val title: String,
    private val items: List<ItemBase>,
    private val selectedItem: ItemBase,
    private val onItemSelected: OnItemRecyclerViewClickListener<ItemBase>,
) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        textViewTitle.text = title

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter =
            ItemAdapter(
                items,
                selectedItem,
                object : OnItemRecyclerViewClickListener<ItemBase> {
                    override fun onItemClick(item: ItemBase?) {
                        dismiss()
                        onItemSelected.onItemClick(item)
                    }
                },
            )
    }
}
