package com.example.rickandmortyapp.data

data class CharacterListResponse(
    val info: Info? = null,
    val results: List<CharacterList>? = null
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)

data class CharacterList(
    val id:Int,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: NameAndUrl?,
    val location: NameAndUrl,
    val image: String,
    val url: String,
    val episode: List<String>,
    val created: String
)
data class NameAndUrl(val name: String,val url: String)