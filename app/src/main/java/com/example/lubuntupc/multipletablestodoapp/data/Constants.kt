package com.example.lubuntupc.multipletablestodoapp.data

/**
 * Created by lubuntupc on 06.12.17.
 */



    // Logcat tag
    val LOG = "DatabaseHelper"


    val DATABASE_VERSION = 1
    val DATABASE_NAME = "contactsManager"

    // Table Names
    val TABLE_TODO = "todos"
    val TABLE_TAG = "tags"
    val TABLE_TODO_TAG = "todo_tags"

    // Common column names
    val KEY_ID = "id"
    val KEY_CREATED_AT = "created_at"

    // NOTES Table - column nmaes
    val KEY_TODO = "todo"
    val KEY_STATUS = "status"

    // TAGS Table - column names
    val KEY_TAG_NAME = "tag_name"

    // NOTE_TAGS Table - column names
    val KEY_TODO_ID = "todo_id"
    val KEY_TAG_ID = "tag_id"

