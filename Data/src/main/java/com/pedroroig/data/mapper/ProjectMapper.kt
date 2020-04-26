package com.pedroroig.data.mapper

import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.domain.model.Project
import javax.inject.Inject

class ProjectMapper @Inject constructor() : EntityMapper<ProjectEntity, Project> {

    override fun mapFromEntity(entity: ProjectEntity): Project =
        with(entity) {
            Project(
                id,
                name,
                fullName,
                starCount,
                dateCreated,
                ownerName,
                ownerAvatar,
                isBookmarked
            )
        }

    override fun mapToEntity(domain: Project): ProjectEntity =
        with(domain) {
            ProjectEntity(
                id,
                name,
                fullName,
                starCount,
                dateCreated,
                ownerName,
                ownerAvatar,
                isBookmarked
            )
        }
}