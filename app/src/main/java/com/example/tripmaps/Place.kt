package com.example.tripmaps

data class Place(
    val id : Int,
    val title : String,
    val latitude : Double,
    val longtitude : Double,
    val uris : ArrayList<String>,
    val describe : String
   )