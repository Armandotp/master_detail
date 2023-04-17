package com.atejeda.masterdetail.data.model

import java.io.Serializable

data class Pokemon(
    var id: Int,
    var name: String,
    var url: String? = "",
    val sprites: Sprites? = null,
    val weight: Int,
    val height: Int,
    var isFavourite: Boolean = false,
    val types: List<Type>? = null,
    var typesString: String? = "",
) : Serializable