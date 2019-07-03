package com.example.myapplication.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.Note
import com.example.myapplication.recycler.NoteAdapter
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {
    val list = ArrayList<Note>()
    val adapter = NoteAdapter(this, list)

    companion object {
        var db: AppDatabase? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        recycler_view.layoutManager = GridLayoutManager(context, 4)
        recycler_view.adapter = adapter

        updateRecycler()

    }

    fun updateRecycler() {
        Thread(Runnable {
            db = AppDatabase.getAppDataBase(context = activity as Context)
            list.removeAll(list)
            list.addAll(db?.noteDao()?.getAllNotes()!!)
            activity!!.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }).start()
    }
}
