package com.inksy.Model

import com.google.gson.annotations.SerializedName

data class DashboardDataModel(
    @SerializedName("people") var people: People? = null,
    @SerializedName("journals") var journals: ArrayList<Journals>? = null,
    @SerializedName("followed_journals") var followedJournals: ArrayList<Journals>? = null,

    @SerializedName("categories") var categories: ArrayList<Categories>? = null
)
