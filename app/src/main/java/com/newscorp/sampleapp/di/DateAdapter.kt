package com.newscorp.sampleapp.di

import com.squareup.moshi.FromJson

import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateAdapter {

    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US)

    @ToJson
    fun dateToJson(d: Date): String {
        return dateFormat.format(d)
    }

    @FromJson
    @Throws(ParseException::class)
    fun dateToJson(s: String): Date? {
        return dateFormat.parse(s)
    }
}