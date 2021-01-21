package com.example.ulessondemo.util

import com.example.ulessondemo.network.NetworkResponse
import com.example.ulessondemo.network.UService
import com.example.ulessondemo.network.model.SubjectDTO
import com.example.ulessondemo.network.model.SubjectResponse
import retrofit2.Response

class UServiceFake : UService {
    override suspend fun getSubjects(): Response<NetworkResponse<SubjectResponse, List<SubjectDTO>>> {
        return Response.success(Util.getMockNetworkResponse())
    }
}