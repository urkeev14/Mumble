package com.example.mumble.utils

interface IJsonHandler {
    fun toJson(value: Any): String
    fun fromJson()
}
