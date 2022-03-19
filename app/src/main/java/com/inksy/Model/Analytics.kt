package com.inksy.Model

import com.google.gson.annotations.SerializedName

data class Analytics(
    @SerializedName("total_pack") var totalPack: Int = 0,
    @SerializedName("approved_pack") var approvedPack: Int = 0,
    @SerializedName("pending_pack") var pendingPack: Int = 0,
    @SerializedName("total_sales") var totalSales: Int = 0,
    @SerializedName("today_sales") var todaySales: Int = 0,
    @SerializedName("monthly_sales") var monthlySales: Int = 0,
    @SerializedName("yealry_sales") var yealrySales: Int = 0,
    @SerializedName("total_received") var totalReceived: Int = 0,
    @SerializedName("total_earned") var totalEarned: Int = 0
)
