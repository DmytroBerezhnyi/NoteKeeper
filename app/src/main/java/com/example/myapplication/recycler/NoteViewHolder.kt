package com.example.myapplication.recycler

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activity.AddNoteActivity
import com.example.myapplication.db.Note
import com.example.myapplication.fragment.NoteFragment
import kotlinx.android.synthetic.main.note_item.view.*

class NoteViewHolder(itemView: View, val noteFragment: NoteFragment) : RecyclerView.ViewHolder(itemView) {

    fun bind(note : Note) {
        itemView.color_container.background = ColorDrawable(note.color)
        itemView.text.text = note.text
        itemView.tv_title.text = note.title

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, AddNoteActivity::class.java)
            intent.putExtra("note", note)
            noteFragment.activity!!.startActivityForResult(intent, 1)
        }
    }
}