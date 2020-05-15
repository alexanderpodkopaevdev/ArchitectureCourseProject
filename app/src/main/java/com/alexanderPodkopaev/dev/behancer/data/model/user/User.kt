package com.alexanderPodkopaev.dev.behancer.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
class User {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id = 0

    @ColumnInfo(name = "username")
    @SerializedName("username")
    lateinit var username: String
        private set

    @ColumnInfo(name = "location")
    @SerializedName("location")
    lateinit var location: String
        private set

    @ColumnInfo(name = "created_on")
    @SerializedName("created_on")
    var createdOn: Long = 0

    @SerializedName("images")
    @Ignore
    lateinit var image: Image
        private set

    @ColumnInfo(name = "display_name")
    @SerializedName("display_name")
    lateinit var displayName: String
        private set

    fun setUsername(username: String) {
        this.username = username
    }

    fun setLocation(location: String) {
        this.location = location
    }

    fun setImage(image: Image) {
        this.image = image
    }

    fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }
}