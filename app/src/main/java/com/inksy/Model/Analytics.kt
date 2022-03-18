package com.inksy.Model

import com.google.gson.annotations.SerializedName

data class Analytics(
    @SerializedName("total_pack") var totalPack: Int? = null,
    @SerializedName("approved_pack") var approvedPack: Int? = null,
    @SerializedName("pending_pack") var pendingPack: Int? = null,
    @SerializedName("total_sales") var totalSales: Int? = null,
    @SerializedName("today_sales") var todaySales: Int? = null,
    @SerializedName("monthly_sales") var monthlySales: Int? = null,
    @SerializedName("yealry_sales") var yealrySales: Int? = null,
    @SerializedName("total_received") var totalReceived: Int? = null,
    @SerializedName("total_earned") var totalEarned: Int? = null
)
