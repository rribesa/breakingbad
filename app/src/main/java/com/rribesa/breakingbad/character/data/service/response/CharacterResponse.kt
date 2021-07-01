package com.rribesa.breakingbad.character.data.service.response


import com.google.gson.annotations.SerializedName
import com.rribesa.breakingbad.character.data.entity.CharacterEntity
import com.rribesa.breakingbad.character.data.entity.toDate
import com.rribesa.breakingbad.character.domain.model.Character
import com.rribesa.breakingbad.character.domain.model.Occupation

data class CharacterResponse(
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("char_id")
    val charId: Int,
    @SerializedName("img")
    val img: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("occupation")
    val occupation: List<String>,
    @SerializedName("portrayed")
    val portrayed: String,
    @SerializedName("status")
    val status: String
)

fun CharacterResponse.toCharacter() = Character(
    name = this.name,
    birthday = this.birthday.toDate(),
    occupations = this.occupation.map { Occupation(it) },
    nickname = this.nickname,
    portrayed = this.portrayed,
    status = this.status,
    image = this.img
)

fun CharacterResponse.toEntity() = CharacterEntity(
    birthday = this.birthday,
    charId = this.charId,
    img = this.img,
    name = this.name,
    nickname = this.nickname,
    occupation = this.occupation.toString(),
    portrayed = this.portrayed,
    status = this.status,
)
