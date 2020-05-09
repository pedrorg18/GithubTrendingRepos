package com.pedroroig.presentation.mapper

import com.pedroroig.domain.model.Project
import com.pedroroig.presentation.model.ProjectView
import javax.inject.Inject

open class ViewMapper @Inject constructor() : Mapper<ProjectView, Project>{

    override fun mapToView(domainModel: Project): ProjectView =
        with(domainModel) {
            ProjectView(
                id, name, fullName, starCount, dateCreated, ownerName, ownerAvatar, isBookmarked
            )
        }

}