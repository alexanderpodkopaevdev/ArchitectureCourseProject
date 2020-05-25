package com.alexanderPodkopaev.dev.behancer.data.model.project

import androidx.room.Embedded
import androidx.room.Relation

class RichProject {

    @Embedded
    lateinit var mProject: Project
    @Relation(entity = Owner::class,entityColumn = "project_id", parentColumn = "id")
    lateinit var mOwners: List<Owner>
}