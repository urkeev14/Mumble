package com.example.mumble.data.mapper.impl

import com.example.mumble.data.mapper.Mapper
import java.text.SimpleDateFormat
import java.util.Date

class DateMapper : Mapper<Long, String> {
    override fun map(input: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        return dateFormat.format(Date(input))
    }
}
