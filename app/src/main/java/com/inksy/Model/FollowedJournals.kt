package com.inksy.Model

import com.google.gson.annotations.SerializedName


data class FollowedJournals(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("category_id") var categoryId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("cover_image") var coverImage: String? = null,
    @SerializedName("cover_bc") var coverBc: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("created_by") var createdBy: Int? = null,
    @SerializedName("html_content") var htmlContent: String? = null,
    @SerializedName("protection") var protection: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("is_journal_like") var isJournalLike: Int? = null,
    @SerializedName("likes_count"     ) var likesCount    : Int?      = null,
    @SerializedName("comments_count"  ) var commentsCount : Int?      = null,
    @SerializedName("category") var category: Category? = null

)