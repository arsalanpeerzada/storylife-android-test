package com.inksy.UI.Repositories

import androidx.lifecycle.MutableLiveData
import com.example.example.DoodleData
import com.inksy.Remote.APIClient
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
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


}