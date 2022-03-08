package com.inksy.Model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("token") var token: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("phone_code") var phoneCode: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("utype") var utype: Int? = null,
    @SerializedName("points") var points: Int? = null,
    @SerializedName("follower_count") var followerCount: Int? = null,
    @SerializedName("following_count") var followingCount: Int? = null,
    @SerializedName("device_type") var deviceType: String? = null,
    @SerializedName("device_token") var deviceToken: String? = null,
    @SerializedName("is_private_profile") var isPrivateProfile: Int? = null,
    @SerializedName("is_profile_completed") var isProfileCompleted: Int? = null,
    @SerializedName("journals") var journals: ArrayList<Journals>? = null


)
