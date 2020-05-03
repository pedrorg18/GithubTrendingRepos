package com.pedroroig.cache

import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.cache.mapper.CachedProjectMapper
import com.pedroroig.cache.model.Config
import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val projectsDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectsCache{

    override fun clearProjects(): Completable =
        Completable.defer {
            projectsDatabase.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }

    override fun saveProjects(projects: List<ProjectEntity>): Completable =
        Completable.defer {
            projectsDatabase.cachedProjectsDao().insertProjects(
                projects.map {
                    mapper.mapToCached(it)
                }
            )
            Completable.complete()
        }

    override fun getProjects(): Observable<List<ProjectEntity>> =
        projectsDatabase.cachedProjectsDao().getProjects()
            .toObservable()
            .map { list ->
                list.map {
                    mapper.mapFromCached(it)
                }
            }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> =
        projectsDatabase.cachedProjectsDao().getBookmarkedProjects()
            .toObservable()
            .map { list ->
                list.map {
                    mapper.mapFromCached(it)
                }
            }

    override fun setProjectAsBookmarked(projectId: String): Completable =
        Completable.defer {
            projectsDatabase.cachedProjectsDao().setBookmarkStatus(true, projectId)
            Completable.complete()
        }

    override fun setProjectAsNotBookmarked(projectId: String): Completable =
        Completable.defer {
            projectsDatabase.cachedProjectsDao().setBookmarkStatus(false, projectId)
            Completable.complete()
        }

    override fun areProjectsCached(): Single<Boolean> =
        projectsDatabase.cachedProjectsDao().getProjects().isEmpty
            .map {
                !it
            }

    override fun setLastCacheTime(lastCache: Long): Completable =
        Completable.defer {
            projectsDatabase.configDao().insertConfig(
                Config(lastCacheTime = System.currentTimeMillis())
            )
            Completable.complete()
        }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1000).toLong()
        return projectsDatabase.configDao().getConfig()
            .toSingle(Config(lastCacheTime = 0))
            .map {
                currentTime - it.lastCacheTime > expirationTime
            }
    }

}