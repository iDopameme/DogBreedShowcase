package com.example.dogbreedshowcase.ui.maindog

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedshowcase.api.RetrofitInstance
import com.example.dogbreedshowcase.model.*
import kotlinx.coroutines.launch

class MainDogViewModel : ViewModel() {
    private val _breeds: MutableLiveData<List<DogBreeds>> = MutableLiveData()
    val breeds: LiveData<List<DogBreeds>>
        get() = _breeds

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    fun getDogs() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val fetchedDogs = RetrofitInstance.api.fetchAllDogs()
                Log.i(TAG, "Got breeds: $fetchedDogs")

                _breeds.value = fetchedDogs.toDomain()

//                val fetchRandomImage = RetrofitInstance.api.fetchRandomImage()
//                Log.i(TAG, "Got images: $fetchRandomImage")


//                val currentDogs = _breeds.value ?: emptyList()
//                _breeds.value = currentDogs + fetchedDogs
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun DogJSON.toDomain(): List<DogBreeds> {
        return this.message.entries.map {
            val subBreed = it.value.map { sub ->
                DogSubBreed(sub)
            }
            DogBreeds(it.key, subBreed)
        }
    }
}