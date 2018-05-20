package com.example.lubuntupc.multipletablestodoapp.model

/**
 * Created by lubuntupc on 06.12.17.
 */
class Todo() {
    var id: Int? = null
    var note: String? = null
    var status: Int? = null
    var created_at: String? = null

    constructor(note: String, status: Int): this() {
        this.note = note
        this.status = status
    }

    constructor(id: Int, note: String, status: Int): this() {
        this.id = id
        this.note = note
        this.status = status
    }

    constructor(id: Int, note: String, status: Int, created_at: String): this() {
        this.id = id
        this.note = note
        this.status = status
        this.created_at = created_at
    }
}