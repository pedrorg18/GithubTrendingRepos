package com.pedroroig.data

import com.pedroroig.data.mapper.ProjectMapper
import com.pedroroig.data.repository.ProjectsCache
import com.pedroroig.data.store.ProjectsDataStoreFactory
import com.pedroroig.domain.model.Project
import com.pedroroig.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val projectMapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val factory: ProjectsDataStoreFactory
) : ProjectsRepository {

    override fun getProjects(): Observable<List<Project>> =
        Observable.zip(
            cache.areProjectsCached().toObservable(),
            cache.isProjectsCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { cached, expired ->
                Pair(cached, expired)
            }
        )
            .flatMap {
                factory.getDataStore(it.first, it.second).getProjects()
                    .distinctUntilChanged()
            }
            .flatMap { projects ->
                factory.getCacheDataStore()
                    .saveProjects(projects)
                    .andThen(Observable.just(projects))
            }
            .map {
                it.map { projectEntity ->
                    projectMapper.mapFromEntity(projectEntity)
                }
            }

    override fun bookmarkProject(projectId: String): Completable =
        factory.getCacheDataStore().setProjectAsBookmarked(projectId)

    override fun unBookmarkProject(projectId: String): Completable =
        factory.getCacheDataStore().setProjectAsNotBookmarked(projectId)

    override fun getBookmarkedProjects(): Observable<List<Project>> =
        factory.getCacheDataStore()
            .getBookmarkedProjects()
            .map {
                it.map { projectEntity ->
                    projectMapper.mapFromEntity(projectEntity)
                }
            }
}