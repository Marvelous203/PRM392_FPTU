package com.example.lab1.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        private const val BASE_URL = "https://67e143ba58cc6bf785250a47.mockapi.io/"
        private var retrofit: Retrofit? = null
        
        fun getClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        
        fun getTraineeService(): TraineeService {
            return getClient().create(TraineeService::class.java)
        }
    }
}