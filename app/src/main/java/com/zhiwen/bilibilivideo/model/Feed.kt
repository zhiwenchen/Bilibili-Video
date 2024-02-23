package com.zhiwen.bilibilivideo.model

import androidx.annotation.Keep

const val TYPE_IMAGE_TEXT = 1 //图文类型帖子
const val TYPE_VIDEO = 2//视频类型帖子

// 帖子对象
@Keep
data class Feed(
    val activityIcon: String?,
    val activityText: String?,
    val author: Author?,
    val authorId: Long,
    val cover: String?,
    val createTime: Long,
    val duration: Double,
    val feedsText: String?,
    val height: Int,
    val id: Long,
    val itemId: Long,
    val itemType: Int,
    val topComment: TopComment?,
    val ugc: Ugc?,
    val url: String?,
    val width: Int
) {
    var backgroundColor: Int = 0
}

// 作者对象
@Keep
data class Author(
    val avatar: String,
    val commentCount: Int,
    val description: String,
    val expiresTime: Int,
    val favoriteCount: Int,
    val feedCount: Int,
    val followCount: Int,
    val followerCount: Int,
    val hasFollow: Boolean,
    val historyCount: Int,
    val likeCount: Int,
    val name: String,
    val qqOpenId: String,
    val score: Int,
    val topCount: Int,
    val userId: Long
)

// 置顶评论对象
@Keep
data class TopComment(
    val author: Author?,
    val commentCount: Int,
    val commentId: Long,
    val commentText: String?,
    val commentType: Int,
    val commentUgc: Ugc?,
    val createTime: Long,
    val hasLiked: Boolean,
    val height: Int,
    val id: Int,
    val imageUrl: String?,
    val itemId: Long,
    val likeCount: Int,
    val userId: Long,
    val videoUrl: String?,
    val width: Int
)

//帖子互动对象
@Keep
data class Ugc(
    val commentCount: Int,
    val hasFavorite: Boolean,
    val hasLiked: Boolean,
    val hasdiss: Boolean,
    val likeCount: Int,
    val shareCount: Int
)