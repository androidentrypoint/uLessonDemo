package com.example.ulessondemo.network

import com.example.ulessondemo.network.model.SubjectDTO
import com.example.ulessondemo.network.model.SubjectResponse
import retrofit2.Response
import retrofit2.http.GET

interface UService {

    @GET("3p/api/content/grade")
    suspend fun getSubjects(): Response<NetworkResponse<SubjectResponse, List<SubjectDTO>>>
}