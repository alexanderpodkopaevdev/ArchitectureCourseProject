package com.alexanderPodkopaev.dev.behancer;

import android.app.Application;
import androidx.room.Room;

import com.alexanderPodkopaev.dev.behancer.data.database.BehanceDatabase;
import com.alexanderPodkopaev.dev.behancer.data.Storage;

/**
 * Created by Vladislav Falzan.
 */

public class AppDelegate extends Application {

    private Storage mStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        final BehanceDatabase database = Room.databaseBuilder(this, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        mStorage = new Storage(database.getBehanceDao());
    }

    public Storage getStorage() {
        return mStorage;
    }
}
