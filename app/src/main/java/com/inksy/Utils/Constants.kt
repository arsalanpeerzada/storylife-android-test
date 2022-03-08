package com.inksy.UI

import android.content.Context
import com.inksy.Model.Model
import org.json.JSONArray
import org.json.JSONObject

class Constants {

    companion object {
        const val BASE_URL =  "https://www.inksy.appshah.us/api/v1/"
        const val BASE_IMAGE = "https://inksy.appshah.us/assets/uploads/images/"
        const val BASE_THUMBNAIL = "https://inksy.appshah.us/assets/uploads/thumbnails/"


        const val APP_NAME = "INSKSY"
        const val CREATEJOURNALINDEX = "CreateJournalIndex"
        const val CREATEJOURNALENTRY = "CreateJournalEntry"
        var sub_journalViewAll = "SubJournalViewAll"
        var doodleViewAll = "DoodleViewAll"

        var sub_journalSearch = "SubJournalSearch"
        var doodleSearch = "DoodleSearch"
        var peopleSearch = "PeopleSearch"
        var peopleViewAll = "PeopleViewAll"
        var fragment_approved = "fragment_approved"
        var fragment_pending = "fragment_pending"

        var packActivity = "PackActivity"
        var people = "People"
        var activity = "activity"
        var amountPaid = "Amount Paid"
        var journalType = "JournalType"
        var fromAdapter = "fromAdapter"
        var private_data = "private_data"
        var isLogin = "isLogin"
        var person = "Person"

        public fun readFromAsset(context: Context): MutableList<Model> {
            val modeList = mutableListOf<Model>()
            val bufferReader = context.assets.open("android_version.json").bufferedReader()
            val json_string = bufferReader.use {
                it.readText()
            }
            val jsonArray = JSONArray(json_string);

            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)

                val model = Model(jsonObject.getString("name"), jsonObject.getString("version"))
                modeList.add(model)
            }

            return modeList
        }




    }
}
