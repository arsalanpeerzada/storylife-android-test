package com.inksy.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.inksy.Model.PeopleListModel
import com.inksy.Model.UserModel
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
import com.inksy.UI.Repositories.PeopleRepo


class PeopleView : ViewModel() {

    private var mutableLiveData: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? =
        null
    private var mutableLiveDataUser: MutableLiveData<Resource<APIInterface.ApiResponse<UserModel>>>? =
        null
    private var mutableLiveDataPeople: MutableLiveData<Resource<APIInterface.ApiResponse<List<PeopleListModel>>>>? =
        null
    private var peopleRepo: PeopleRepo? = null

    fun init() {
        peopleRepo = PeopleRepo.getInstance()
        if (mutableLiveData != null) {
            return
        }
    }

    fun userDetail(
        id: Int,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<UserModel>>>? {
        mutableLiveDataUser = peopleRepo!!.userDetails(id, token)
        return mutableLiveDataUser
    }


    fun blockList(
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<List<PeopleListModel>>>>? {
        mutableLiveDataPeople = peopleRepo!!.getBlockList(token)
        return mutableLiveDataPeople
    }

    fun followRequests(
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<List<PeopleListModel>>>>? {
        mutableLiveDataPeople = peopleRepo!!.followRequests(token)
        return mutableLiveDataPeople
    }


    fun userFollow(
        id: Int,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = peopleRepo!!.followUser(id, token)
        return mutableLiveData
    }

    fun userBlock(
        id: Int,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = peopleRepo!!.blockUser(id, token)
        return mutableLiveData
    }

    fun userUnblock(
        id: Int,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = peopleRepo!!.unblockUser(id, token)
        return mutableLiveData
    }

    fun userUnfollow(
        id: Int,
        token: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = peopleRepo!!.unfollowUser(id, token)
        return mutableLiveData
    }
}