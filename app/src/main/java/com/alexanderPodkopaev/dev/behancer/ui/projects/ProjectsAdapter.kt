package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.databinding.ProjectBinding


class ProjectsAdapter(val mProjects: MutableList<Project>, private val mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ProjectsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProjectBinding.inflate(inflater, parent, false)
        return ProjectsHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectsHolder, position: Int) {
        val project = mProjects[position]
        holder.bind(project, mOnItemClickListener)
    }

    override fun getItemCount(): Int {
        return mProjects.size
    }

    interface OnItemClickListener {
        fun onItemClick(username: String?)
    }

}