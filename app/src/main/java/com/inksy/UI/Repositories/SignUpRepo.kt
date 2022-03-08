package com.inksy.UI.Repositories

import androidx.lifecycle.MutableLiveData
import com.inksy.Model.UserModel
import com.inksy.Remote.APIClient
import com.inksy.Remote.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpRepo {
    var apiInterface: APIInterface = APIClient.createService(APIInterface::class.java)

    companion object {
        lateinit var signupRepo: SignUpRepo

        fun getInstance(): SignUpRepo {

            signupRepo = SignUpRepo()

            return signupRepo
        }
    }

    fun signup(
        email: String?,
        password: String?
    ): MutableLiveData<APIInterface.ApiResponse<UserModel>> {
        val signup: MutableLiveData<APIInterface.ApiResponse<UserModel>> =
            MutableLiveData<APIInterface.ApiResponse<UserModel>>()
        apiInterface.signup(email, password)
            .enqueue(object : Callback<APIInterface.ApiResponse<UserModel>> {
                override fun onResponse(
                    call: Call<APIInterface.ApiResponse<UserModel>?>?,
                    response: Response<APIInterface.ApiResponse<UserModel>>
                ) {
                    if (response.isSuccessful) {
                        signup.value = response.body()
                    }
                }

                override fun onFailure(
                    call: Call<APIInterface.ApiResponse<UserModel>>,
                    t: Throwable?
                ) {

                }
            })
        return signup
    }


}
