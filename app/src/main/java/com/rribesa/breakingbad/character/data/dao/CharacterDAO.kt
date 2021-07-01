package com.rribesa.breakingbad.character.data.dao

import androidx.room.*
import com.rribesa.breakingbad.character.data.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM Character")
    fun getAll(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE name = :name")
    fun get(name: String): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: CharacterEntity)

    @Query("DELETE FROM Character")
    fun deleteAll()

    @Query("DELETE FROM Character  WHERE name = :name")
    fun delete(name: String)

    @Transaction
    fun deleteAndInsert(character: CharacterEntity) {
        delete(character.name)
        insert(character)
    }

    @Transaction
    fun deleteAndInsertAll(characters: List<CharacterEntity>) {
        deleteAll()
        insertAll(characters)
    }
}