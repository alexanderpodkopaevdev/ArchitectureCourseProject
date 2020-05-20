package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.databinding.ProjectBinding
import com.alexanderPodkopaev.dev.behancer.utils.DateUtils
import com.squareup.picasso.Picasso


class ProjectsHolder(val binding: ProjectBinding) : ViewHolder(binding.root) {


    fun bind(item: Project, onItemClickListener: ProjectsAdapter.OnItemClickListener?) {
        binding.project = ProjectListItemViewModel(item)
        binding.onItemClickListener = onItemClickListener
        binding.executePendingBindings()
/*        Picasso.get().load(item.cover.photoUrl)
                .fit()
                .into(mImage)
        mName.text = item.name
        mUsername.text = item.owners[FIRST_OWNER_INDEX].username
        mPublishedOn.text = DateUtils.format(item.publishedOn)
        if (onItemClickListener != null) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(
                        item.owners[FIRST_OWNER_INDEX]
                                .username
                )
            }
        }*/
    }

}