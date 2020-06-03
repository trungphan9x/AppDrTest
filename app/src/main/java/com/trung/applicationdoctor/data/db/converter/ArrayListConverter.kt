package com.trung.applicationdoctor.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ArrayListConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToListBoolean(value: String): MutableList<Boolean>
    {
        val list = mutableListOf<Boolean>()
        value.forEach { c ->
            if(c == 'F') list.add(false)
            else list.add(true)
        }
        return list
    }
    @TypeConverter
    fun listBooleanToString(list: MutableList<Boolean>): String
    {
        var value = ""
        for(i in list)
        {
            if(i) value += "T"
            else value += "F"
        }
        return value
    }
}