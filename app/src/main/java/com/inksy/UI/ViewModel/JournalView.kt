package com.inksy.UI.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
import com.inksy.UI.Repositories.JournalRepo


class JournalView : ViewModel() {

    private var mutableLiveData: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? = null
    private var journalRepo: JournalRepo? = null

    fun init() {
        journalRepo = JournalRepo.getInstance()
        if (mutableLiveData != null) {
            return
        }


    }

    fun journalLike(
        journal_Id: String?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>?  {
        mutableLiveData = journalRepo!!.journalLike(journal_ID = journal_Id!!, token = token!!)
        return mutableLiveData
    }

    fun journalReport(
        journal_Id: Int,
        token: String,
        title: String,
        description: String
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = journalRepo!!.reportJournal(
            journal_ID = journal_Id,
            journalTitle = title,
            journalDescription = description,
            token = token!!
        )
        return mutableLiveData
    }


}