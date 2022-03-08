package com.inksy.Model

import com.google.gson.annotations.SerializedName


data class Categories (

  @SerializedName("id"            ) var id           : Int?    = null,
  @SerializedName("category_name" ) var categoryName : String? = null,
  @SerializedName("is_active"     ) var isActive     : Int?    = null,
  @SerializedName("created_at"    ) var createdAt    : String? = null,
  @SerializedName("updated_at"    ) var updatedAt    : String? = null

)