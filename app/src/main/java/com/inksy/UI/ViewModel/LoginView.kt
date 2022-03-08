package com.inksy.UI.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inksy.Model.UserModel
import com.inksy.Remote.APIInterface
import com.inksy.UI.Repositories.LoginRepo


class LoginView : ViewModel() {

    private var mutableLiveData: MutableLiveData<APIInterface.ApiResponse<UserModel>>? = null
    private var loginRepo: LoginRepo? = null

    fun init() {
        loginRepo = LoginRepo.getInstance()
        if (mutableLiveData != null) {
            return
        }

    }
    fun login(
        email: String,
        password: String,
        mobilenumber: String,
        code: String
    ): LiveData<APIInterface.ApiResponse<UserModel>?>? {
        mutableLiveData = loginRepo!!.login(email, password, mobilenumber, code)
        return mutableLiveData
    }

    fun profile(
        fullname: String,
        bio: String,
        avatar: String,
        token: String
    ): LiveData<APIInterface.ApiResponse<UserModel>?>? {
        mutableLiveData = loginRepo!!.userprofile(fullname, bio, avatar, token)
        return mutableLiveData
    }
}