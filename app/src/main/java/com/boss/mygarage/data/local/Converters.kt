package com.boss.mygarage.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    // Setup Json-object: ignore unknown fields for stability
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromMap(value: Map<String, String>?): String {
        // If value == null, return empty json-object "{}"
        return json.encodeToString(value ?: emptyMap())
    }

    @TypeConverter
    fun toMap(value: String): Map<String, String> {
        return try {
            json.decodeFromString(value)
        } catch (e: Exception) {
            emptyMap() // return empty map if data is incorrect
        }
    }
}