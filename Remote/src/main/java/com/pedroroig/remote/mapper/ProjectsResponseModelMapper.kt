package com.pedroroig.remote.mapper

import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.remote.model.ProjectModel
import javax.inject.Inject

open class ProjectsResponseModelMapper @Inject constructor() : ModelMapper<ProjectModel, ProjectEntity> {

    override fun mapFromModel(model: ProjectModel): ProjectEntity =
        with(model) {
            ProjectEntity(
                id, name, fullName, starCount.toString(), dateCreated, owner.ownerName,
                owner.ownerAvatar)
        }
}