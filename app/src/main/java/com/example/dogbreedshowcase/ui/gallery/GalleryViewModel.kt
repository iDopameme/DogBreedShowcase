package com.example.dogbreedshowcase.ui.gallery

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedshowcase.api.RetrofitInstance
import com.example.dogbreedshowcase.model.*
import kotlinx.coroutines.launch
import java.lang.Exception

class GalleryViewModel : ViewModel() {
    private val _images: MutableLiveData<List<DogImage>> = MutableLiveData()
    val images: LiveData<List<DogImage>>
        get() = _images

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    fun getImages(breed: String) {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val fetchedImages = RetrofitInstance.api.fetchDogImages(breed)
                Log.i(TAG, "Got images: $fetchedImages")

                _images.value = fetchedImages.toDomain()

            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun DogJSONImage.toDomain(): List<DogImage> {
        return this.message.map {
            DogImage(imageUrl = it)
        }
    }

    // add favorite &
    fun displayPopupOptions(dogImage: DogImage) {
        Log.e(TAG, "TESTING IF THE BUTTON CLICK WORKS")
    }

}