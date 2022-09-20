package com.example.mumble.data.mapper

interface Mapper<Input, Output> {
    fun map(input: Input): Output
    fun map(list: List<Input>): List<Output> = list.map { map(it) }
}
