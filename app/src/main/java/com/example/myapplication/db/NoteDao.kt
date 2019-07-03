package com.example.myapplication.db

import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM Note WHERE id == :id")
    fun getNoteByColor(id: Int?): Note

    @Query("SELECT * FROM Note WHERE isDone == :isDone")
    fun getNoteByStatus(isDone: Boolean): List<Note>

    @Query("SELECT * FROM Note WHERE color == :color")
    fun getNoteByColor(color: Int): List<Note>

    @Query("SELECT * FROM Note")
    fun getAllNotes(): List<Note>
}