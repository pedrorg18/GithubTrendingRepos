package com.pedroroig.cache.mapper

import com.pedroroig.cache.model.CachedProject
import com.pedroroig.data.model.ProjectEntity

class CachedProjectMapper : CacheMapper<CachedProject, ProjectEntity> {

    override fun mapFromCached(cached: CachedProject): ProjectEntity =
        with(cached) {
            ProjectEntity(
                id, name, fullName, starCount, dateCreated, ownerName, ownerAvatar, isBookmarked
            )
        }

    override fun mapToCached(entity: ProjectEntity): CachedProject =
        with(entity) {
            CachedProject(
                id, name, fullName, starCount, dateCreated, ownerName, ownerAvatar, isBookmarked)
        }

}