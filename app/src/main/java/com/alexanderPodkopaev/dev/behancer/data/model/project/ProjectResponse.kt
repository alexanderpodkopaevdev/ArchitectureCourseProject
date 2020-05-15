package com.alexanderPodkopaev.dev.behancer.data.model.project

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ProjectResponse : Serializable {
    @SerializedName("projects")
    lateinit var projects: List<Project>

}