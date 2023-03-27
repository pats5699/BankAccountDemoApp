package com.quickplay.bank_app.data.db

import androidx.room.TypeConverter
import com.quickplay.bank_app.data.model.AccountType
import java.util.*

class Converters {

    @TypeConverter
    fun toAccountType(value: String) = enumValueOf<AccountType>(value)

    @TypeConverter
    fun fromAccountType(value: AccountType) = value.name

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}