package com.example.myapplication.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.db.Note
import com.example.myapplication.fragment.NoteFragment

class NoteAdapter(val fragment: NoteFragment, val list: ArrayList<Note>) :
    RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(
            LayoutInflater.from(fragment.context).inflate(R.layout.note_item, parent, false),
            fragment
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list.get(position))
    }
}