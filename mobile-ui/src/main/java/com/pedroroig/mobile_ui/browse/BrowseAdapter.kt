package com.pedroroig.mobile_ui.browse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pedroroig.mobile_ui.R
import com.pedroroig.presentation.model.ProjectView
import javax.inject.Inject

class BrowseAdapter @Inject constructor() : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    var projects: List<ProjectView> = arrayListOf()
    var projectListener: ProjectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
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

            val starResource =
                if(isBookmarked)
                    R.drawable.ic_star_black_24dp
                else
                    R.drawable.ic_star_border_black_24dp
            holder.bookmarkedImage.setImageResource(starResource)

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
        var bookmarkedImage: ImageView = view.findViewById(R.id.image_bookmarked)

    }
}