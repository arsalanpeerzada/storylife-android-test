package com.inksy.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.example.DoodleData
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
import com.inksy.UI.Repositories.DoodleRepo


class DoodleView : ViewModel() {

    private var mutableLiveData: MutableLiveData<Resource<APIInterface.ApiResponse<DoodleData>>>? =
        null
    private var doodleRepo: DoodleRepo? = null

    fun init() {
        doodleRepo = DoodleRepo.getInstance()
        if (mutableLiveData != null) {
            return
        }
    }

    fun getData(token: String): MutableLiveData<Resource<APIInterface.ApiResponse<DoodleData>>>? {
        mutableLiveData = doodleRepo!!.getDoodle(token)
        return mutableLiveData
    }

}