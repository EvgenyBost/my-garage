package com.boss.mygarage.data.local

import androidx.room.TypeConverter
import com.boss.mygarage.domain.model.VehicleMetric
import com.boss.mygarage.domain.model.VehicleType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    // Setup Json-object: ignore unknown fields for stability
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

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

    @TypeConverter
    fun fromMetricsList(metrics: List<VehicleMetric>): String {
        return json.encodeToString(metrics)
    }

    @TypeConverter
    fun toMetricsList(data: String): List<VehicleMetric> {
        return try {
            json.decodeFromString(data)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // For VehicleType (Enum)
    @TypeConverter
    fun fromVehicleType(type: VehicleType): String = type.name

    @TypeConverter
    fun toVehicleType(value: String): VehicleType = VehicleType.valueOf(value)
}