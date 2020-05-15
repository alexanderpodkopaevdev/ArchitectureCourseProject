package com.alexanderPodkopaev.dev.behancer.data.model.user

import com.google.gson.annotations.SerializedName


class UserResponse {
    @SerializedName("user")
    lateinit var user: User

}