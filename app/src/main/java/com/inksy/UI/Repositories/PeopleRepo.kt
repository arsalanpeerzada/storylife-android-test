package com.inksy.UI.Repositories

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.inksy.Model.PeopleListModel
import com.inksy.Model.UserModel
import com.inksy.Remote.APIClient
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PeopleRepo {
    var apiInterface: APIInterface = APIClient.createService(APIInterface::class.java)

    companion object {
        lateinit var peopleRepo: PeopleRepo

        fun getInstance(): PeopleRepo {

            peopleRepo = PeopleRepo()

            return peopleRepo
        }
    }

    fun followUser(
        id: Int?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        var mytoken = "Bearer $token"

        apiInterface.userFollow(id!!, mytoken)
            ?.enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    response: Response<APIInterface.ApiResponse<JsonElement>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<JsonElement> = response.body()!!

                        if (body.status == 1) {
                            data.value = Resource.success(body)
                        } else {
                            data.value = Resource.error(body.message.toString(), null)
                        }


                    } else {
                        val body: ResponseBody? = response.errorBody()
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var string = jObjError.getString("message")

                            data.value = Resource.error(string, null)
                        } catch (e: Exception) {
                            // Toast.makeText(getContext(), e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }

    fun blockUser(
        id: Int?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        var mytoken = "Bearer $token"

        apiInterface.userBlock(id!!, mytoken)
            ?.enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    response: Response<APIInterface.ApiResponse<JsonElement>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<JsonElement> = response.body()!!

                        if (body.status == 1) {
                            data.value = Resource.success(body)
                        } else {
                            data.value = Resource.error(body.message.toString(), null)
                        }


                    } else {
                        val body: ResponseBody? = response.errorBody()
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var string = jObjError.getString("message")

                            data.value = Resource.error(string, null)
                        } catch (e: Exception) {
                            // Toast.makeText(getContext(), e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }

    fun unblockUser(
        id: Int?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        var mytoken = "Bearer $token"

        apiInterface.userUnblock(id!!, mytoken)
            ?.enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    response: Response<APIInterface.ApiResponse<JsonElement>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<JsonElement> = response.body()!!

                        if (body.status == 1) {
                            data.value = Resource.success(body)
                        } else {
                            data.value = Resource.error(body.message.toString(), null)
                        }


                    } else {
                        val body: ResponseBody? = response.errorBody()
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var string = jObjError.getString("message")

                            data.value = Resource.error(string, null)
                        } catch (e: Exception) {
                            // Toast.makeText(getContext(), e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }

    fun unfollowUser(
        id: Int?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        var mytoken = "Bearer $token"

        apiInterface.userUnfollow(id!!, mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    response: Response<APIInterface.ApiResponse<JsonElement>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<JsonElement> = response.body()!!

                        if (body.status == 1) {
                            data.value = Resource.success(body)
                        } else {
                            data.value = Resource.error(body.message.toString(), null)
                        }


                    } else {
                        val body: ResponseBody? = response.errorBody()
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var string = jObjError.getString("message")

                            data.value = Resource.error(string, null)
                        } catch (e: Exception) {
                            // Toast.makeText(getContext(), e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<APIInterface.ApiResponse<JsonElement>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }


    fun userDetails(
        id: Int?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<UserModel>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<UserModel>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<UserModel>>>()

        var mytoken = "Bearer $token"

        apiInterface.userDetail(id!!, mytoken)
            ?.enqueue(object : Callback<APIInterface.ApiResponse<UserModel>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<UserModel>>,
                    response: Response<APIInterface.ApiResponse<UserModel>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<UserModel> = response.body()!!

                        if (body.status == 1) {
                            data.value = Resource.success(body)
                        } else {
                            data.value = Resource.error(body.message.toString(), null)
                        }


                    } else {
                        val body: ResponseBody? = response.errorBody()
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var string = jObjError.getString("message")

                            data.value = Resource.error(string, null)
                        } catch (e: Exception) {
                            // Toast.makeText(getContext(), e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<APIInterface.ApiResponse<UserModel>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }


    fun getBlockList(
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<List<PeopleListModel>>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<List<PeopleListModel>>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<List<PeopleListModel>>>>()

        var mytoken = "Bearer $token"

        apiInterface.blockList(mytoken)
            ?.enqueue(object : Callback<APIInterface.ApiResponse<List<PeopleListModel>>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<List<PeopleListModel>>>,
                    response: Response<APIInterface.ApiResponse<List<PeopleListModel>>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<List<PeopleListModel>> = response.body()!!

                        if (body.status == 1) {
                            data.value = Resource.success(body)
                        } else {
                            data.value = Resource.error(body.message.toString(), null)
                        }


                    } else {
                        val body: ResponseBody? = response.errorBody()
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var string = jObjError.getString("message")

                            data.value = Resource.error(string, null)
                        } catch (e: Exception) {
                            // Toast.makeText(getContext(), e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<APIInterface.ApiResponse<List<PeopleListModel>>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }


}
