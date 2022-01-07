package com.example.dogbreedshowcase.api

import com.example.dogbreedshowcase.model.DogJSON
import com.example.dogbreedshowcase.model.DogJSONImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogAPI {

    @GET("api/breeds/list/all")
    suspend fun fetchAllDogs(): DogJSON

    @GET("api/breeds/image/random")
    suspend fun fetchRandomImage(): DogJSON

    @GET("api/breed/{breed_name}/images/")
    suspend fun fetchDogImages(@Path("breed_name") breedName: String): DogJSONImage

    @GET("api/breed/{breed_name}/{sub_breed_name}/images/")
    suspend fun fetchSubDogImages(@Path("breed_name") breedName: String, @Path("sub_breed_name")
    subBreedName: String): DogJSON
}