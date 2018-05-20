package com.example.lubuntupc.multipletablestodoapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.lubuntupc.multipletablestodoapp.model.Tag
import com.example.lubuntupc.multipletablestodoapp.model.Todo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by lubuntupc on 06.12.17.
 */
class DatabaseHelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private fun getDateTime(): String {
        val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }


    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TODO + " TEXT," +
                KEY_STATUS + " INTEGER," +
                KEY_CREATED_AT + " DATETIME" + ")"

        // Tag table create statement
        var CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG +   "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TAG_NAME + " TEXT," +
                KEY_CREATED_AT + " DATETIME" + ")"

        // todo_tag table create statement
        var CREATE_TABLE_TODO_TAG = "CREATE TABLE " + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TODO_ID + " INTEGER," +
                KEY_TAG_ID + " INTEGER," +
                KEY_CREATED_AT + " DATETIME" + ")";

        db?.execSQL(CREATE_TABLE_TODO)
        db?.execSQL(CREATE_TABLE_TAG)
        db?.execSQL(CREATE_TABLE_TODO_TAG)
    }

    override fun onUpgrade(db: SQLiteDatabase?, beforeUpdate: Int, afterUpdate: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG)
    }



    //crud TODO
    fun createToDo(todo: Todo, tag_ids: LongArray) : Long{
        var db = writableDatabase
        var values = ContentValues()
        values.put(KEY_TODO, todo.note)
        values.put(KEY_STATUS, todo.status)
        values.put(KEY_CREATED_AT, getDateTime())

        //insert row
        var todo_id = db.insert(TABLE_TODO, null, values)

        //assigning tags to todo
        for (tag_id in tag_ids) {
            createTodoTag(todo_id, tag_id)
        }
        
        return todo_id
    }

    fun readTodo(todo_id: Long){
        var db = readableDatabase

        var selectQuery = ("SELECT  * FROM " + TABLE_TODO + " WHERE "
                + KEY_ID + " = " + todo_id)

        Log.e(LOG, selectQuery)

        var c: Cursor = db.rawQuery(selectQuery, null)

        if (c != null)
            c.moveToFirst()

        var todo = Todo()
        todo.id = c.getInt(c.getColumnIndex(KEY_ID))
        todo.note = c.getString(c.getColumnIndex(KEY_TODO))
        todo.created_at = c.getString(c.getColumnIndex(KEY_CREATED_AT))
    }

    fun readAllToDos(): ArrayList<Todo> {
        var db = readableDatabase

        var listTodos = ArrayList<Todo>()
        var selectQuery = "SELECT * FROM " + TABLE_TODO

        Log.e(LOG, selectQuery)

        var c: Cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                var todo = Todo()
                todo.id = (c.getInt((c.getColumnIndex(KEY_ID))))
                todo.note = ((c.getString(c.getColumnIndex(KEY_TODO))))
                todo.created_at = (c.getString(c.getColumnIndex(KEY_CREATED_AT)))

                // adding to todo list
                listTodos.add(todo);
            } while(c.moveToNext())
        }
        return listTodos
    }


    /*
     * getting all todos under single tag
    * */
    fun readAllToDosByTag(tag_name: String): ArrayList<Todo> {
        var db = readableDatabase

        var listTodos = ArrayList<Todo>()

        var selectQuery = ("SELECT  * FROM " + TABLE_TODO + " td, "
                + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt WHERE tg."
                + KEY_TAG_NAME + " = '" + tag_name + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_TAG_ID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_TODO_ID)

        Log.e(LOG, selectQuery)

        var c:Cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                var todo = Todo()
                todo.id = (c.getInt((c.getColumnIndex(KEY_ID))))
                todo.note = ((c.getString(c.getColumnIndex(KEY_TODO))))
                todo.created_at = (c.getString(c.getColumnIndex(KEY_CREATED_AT)))

                // adding to todo list
                listTodos.add(todo)
            } while (c.moveToNext())
        }

        return listTodos
    }

    fun getToDoCount(): Int {
        val db = this.readableDatabase
        val countQuery = "SELECT  * FROM " + TABLE_TODO
        val cursor = db.rawQuery(countQuery, null)

        val count = cursor.count
        cursor.close()

        // return count
        return count
    }

    fun updateToDo(todo: Todo): Int {
        var db = writableDatabase
        var values= ContentValues()

        values.put(KEY_TODO, todo.note)
        values.put(KEY_STATUS, todo.status)

        return db.update(TABLE_TODO, values, KEY_ID + "=?", arrayOf(todo.id.toString()))
    }

    fun deleteTodo(todo_id: Int) {
        var db = writableDatabase
        db.delete(TABLE_TODO, KEY_ID + "=?", arrayOf(todo_id.toString()))
    }

    //crud Tag
    fun createTag(tag: Tag): Long {
        var db = writableDatabase
        var values = ContentValues()
        values.put(KEY_TAG_NAME, tag.tag_name)
        values.put(KEY_CREATED_AT, getDateTime())

        // insert row
        var tag_id = db.insert(TABLE_TAG, null, values)
        return tag_id

    }

    fun readAllTags(): ArrayList<Tag> {
        var db = readableDatabase
        var listTags = ArrayList<Tag>()

        var selectQuery = "SELECT * FROM " + TABLE_TAG
        Log.e(LOG, selectQuery)

        var c: Cursor = db.rawQuery(selectQuery, null)

        if (c.moveToFirst()) {
            do {
                var tag = Tag()
                tag.id = c.getInt(c.getColumnIndex(KEY_ID))
                tag.tag_name = c.getString(c.getColumnIndex(KEY_TAG_NAME))

                listTags.add(tag)
            } while (c.moveToNext())
        }

        return listTags
    }

    fun updateTag(tag: Tag): Int {
        var db = writableDatabase
        var values = ContentValues()
        values.put(KEY_TAG_NAME, tag.tag_name)

        return db.update(TABLE_TAG, values, KEY_ID + "=?", arrayOf(tag.id.toString()))
    }

    fun deleteTag(tag: Tag, should_delete_all_tag_todos: Boolean) {
        var db = writableDatabase

        // before deleting tag
        // check if todos under this tag should also be deleted
        if (should_delete_all_tag_todos) {
            // get all todos under this tag
            val allTagToDos = readAllToDosByTag(tag.tag_name!!)

            // delete all todos
            for (todo in allTagToDos) {
                // delete todo
                deleteTodo(todo.id!!)
            }

        }

        db.delete(TABLE_TAG, KEY_ID + "=?", arrayOf(tag.id.toString()))
    }


    //Assigning a Tag to Todo

    fun createTodoTag(todo_id: Long, tag_id: Long): Long {
        var db = writableDatabase

        var values = ContentValues()
        values.put(KEY_TODO_ID, todo_id);
        values.put(KEY_TAG_ID, tag_id);
        values.put(KEY_CREATED_AT, getDateTime());

        var id = db.insert(TABLE_TODO_TAG, null, values)

        return id
    }

    //Removing Tag of Todo
  //  Following method will remove the tag assigned to a

    fun deleteToDoTag(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                arrayOf(id.toString()))
    }

    fun updateNoteTag(id: Long, tag_id: Long): Int {
        var db = writableDatabase

        var values = ContentValues()
        values.put(KEY_TAG_ID, tag_id)

        return db.update(TABLE_TODO, values, KEY_ID + "=?", arrayOf(id.toString()))
    }

    // closing database
    fun closeDB() {
        val db = readableDatabase
        if (db != null && db.isOpen)
            db.close()
    }
}