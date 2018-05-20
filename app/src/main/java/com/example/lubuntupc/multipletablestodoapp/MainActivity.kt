package com.example.lubuntupc.multipletablestodoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lubuntupc.multipletablestodoapp.data.DatabaseHelper
import com.example.lubuntupc.multipletablestodoapp.model.Tag
import com.example.lubuntupc.multipletablestodoapp.model.Todo















class MainActivity : AppCompatActivity() {

    // Database Helper
    var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)

        val tag1 = Tag("Shopping")
        val tag2 = Tag("Important")
        val tag3 = Tag("Watchlist")
        val tag4 = Tag("Androidhive")

        var tag1_id = db!!.createTag(tag1)
        val tag2_id = db!!.createTag(tag2)
        val tag3_id = db!!.createTag(tag3)
        val tag4_id = db!!.createTag(tag4)

        Log.d("Tag Count", "Tag Count: " + db!!.readAllTags().size);


        // Creating ToDos
        val todo1 = Todo("iPhone 5S", 0)
        val todo2 = Todo("Galaxy Note II", 0)
        val todo3 = Todo("Whiteboard", 0)

        val todo4 = Todo("Riddick", 0)
        val todo5 = Todo("Prisoners", 0)
        val todo6 = Todo("The Croods", 0)
        val todo7 = Todo("Insidious: Chapter 2", 0)

        val todo8 = Todo("Don't forget to call MOM", 0)
        val todo9 = Todo("Collect money from John", 0)

        val todo10 = Todo("Post new Article", 0)
        val todo11 = Todo("Take database backup", 0)


        // Inserting todos in db
        // Inserting todos under "Shopping" Tag
        val todo1_id = db!!.createToDo(todo1, longArrayOf(tag1_id))
        val todo2_id = db!!.createToDo(todo2, longArrayOf(tag1_id))
        val todo3_id = db!!.createToDo(todo3, longArrayOf(tag1_id))

        // Inserting todos under "Watchlist" Tag
        val todo4_id = db!!.createToDo(todo4, longArrayOf(tag3_id))
        val todo5_id = db!!.createToDo(todo5, longArrayOf(tag3_id))
        val todo6_id = db!!.createToDo(todo6, longArrayOf(tag3_id))
        val todo7_id = db!!.createToDo(todo7, longArrayOf(tag3_id))

        // Inserting todos under "Important" Tag
        val todo8_id = db!!.createToDo(todo8, longArrayOf(tag2_id))
        val todo9_id = db!!.createToDo(todo9, longArrayOf(tag2_id))

        // Inserting todos under "Androidhive" Tag
        val todo10_id = db!!.createToDo(todo10, longArrayOf(tag4_id))
        val todo11_id = db!!.createToDo(todo11, longArrayOf(tag4_id))

        Log.e("Todo Count", "Todo count: " + db!!.getToDoCount());

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        db!!.createTodoTag(todo10_id, tag2_id);

        // Getting all tag names
        Log.d("Get Tags", "Getting All Tags");

        val allTags: ArrayList<Tag> = db!!.readAllTags()
        for (tag in allTags) {
            Log.d("Tag Name", tag.tag_name)
        }


        // Getting all Todos
        Log.d("Get Todos", "Getting All ToDos")

        val allToDos: ArrayList<Todo> = db!!.readAllToDos()
        for (todo in allToDos) {
            Log.d("ToDo", todo.note)
        }

        // Getting todos under "Watchlist" tag name
        Log.d("ToDo", "Get todos under single Tag name")

        val tagsWatchList: ArrayList<Todo> = db!!.readAllToDosByTag(tag3.tag_name!!)
        for (todo in tagsWatchList) {
            Log.d("ToDo Watchlist", todo.note)
        }
    }
}
