package com.inksy.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.inksy.Model.Journals
import com.inksy.Remote.APIInterface
import com.inksy.Remote.Resource
import com.inksy.UI.Repositories.JournalRepo
import java.io.File


class JournalView : ViewModel() {

    private var mutableLiveData: MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? =
        null
    private var mutableLiveDataJournals: MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>>? =
        null
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
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = journalRepo!!.journalLike(journal_ID = journal_Id!!, token = token!!)
        return mutableLiveData
    }

    fun journalFollow(
        journal_Id: String?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<JsonElement>>>? {
        mutableLiveData = journalRepo!!.journalFollow(journal_ID = journal_Id!!, token = token!!)
        return mutableLiveData
    }

    fun journalDetails(
        journal_Id: Int?,
        token: String?
    ): MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>>? {
        mutableLiveDataJournals =
            journalRepo!!.journalDetails(journal_ID = journal_Id!!, token = token!!)
        return mutableLiveDataJournals
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
    ): MutableLiveData<Resource<APIInterface.ApiResponse<Journals>>>? {
        mutableLiveDataJournals = journalRepo!!.journalCreate(
            _token,
            _category_id,
            _title,
            _cover_bc,
            _description,
            _html_content,
            _protection,
            _is_active,
            _cover_image
        )
        return mutableLiveDataJournals
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