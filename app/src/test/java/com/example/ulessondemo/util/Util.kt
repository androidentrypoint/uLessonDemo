package com.example.ulessondemo.util

import com.example.ulessondemo.network.NetworkResponse
import com.example.ulessondemo.network.model.SubjectDTO
import com.example.ulessondemo.network.model.SubjectResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okio.Okio
import java.lang.reflect.Type

object Util {

    private fun <T> parseJsonFileToObject(fileName: String, type: Type): T? {
        val json = getJsonStringFromFile(fileName)
        val result: T = Gson().fromJson(json, type)
        return result
    }

    fun getJsonStringFromFile(fileName: String): String {
        val inputStream = this::class.java.classLoader!!.getResourceAsStream(fileName)
        val source = Okio.buffer(Okio.source(inputStream!!))
        return source!!.readString(Charsets.UTF_8)
    }

    fun getMockNetworkResponseData(): List<SubjectDTO> {
        val type = object : TypeToken<NetworkResponse<SubjectResponse, List<SubjectDTO>>>() {}.type
        val response = parseJsonFileToObject<NetworkResponse<SubjectResponse, List<SubjectDTO>>>(
            "sample-subject-response.json",
            type
        )
        return response!!.dto!!
    }

    fun getMockNetworkResponse(): NetworkResponse<SubjectResponse, List<SubjectDTO>>? {
        val type = object : TypeToken<NetworkResponse<SubjectResponse, List<SubjectDTO>>>() {}.type
        return parseJsonFileToObject<NetworkResponse<SubjectResponse, List<SubjectDTO>>>(
            "sample-subject-response.json",
            type
        )
    }
}