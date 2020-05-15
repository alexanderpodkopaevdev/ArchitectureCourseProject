package com.alexanderPodkopaev.dev.behancer.data.model.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity
class Project : Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id = 0

    @ColumnInfo(name = "name")
    @SerializedName("name")
    lateinit var name: String
        private set

    @ColumnInfo(name = "published_on")
    @SerializedName("published_on")
    var publishedOn: Long = 0

    @SerializedName("covers")
    @Ignore
    lateinit var cover: Cover
        private set

    @SerializedName("owners")
    @Ignore
    lateinit var owners: List<Owner>
        private set

    fun setName(name: String) {
        this.name = name
    }

    fun setCover(cover: Cover) {
        this.cover = cover
    }

    fun setOwners(owners: List<Owner>) {
        this.owners = owners
    }
}