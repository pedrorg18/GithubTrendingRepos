package com.pedroroig.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.data.factory.makeProjectEntity
import com.pedroroig.data.mapper.ProjectMapper
import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.data.repository.ProjectsCache
import com.pedroroig.data.repository.ProjectsDataStore
import com.pedroroig.data.store.ProjectsDataStoreFactory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoAnnotations

class ProjectsDataRepositoryTest {

    private val factory = mock<ProjectsDataStoreFactory>()
    private val cache = mock<ProjectsCache>()
    private val store = mock<ProjectsDataStore>()
    private val repository: ProjectsDataRepository = ProjectsDataRepository(
        ProjectMapper(),
        cache,
        factory
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        stubFactoryGetDataStore()
        stubFactoryGetCacheDataStore()
        stubIsCacheExpired(Single.just(true))
        stubAreProjectsCached(Single.just(true))
        stubSaveProjects(Completable.complete())
    }

    @Test
    fun getProjects_completes() {
        stubGetProjects(Observable.just(listOf(makeProjectEntity())))
        repository.getProjects().test().assertComplete()
    }

    @Test
    fun getProjects_returnsData() {
        val projectList = listOf(makeProjectEntity())
        stubGetProjects(Observable.just(projectList))
        repository.getProjects().test()
            .assertValue(
                projectList.map {
                    ProjectMapper().mapFromEntity(it)
                })
    }

    @Test
    fun getBookmarkedProjects_completes() {
        stubGetBookmarkedProjects(Observable.just(listOf(makeProjectEntity())))
        repository.getBookmarkedProjects().test().assertComplete()
    }

    @Test
    fun getBookmarkedProjects_returnsData() {
        val projectList = listOf(makeProjectEntity())
        stubGetBookmarkedProjects(Observable.just(projectList))
        repository.getBookmarkedProjects().test()
            .assertValue(
                projectList.map {
                    ProjectMapper().mapFromEntity(it)
                })
    }

    @Test
    fun bookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete())
        repository.bookmarkProject("1").test().assertComplete()
    }

    @Test
    fun unBookmarkProjectCompletes() {
        stubUnBookmarkProject(Completable.complete())
        repository.unBookmarkProject("1").test().assertComplete()
    }

    private fun stubAreProjectsCached(single: Single<Boolean>) {
        whenever(cache.areProjectsCached())
            .thenReturn(single)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(cache.isProjectsCacheExpired())
            .thenReturn(single)
    }

    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(store.getProjects())
            .thenReturn(observable)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(store.getBookmarkedProjects())
            .thenReturn(observable)
    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(store.setProjectAsBookmarked(anyString()))
            .thenReturn(completable)
    }

    private fun stubUnBookmarkProject(completable: Completable) {
        whenever(store.setProjectAsNotBookmarked(anyString()))
            .thenReturn(completable)
    }

    private fun stubFactoryGetDataStore() {
        whenever(factory.getDataStore(anyBoolean(), anyBoolean()))
            .thenReturn(store)
    }

    private fun stubFactoryGetCacheDataStore() {
        whenever(factory.getCacheDataStore())
            .thenReturn(store)
    }

    private fun stubSaveProjects(completable: Completable) {
        whenever(store.saveProjects(any()))
            .thenReturn(completable)
    }
}