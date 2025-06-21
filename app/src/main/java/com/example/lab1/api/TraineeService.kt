package com.example.lab1.api

import com.example.lab1.model.Trainee
import retrofit2.Call
import retrofit2.http.*

interface TraineeService {
    companion object {
        const val TRAINEES = "NhanVien" // Tên của table trong API
    }
    
    // GET - Lấy tất cả trainee
    @GET(TRAINEES)
    fun getAllTrainees(): Call<List<Trainee>>
    
    // GET - Lấy trainee theo ID
    @GET("$TRAINEES/{id}")
    fun getTraineeById(@Path("id") id: Long): Call<Trainee>
    
    // POST - Tạo trainee mới
    @POST(TRAINEES)
    fun createTrainee(@Body trainee: Trainee): Call<Trainee>
    
    // PUT - Cập nhật trainee
    @PUT("$TRAINEES/{id}")
    fun updateTrainee(@Path("id") id: Long, @Body trainee: Trainee): Call<Trainee>
    
    // DELETE - Xóa trainee
    @DELETE("$TRAINEES/{id}")
    fun deleteTrainee(@Path("id") id: Long): Call<Void>
}