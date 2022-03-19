package com.inksy.UI.Repositories

import androidx.lifecycle.MutableLiveData
import com.example.example.DoodleData
import com.example.example.DoodlePack
import com.google.gson.JsonElement
import com.inksy.Model.AnalyticsData
import com.inksy.Remote.APIClient
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoodleRepo {
    var apiInterface: APIInterface = APIClient.createService(APIInterface::class.java)

    companion object {
        lateinit var doodleRepo: DoodleRepo

        fun getInstance(): DoodleRepo {

            doodleRepo = DoodleRepo()

            return doodleRepo
        }
    }


    fun getDoodle(
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<DoodleData>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<DoodleData>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<DoodleData>>>()

        val mytoken = "Bearer $token"
        apiInterface.doodleShop(mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<DoodleData>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<DoodleData>?>?,
                    response: Response<APIInterface.ApiResponse<DoodleData>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<DoodleData> = response.body()!!

                        data.value = Resource.success(body)

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
                    call: Call<APIInterface.ApiResponse<DoodleData>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }

    fun doodlePending(
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>>()

        val mytoken = "Bearer $token"
        apiInterface.doodlePending(mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<List<DoodlePack>>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<List<DoodlePack>>?>?,
                    response: Response<APIInterface.ApiResponse<List<DoodlePack>>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<List<DoodlePack>> = response.body()!!

                        data.value = Resource.success(body)

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
                    call: Call<APIInterface.ApiResponse<List<DoodlePack>>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }

    fun doodleAppoved(
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>>()

        val mytoken = "Bearer $token"
        apiInterface.doodleApproved(mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<List<DoodlePack>>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<List<DoodlePack>>?>?,
                    response: Response<APIInterface.ApiResponse<List<DoodlePack>>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<List<DoodlePack>> = response.body()!!

                        data.value = Resource.success(body)

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
                    call: Call<APIInterface.ApiResponse<List<DoodlePack>>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }


    fun artistDashboard(
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<AnalyticsData>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<AnalyticsData>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<AnalyticsData>>>()

        val mytoken = "Bearer $token"
        apiInterface.artistDashboard(mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<AnalyticsData>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<AnalyticsData>?>?,
                    response: Response<APIInterface.ApiResponse<AnalyticsData>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<AnalyticsData> = response.body()!!

                        data.value = Resource.success(body)

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
                    call: Call<APIInterface.ApiResponse<AnalyticsData>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }

    fun createpack(
        token: String,
        _packTitle: String,
        _price: String,
        _defaultCover: String,
        _imageList: ArrayList<Int>
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()


        val packTitle: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _packTitle!!)
        val price: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _price!!)
        val defaultCover: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _defaultCover!!)

        var tatee = ""
        var selectedUsers: RequestBody? = null
        if (_imageList != null) {
            if (_imageList.size > 0) {
                for (i in _imageList.indices) {
                    if (i == 0) {
                        tatee = _imageList.get(i).toString()
                    } else {
                        tatee = tatee + "," + _imageList.get(i)
                    }
                }
                selectedUsers = RequestBody.create("text/plain".toMediaTypeOrNull(), tatee!!)
            }
        }

        val mytoken = "Bearer $token"
        apiInterface.createPack(mytoken, packTitle, price, defaultCover, selectedUsers!!)
            .enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>?>?,
                    response: Response<APIInterface.ApiResponse<JsonElement>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<JsonElement> = response.body()!!

                        data.value = Resource.success(body)

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


    fun makeArtist(
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        val mytoken = "Bearer $token"
        apiInterface.artistMake(mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>?>?,
                    response: Response<APIInterface.ApiResponse<JsonElement>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<JsonElement> = response.body()!!

                        data.value = Resource.success(body)

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


    fun createPack(

        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        val mytoken = "Bearer $token"
        apiInterface.artistMake(mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>?>?,
                    response: Response<APIInterface.ApiResponse<JsonElement>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<JsonElement> = response.body()!!

                        data.value = Resource.success(body)

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


}