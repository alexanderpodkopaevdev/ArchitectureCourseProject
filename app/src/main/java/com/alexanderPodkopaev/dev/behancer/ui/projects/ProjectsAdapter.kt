package com.alexanderPodkopaev.dev.behancer.ui.projects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexanderPodkopaev.dev.behancer.R
import com.alexanderPodkopaev.dev.behancer.data.model.project.Project
import java.util.*


class ProjectsAdapter(private val mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ProjectsHolder>() {
    private val mProjects: MutableList<Project> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.li_projects, parent, false)
        return ProjectsHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectsHolder, position: Int) {
        val project = mProjects[position]
        holder.bind(project, mOnItemClickListener)
    }

    override fun getItemCount(): Int {
        return mProjects.size
    }

    fun addData(data: List<Project>?, isRefreshed: Boolean) {
        if (isRefreshed) {
            mProjects.clear()
        }

        // TODO: 09.04.2018 ДЗ обработать кейс с data.size == 0 || data == null
        data?.let { mProjects.addAll(it) }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(username: String?)
    }

}