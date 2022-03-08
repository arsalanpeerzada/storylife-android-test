package com.inksy.Remote

import com.example.example.DoodleData
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.inksy.Model.DashboardDataModel
import com.inksy.Model.OthersModel
import com.inksy.Model.PeopleListModel
import com.inksy.Model.UserModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


public interface APIInterface {



    @FormUrlEncoded
    @POST("login")
    @Headers("Accept: application/json")
    fun login(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("phone") mobile: String?,
        @Field("phone_code") code: String?
    ): Call<ApiResponse<UserModel>>

    @Multipart
    @POST("user/profile")
    @Headers("Accept: application/json")
    fun profile(
        @Part("avatar\"; filename=\"myfile.jpg") filePart: RequestBody,
        @Part("full_name") full_name: RequestBody?,
        @Part("bio") bio: RequestBody?,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<UserModel>>


    @POST("user/profile/privacy/change")
    @Headers("Accept: application/json")
    fun privacyChange(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<UserModel>>


    @GET("user/blocked/list")
    @Headers("Accept: application/json")
    fun blockList(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<List<PeopleListModel>>>


    @GET("user/profile/{id}")
    @Headers("Accept: application/json")
    fun userDetail(
        @Path("id") id: Int,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<UserModel>>

    @GET("doodle/shop")
    @Headers("Accept: application/json")
    fun doodleShop(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<DoodleData>>


    @POST("user/block/{id}")
    @Headers("Accept: application/json")
    fun userBlock(
        @Path("id") id: Int,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>

    @POST("user/unblock/{id}")
    @Headers("Accept: application/json")
    fun userUnblock(
        @Path("id") id: Int,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>


    @FormUrlEncoded
    @POST("journal/report")
    @Headers("Accept: application/json")
    fun journalReport(
        @Field("journal_id") journal_id: Int,
        @Field("title") title: String,
        @Field("description") description: String,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>

    @POST("user/follow/{id}")
    @Headers("Accept: application/json")
    fun userFollow(
        @Path("id") id: Int,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>

    @POST("user/unfollow/{id}")
    @Headers("Accept: application/json")
    fun userUnfollow(
        @Path("id") id: Int,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>

    @FormUrlEncoded
    @POST("user/signup")
    @Headers("Accept: application/json")
    fun signup(
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): Call<ApiResponse<UserModel>>

    @Headers("Accept: application/json")
    @GET("user/search")
    fun searchUser(
        @Query("title") s: String?,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<List<PeopleListModel>>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("journal/like")
    fun journalLike(
        @Field("journal_id") journal_Id: String?,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>

    @Headers("Accept: application/json")
    @POST("logout")
    fun logout(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>

    @GET("page/about")
    fun about(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<OthersModel>>

    @GET("page/terms-and-condition")
    fun terms(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<OthersModel>>

    @GET("page/privacy-policy")
    fun privacy_policy(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<OthersModel>>



    @GET("dashboard")
    @Headers("Accept: application/json")
    fun dashboardData(
        @Header("Authorization") token: String?
    ): Call<ApiResponse<DashboardDataModel>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("user/password/change")
    fun changePassword(
        @Field("old_password") old_password: String?,
        @Field("password") password: String?,
        @Field("password_confirmation") password_confirmation: String?,
        @Field("email") email: String?,
        @Header("Authorization") token: String?
    ): Call<ApiResponse<JsonElement>>

    class ApiResponse<T> {
        @SerializedName("success")
        @Expose
        var status: Int? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("data")
        @Expose
        var data: T? = null
            private set

        fun setData(data: T) {
            this.data = data
        }
    }
}