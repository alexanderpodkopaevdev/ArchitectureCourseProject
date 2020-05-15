package com.alexanderPodkopaev.dev.behancer.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alexanderPodkopaev.dev.behancer.data.model.project.Cover;
import com.alexanderPodkopaev.dev.behancer.data.model.project.Owner;
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project;
import com.alexanderPodkopaev.dev.behancer.data.model.user.Image;
import com.alexanderPodkopaev.dev.behancer.data.model.user.User;

/**
 * Created by Vladislav Falzan.
 */

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
