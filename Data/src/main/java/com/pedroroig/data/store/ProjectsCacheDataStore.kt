package com.pedroroig.data.store

import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.data.repository.ProjectsCache
import com.pedroroig.data.repository.ProjectsDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class ProjectsCacheDataStore @Inject constructor(
    private val projectsCache: ProjectsCache
) : ProjectsDataStore {

    override fun getProjects(): Observable<List<ProjectEntity>> =
        projectsCache.getProjects()

    override fun clearProjects(): Completable =
        projectsCache.clearProjects()

    override fun saveProjects(projects: List<ProjectEntity>): Completable =
        projectsCache.saveProjects(projects)
            .andThen {
                projectsCache.setLastCacheTime(System.currentTimeMillis())
            }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> =
        projectsCache.getBookmarkedProjects()

    override fun setProjectAsBookmarked(projectId: String): Completable =
        projectsCache.setProjectAsBookmarked(projectId)

    override fun setProjectAsNotBookmarked(projectId: String): Completable =
        projectsCache.setProjectAsNotBookmarked(projectId)
}