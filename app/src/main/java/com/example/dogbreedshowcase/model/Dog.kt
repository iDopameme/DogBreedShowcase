package com.example.dogbreedshowcase.model

import androidx.annotation.Keep

@Keep
data class DogJSON(
    val status: String,
    val message: Map<String, List<String>>
)

@Keep
data class DogJSONImage(
    val status: String,
    val message: List<String>
)

data class DogBreeds(
    val breed: String,
    val subBreed: List<DogSubBreed>
)

data class DogSubBreed(
    val name: String
)

data class DogImage(
    val imageUrl: String,
    var favoriteImage: Boolean = false
)

class ExpandableBreedsModel {
    companion object {
        const val PARENT = 1
        const val CHILD = 2
    }
    lateinit var dogParent: DogBreeds
    var type: Int = 0
    lateinit var dogChild: DogSubBreed
    var isExpanded: Boolean = false
    private var isCloseShown: Boolean = false

    constructor(type : Int, dogParent: DogBreeds, isExpanded: Boolean = false,
                isCloseShown: Boolean = false) {
        this.type = type
        this.dogParent = dogParent
        this.dogChild = dogChild
        this.isCloseShown = isCloseShown
    }

    constructor(type: Int, dogChild: DogSubBreed, isExpanded: Boolean = false,
        isCloseShown: Boolean = false) {
        this.type = type
        this.dogChild = dogChild
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown
    }
}
