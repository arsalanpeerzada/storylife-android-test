package com.inksy.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PeopleListModel(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("points") var points: Int? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_followed") var isFollowed: Int? = null,
    @SerializedName("pivot"     ) var pivot    : Pivot?  = Pivot()
) : Serializable
