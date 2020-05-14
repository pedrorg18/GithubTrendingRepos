package com.pedroroig.mobile_ui.bookmarked

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pedroroig.mobile_ui.R
import com.pedroroig.mobile_ui.browse.ProjectListener
import com.pedroroig.presentation.model.ProjectView
import javax.inject.Inject

class BookmarkedAdapter @Inject constructor() : RecyclerView.Adapter<BookmarkedAdapter.ViewHolder>() {

    var projects: List<ProjectView> = arrayListOf()
    private val projectListener: ProjectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmarked_project, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = projects.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        with(project) {
            holder.ownerNameText.text = ownerName
            holder.projectNameText.text = fullName

            Glide.with(holder.itemView.context)
                .load(ownerAvatar)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.avatarImage)

            holder.itemView.setOnClickListener {
                if(isBookmarked)
                    projectListener?.onBookmarkedProjectClicked(id)
                else
                    projectListener?.onProjectClicked(id)
            }
        }

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView = view.findViewById(R.id.image_owner_avatar)
        var ownerNameText: TextView = view.findViewById(R.id.text_owner_name)
        var projectNameText: TextView = view.findViewById(R.id.text_project_name)

    }
}