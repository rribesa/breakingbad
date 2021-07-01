package com.rribesa.breakingbad.character.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rribesa.breakingbad.Constants.DataBase.DATABASE_VERSION
import com.rribesa.breakingbad.character.data.dao.CharacterDAO
import com.rribesa.breakingbad.character.data.entity.CharacterEntity

@Database(
    version = DATABASE_VERSION,
    entities = [CharacterEntity::class],
    exportSchema = false
)
abstract class CharacterDataBase : RoomDatabase() {
    abstract fun characterDAO():CharacterDAO
}

