package com.inksy.UI.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inksy.Model.OthersModel
import com.inksy.Remote.APIInterface
import com.inksy.UI.Repositories.OthersRepo


class OthersView : ViewModel() {

    private var mutableLiveData: MutableLiveData<APIInterface.ApiResponse<OthersModel>>? = null
    private var othersRepo: OthersRepo? = null

    fun init() {
        othersRepo = OthersRepo.getInstance()
        if (mutableLiveData != null) {
            return
        }


    }

    fun privacy(token : String?): LiveData<APIInterface.ApiResponse<OthersModel>?>? {
        mutableLiveData = othersRepo!!.privacy(token = token!!)
        return mutableLiveData
    }

    fun about(token : String?): LiveData<APIInterface.ApiResponse<OthersModel>?>? {
        mutableLiveData = othersRepo!!.about(token = token!!)
        return mutableLiveData
    }

    fun terms(token : String?): LiveData<APIInterface.ApiResponse<OthersModel>?>? {
        mutableLiveData = othersRepo!!.terms(token = token!!)
        return mutableLiveData
    }
}