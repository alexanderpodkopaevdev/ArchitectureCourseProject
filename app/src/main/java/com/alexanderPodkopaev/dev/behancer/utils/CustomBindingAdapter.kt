package com.alexanderPodkopaev.dev.behancer.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import com.alexanderPodkopaev.dev.behancer.data.model.project.RichProject
import com.alexanderPodkopaev.dev.behancer.ui.projects.ProjectsAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, urlImage: String?) {
    Picasso.get()
            .load(urlImage)
            .fit()
            .error(R.drawable.ic_error)
            .into(imageView)
}

@BindingAdapter(value= arrayOf("bind:data", "bind:clickHandler"), requireAll = false)
fun configureRecyclerView(recyclerView: RecyclerView, projects: MutableList<Project>?, listener: ProjectsAdapter.OnItemClickListener) {
    val adapter = ProjectsAdapter(projects, listener)
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = adapter

}

@BindingAdapter(value= arrayOf("bind:refreshState", "bind:onRefresh"), requireAll = false)
fun configureSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout, isLoading: Boolean, onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
    swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
    swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = isLoading }
}