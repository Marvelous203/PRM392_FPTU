package com.example.lab1.repository

import com.example.lab1.api.APIClient
import com.example.lab1.api.TraineeService
import com.example.lab1.model.Trainee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TraineeRepository {
    private val traineeService: TraineeService = APIClient.getTraineeService()
    
    interface TraineeCallback<T> {
        fun onSuccess(data: T)
        fun onError(message: String)
    }
    
    fun getAllTrainees(callback: TraineeCallback<List<Trainee>>) {
        traineeService.getAllTrainees().enqueue(object : Callback<List<Trainee>> {
            override fun onResponse(call: Call<List<Trainee>>, response: Response<List<Trainee>>) {
                if (response.isSuccessful) {
                    response.body()?.let { trainees ->
                        callback.onSuccess(trainees)
                    } ?: callback.onError("Không có dữ liệu")
                } else {
                    callback.onError("Lỗi: ${response.code()} - ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<List<Trainee>>, t: Throwable) {
                callback.onError("Lỗi kết nối: ${t.message}")
            }
        })
    }
    
    fun getTraineeById(id: Long, callback: TraineeCallback<Trainee>) {
        traineeService.getTraineeById(id).enqueue(object : Callback<Trainee> {
            override fun onResponse(call: Call<Trainee>, response: Response<Trainee>) {
                if (response.isSuccessful) {
                    response.body()?.let { trainee ->
                        callback.onSuccess(trainee)
                    } ?: callback.onError("Không tìm thấy nhân viên")
                } else {
                    callback.onError("Lỗi: ${response.code()} - ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<Trainee>, t: Throwable) {
                callback.onError("Lỗi kết nối: ${t.message}")
            }
        })
    }
    
    fun createTrainee(trainee: Trainee, callback: TraineeCallback<Trainee>) {
        traineeService.createTrainee(trainee).enqueue(object : Callback<Trainee> {
            override fun onResponse(call: Call<Trainee>, response: Response<Trainee>) {
                if (response.isSuccessful) {
                    response.body()?.let { createdTrainee ->
                        callback.onSuccess(createdTrainee)
                    } ?: callback.onError("Không thể tạo nhân viên")
                } else {
                    callback.onError("Lỗi: ${response.code()} - ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<Trainee>, t: Throwable) {
                callback.onError("Lỗi kết nối: ${t.message}")
            }
        })
    }
    
    fun updateTrainee(id: Long, trainee: Trainee, callback: TraineeCallback<Trainee>) {
        traineeService.updateTrainee(id, trainee).enqueue(object : Callback<Trainee> {
            override fun onResponse(call: Call<Trainee>, response: Response<Trainee>) {
                if (response.isSuccessful) {
                    response.body()?.let { updatedTrainee ->
                        callback.onSuccess(updatedTrainee)
                    } ?: callback.onError("Không thể cập nhật nhân viên")
                } else {
                    callback.onError("Lỗi: ${response.code()} - ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<Trainee>, t: Throwable) {
                callback.onError("Lỗi kết nối: ${t.message}")
            }
        })
    }
    
    fun deleteTrainee(id: Long, callback: TraineeCallback<Unit>) {
        traineeService.deleteTrainee(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback.onSuccess(Unit)
                } else {
                    callback.onError("Lỗi: ${response.code()} - ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback.onError("Lỗi kết nối: ${t.message}")
            }
        })
    }
}