package com.pedroroig.cache

import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.cache.mapper.CachedProjectMapper
import com.pedroroig.cache.model.Config
import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.data.repository.ProjectsCache
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
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
        RxJavaBridge.toV3Flowable(
            projectsDatabase.cachedProjectsDao().getProjects()
        )
            .toObservable()
            .map { list ->
                list.map {
                    mapper.mapFromCached(it)
                }
            }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> =
        RxJavaBridge.toV3Flowable(
            projectsDatabase.cachedProjectsDao().getBookmarkedProjects()
        )
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
        RxJavaBridge.toV3Flowable(
            projectsDatabase.cachedProjectsDao().getProjects()
        ).isEmpty
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
        return RxJavaBridge.toV3Maybe(
            projectsDatabase.configDao().getConfig()
        )
            .switchIfEmpty(Single.just(Config(lastCacheTime = 0)))
            .map {
                currentTime - it.lastCacheTime > expirationTime
            }
    }

}