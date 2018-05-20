package com.example.lubuntupc.multipletablestodoapp.model

/**
 * Created by lubuntupc on 06.12.17.
 */
class Tag() {
    var id: Int? = null
    var tag_name: String? = null

    constructor(tag_name: String):this() {
        this.tag_name = tag_name
    }

    constructor(id: Int, tag_name: String):this() {
        this.id = id
        this.tag_name = tag_name
    }

}