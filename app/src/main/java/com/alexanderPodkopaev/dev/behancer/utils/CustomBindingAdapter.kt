package com.alexanderPodkopaev.dev.behancer.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, urlImage: String) {
    Picasso.get()
            .load(urlImage)
            .fit()
            .into(imageView)

}

@BindingAdapter("bind:data", "bind:clickHandler")
fun configureRecyclerView(recyclerView: RecyclerView, projects: MutableList<Project>, listener: ProjectsAdapter.OnItemClickListener) {
    val adapter = ProjectsAdapter(projects, listener)
    recyclerView.adapter = adapter

}

@BindingAdapter("bind:refreshState", "bind: onRefresh")
fun configureSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout, isLoading: Boolean, onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
    swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
    swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = isLoading }

}