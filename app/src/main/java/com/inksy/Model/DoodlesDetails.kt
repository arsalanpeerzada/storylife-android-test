package com.example.example

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DoodlesDetails (

  @SerializedName("id"             ) var id           : Int?    = null,
  @SerializedName("doodle_pack_id" ) var doodlePackId : Int?    = null,
  @SerializedName("doodle_image"   ) var doodleImage  : String? = null,
  @SerializedName("is_cover"       ) var isCover      : Int?    = null,
  @SerializedName("is_active"      ) var isActive     : Int?    = null,
  @SerializedName("created_at"     ) var createdAt    : String? = null,
  @SerializedName("updated_at"     ) var updatedAt    : String? = null

) : Serializable