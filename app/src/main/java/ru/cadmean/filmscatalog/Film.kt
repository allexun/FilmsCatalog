package ru.cadmean.filmscatalog

import com.google.gson.annotations.SerializedName

class Film {
    var title: String = ""
    var overview: String = ""
    @SerializedName("poster_path")
    var posterPath: String = ""

    override fun toString(): String {
        return "Title: $title\nOverview: $overview"
    }
}