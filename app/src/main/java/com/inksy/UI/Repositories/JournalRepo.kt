package com.inksy.UI.Repositories

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.inksy.Model.Journals
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
import java.io.File

class JournalRepo {
    var apiInterface: APIInterface = APIClient.createService(APIInterface::class.java)

    companion object {
        lateinit var journalRepo: JournalRepo

        fun getInstance(): JournalRepo {

            journalRepo = JournalRepo()

            return journalRepo
        }
    }

    fun reportJournal(
        journalTitle: String,
        journalDescription: String,
        journal_ID: Int,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()
        var mytoken = "Bearer $token"

        apiInterface.journalReport(journal_ID!!, journalTitle, journalDescription, mytoken).enqueue(
            object : Callback<APIInterface.ApiResponse<JsonElement>> {
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
                    t: Throwable
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            }
        )
        return data
    }

    fun journalLike(
        journal_ID: String,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        val mytoken = "Bearer $token"
        apiInterface.journalLike(journal_ID, mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>?>?,
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

    fun journalFollow(
        journal_ID: String,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>()

        val mytoken = "Bearer $token"
        apiInterface.journalFollow(journal_ID, mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<JsonElement>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<JsonElement>?>?,
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


    fun journalDetails(
        journal_ID: Int,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>> {
        val data: MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>>()

        val mytoken = "Bearer $token"
        apiInterface.journalDetail(journal_ID, mytoken)
            .enqueue(object : Callback<APIInterface.ApiResponse<Journals>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<Journals>?>?,
                    response: Response<APIInterface.ApiResponse<Journals>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<Journals> = response.body()!!

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
                    call: Call<APIInterface.ApiResponse<Journals>>,
                    t: Throwable?
                ) {
                    var dataa = t?.message.toString()
                    var mydata = t?.localizedMessage

                    data.value = Resource.error(dataa, null)
                }
            })
        return data
    }


    fun journalCreate(
        _token: String,
        _category_id: Int,
        _title: String,
        _cover_bc: String,
        _description: String,
        _html_content: String,
        _protection: String,
        _is_active: String,
        _cover_image: File
    ): MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>> {

        var cover_image: RequestBody


        val category_id: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _category_id.toString())
        val title: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), _title)
        val cover_bc: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), _cover_bc)
        val description: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _description)
        val html_content: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _html_content)
        val protection: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _protection)
        val is_active: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), _is_active)

        cover_image = RequestBody.create(".png".toMediaTypeOrNull(), _cover_image)


        val data: MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>> =
            MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>>()

        val mytoken = "Bearer $_token"
        apiInterface.journalCreate(
            mytoken,
            cover_image,
            category_id,
            title,
            cover_bc,
            description,
            html_content,
            protection,
            is_active
        )
            .enqueue(object : Callback<APIInterface.ApiResponse<Journals>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<Journals>?>?,
                    response: Response<APIInterface.ApiResponse<Journals>>
                ) {
                    if (response.body() != null) {
                        val body: APIInterface.ApiResponse<Journals> = response.body()!!

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
                    call: Call<APIInterface.ApiResponse<Journals>>,
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