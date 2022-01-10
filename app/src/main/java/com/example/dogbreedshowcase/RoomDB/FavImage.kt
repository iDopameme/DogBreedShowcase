package com.example.dogbreedshowcase.RoomDB

class FavImage {

    var image_url: String? = null
    var key_id: String? = null
    var favStatus: Boolean? = null

    constructor() {}
    constructor(image_url: String?, key_id: String?, favStatus: Boolean?) {
        this.image_url = image_url
        this.key_id = key_id
        this.favStatus = favStatus
    }

}