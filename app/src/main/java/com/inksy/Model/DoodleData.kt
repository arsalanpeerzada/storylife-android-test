package com.example.example

import com.google.gson.annotations.SerializedName


data class DoodleData (

  @SerializedName("featured_pack" ) var featuredPack : ArrayList<DoodlePack> = arrayListOf(),
  @SerializedName("pack"          ) var pack         : ArrayList<Pack>         = arrayListOf()

)