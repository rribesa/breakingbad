package com.rribesa.breakingbad.character.domain.model

import java.util.Date

data class Character(
    val name: String,
    val birthday: Date?,
    val occupations: List<Occupation>,
    val nickname: String,
    val portrayed: String,
    val status: String,
    val image:String,
)

data class Occupation(val name: String)