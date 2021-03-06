package com.inksy.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.example.DoodleData
import com.example.example.DoodlePack
import com.google.gson.JsonElement
import com.inksy.Model.Analytics
import com.inksy.Model.AnalyticsData
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
import com.inksy.UI.Repositories.DoodleRepo


class DoodleView : ViewModel() {
    private var mutableLiveDoodlePack: MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>>? =
        null
    private var mutableLiveDoodleData: MutableLiveData<Resource<APIInterface.ApiResponse<DoodleData>>>? =
        null
    private var mutableLiveData: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? =
        null
    private var mutableLiveDataDashboard: MutableLiveData<Resource<APIInterface.ApiResponse<AnalyticsData>>>? =
        null
    private var doodleRepo: DoodleRepo? = null

    fun init() {
        doodleRepo = DoodleRepo.getInstance()
        if (mutableLiveData != null) {
            return
        }
    }

    fun getData(token: String): MutableLiveData<Resource<APIInterface.ApiResponse<DoodleData>>>? {
        mutableLiveDoodleData = doodleRepo!!.getDoodle(token)
        return mutableLiveDoodleData
    }

    fun doodlePending(token: String): MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>>? {
        mutableLiveDoodlePack = doodleRepo!!.doodlePending(token)
        return mutableLiveDoodlePack
    }

    fun doodleApproved(token: String): MutableLiveData<Resource<APIInterface.ApiResponse<List<DoodlePack>>>>? {
        mutableLiveDoodlePack = doodleRepo!!.doodleAppoved(token)
        return mutableLiveDoodlePack
    }

    fun artistDashboard(token: String): MutableLiveData<Resource<APIInterface.ApiResponse<AnalyticsData>>>? {
        mutableLiveDataDashboard = doodleRepo!!.artistDashboard(token)
        return mutableLiveDataDashboard
    }

    fun createPack(
        token: String, _packTitle: String,
        _price: String,
        _defaultCover: String,
        _imageList: ArrayList<Int>
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData =
            doodleRepo!!.createpack(token, _packTitle, _price, _defaultCover, _imageList)
        return mutableLiveData
    }

    fun artistMake(token: String): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = doodleRepo!!.makeArtist(token)
        return mutableLiveData
    }

}