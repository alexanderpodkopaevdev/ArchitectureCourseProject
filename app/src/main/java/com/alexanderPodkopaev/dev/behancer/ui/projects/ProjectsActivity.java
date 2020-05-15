package com.alexanderPodkopaev.dev.behancer.ui.projects;

import androidx.fragment.app.Fragment;

import com.alexanderPodkopaev.dev.behancer.common.SingleFragmentActivity;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return ProjectsFragment.newInstance();
    }
}
