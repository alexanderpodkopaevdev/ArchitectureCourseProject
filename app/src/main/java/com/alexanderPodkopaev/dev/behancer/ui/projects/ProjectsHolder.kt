package com.alexanderPodkopaev.dev.behancer.ui.projects

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.databinding.ProjectBinding


class ProjectsHolder(val binding: ProjectBinding) : ViewHolder(binding.root) {


    fun bind(item: Project, onItemClickListener: ProjectsAdapter.OnItemClickListener?) {
        binding.project = ProjectListItemViewModel(item)
        binding.onItemClickListener = onItemClickListener
        binding.executePendingBindings()

    }

}