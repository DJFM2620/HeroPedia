package com.example.heropedia.network

import com.squareup.moshi.Json

//Para obtenemos los datos que vamos a necesitar
data class Superhero (

    val id: Int,
    @Json(name = "url") val imgSrcUrl: String,
    @Json(name = "name") val name: String
    )