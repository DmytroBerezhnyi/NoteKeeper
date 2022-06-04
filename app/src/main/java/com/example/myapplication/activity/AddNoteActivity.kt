package com.example.myapplication.activity

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.Note
import com.example.myapplication.fragment.NoteFragment
import kotlinx.android.synthetic.main.content_add_note.*
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private var isUpdate = false
    private var isNeeded = true
    lateinit var note: Note
    var color = Color.argb(255, 255, 255, 255)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_add_note)
        init()
    }

    private fun init() {
        resize()
        showNote()

        btn_return.setOnClickListener {
            closeNoteWindow()
        }

        menu.setOnClickListener {
            showMenu(it)
        }
    }

    private fun showMenu(view: View?) {
        val menu = PopupMenu(this, view)
        menu.setOnMenuItemClickListener {
            if (it.itemId == R.id.delete) {
                delete()
            } else if (it.itemId == R.id.clone) {
                clone()
            }
            false
        }
        menu.inflate(R.menu.menu_more)
        menu.show()
    }

    private fun clone() {
        Thread {
            if (isUpdate) {
                NoteFragment.db = AppDatabase.getAppDataBase(context = this)
                NoteFragment.db?.noteDao()?.insertNote(
                    Note(
                        note.title,
                        note.text,
                        note.color,
                        note.lastChanged,
                        note.created
                    )
                )
                NoteFragment.db?.noteDao()?.insertNote(note)
            }
            isNeeded = false
            closeNoteWindow()
        }.start()
    }

    private fun delete() {
        Thread(Runnable {
            if (isUpdate) {
                NoteFragment.db = AppDatabase.getAppDataBase(context = this)
                NoteFragment.db?.noteDao()?.deleteNote(note)
            }
            isNeeded = false
            closeNoteWindow()
        }).start()
    }

    private fun closeNoteWindow() {
        runOnUiThread {
            setResult(1, intent)
            finish()
        }
    }


    private fun showNote() {
        if (intent != null) {
            val note = intent.getParcelableExtra<Note>("note")
            if (note != null) {
                isUpdate = true
                AddNoteActivity@ this.note = note
                tv_title.setText(note.title)
                description.setText(note.text)
                description.setSelection(description.text.length)
                content.background = ColorDrawable(note.color)
                tv_lastChanged.text = Date(note.lastChanged).toString()
            }
        }
    }

    private fun resize() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        if (size.x > size.y) {
            window.setLayout((size.x * 0.5).toInt(), (size.y * 0.5).toInt())
        } else {
            window.setLayout(size.x, (size.y * 0.5).toInt())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isNeeded) {
            Thread(Runnable {
                NoteFragment.db = AppDatabase.getAppDataBase(context = this)
                if (isUpdate) {
                    note.text = description.text.toString()
                    note.title = tv_title.text.toString()
                    note.color = color
                    note.lastChanged = System.currentTimeMillis()
                    NoteFragment.db?.noteDao()?.updateNote(note)
                } else {
                    if (tv_title.text.toString() != "" && description.text.toString() != "") {
                        note = Note(tv_title.text.toString(), description.text.toString())
                        note.color = color
                        note.lastChanged = System.currentTimeMillis()
                        NoteFragment.db?.noteDao()?.insertNote(note)
                    } else {
                        runOnUiThread {
                            Toast.makeText(this, "Пустая заметка была удалена", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                closeNoteWindow()

            }).start()
        }
    }

}
