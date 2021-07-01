package com.rribesa.breakingbad.character.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rribesa.breakingbad.character.domain.model.Character
import com.rribesa.breakingbad.character.domain.model.Occupation
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

val local = Locale("pt", "BR")

@Entity(tableName = "Character")
@Parcelize


data class CharacterEntity(
    @ColumnInfo(name = "birthday")
    val birthday: String,
    @PrimaryKey
    @ColumnInfo(name = "char_id")
    val charId: Int,
    @ColumnInfo(name = "image")
    val img: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "occupations")
    val occupation: String,
    @ColumnInfo(name = "portrayed")
    val portrayed: String,
    @ColumnInfo(name = "status")
    val status: String,
) : Parcelable

fun CharacterEntity.toCharacter(): Character =
    Character(
        name = this.name,
        nickname = this.nickname,
        birthday = this.birthday.toDate(),
        occupations = this.occupation.toList().map { Occupation(it) },
        portrayed = this.portrayed,
        status = this.status,
        image = this.img
    )

fun String.toDate(format: String = "dd-MM-yyyy") = kotlin.runCatching {
    SimpleDateFormat(format, local).parse(this)
}.getOrNull()

fun String.toList(): List<String> = this.split(",")


