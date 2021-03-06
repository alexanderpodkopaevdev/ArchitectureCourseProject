package com.alexanderPodkopaev.dev.behancer.data.model.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity
class Cover : Serializable {
    @PrimaryKey
    @ColumnInfo(name = "cover_id")
    var id = 0

    @ColumnInfo(name = "photo_url")
    @SerializedName("202")
    var photoUrl: String? = null
        private set

    @ColumnInfo(name = "project_id")
    var projectId = 0

    fun setPhotoUrl(photoUrl: String) {
        this.photoUrl = photoUrl
    }

}