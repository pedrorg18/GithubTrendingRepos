package com.pedroroig.data.store

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.data.factory.makeProjectEntity
import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong

class ProjectsCacheDataStoreTest {

    private val mockCache = mock<ProjectsCache>()
    private val cacheStore = ProjectsCacheDataStore(mockCache)

    @Before
    fun setUp() {
    }

    @Test
    fun getProjects_completes() {
        stubGetProjects(
            Observable.just(listOf(makeProjectEntity()))
        )
        cacheStore.getProjects().test().assertComplete()

    }

    @Test
    fun getProjects_returnsData() {
        val projectList = listOf(makeProjectEntity())
        stubGetProjects(
            Observable.just(projectList)
        )
        cacheStore.getProjects().test().assertValue(projectList)
    }

    @Test
    fun getProjects_callsCacheSource() {
        val projectList = listOf(makeProjectEntity())
        stubGetProjects(
            Observable.just(projectList)
        )
        cacheStore.getProjects().test()
        verify(mockCache).getProjects()

    }

    @Test
    fun getBookmarkedProjects_completes() {
        stubGetBookmarkedProjects(
            Observable.just(listOf(makeProjectEntity()))
        )
        cacheStore.getBookmarkedProjects().test().assertComplete()
    }

    @Test
    fun getBookmarkedProjects_returnsData() {
        val projectList = listOf(makeProjectEntity())
        stubGetBookmarkedProjects(
            Observable.just(projectList)
        )
        cacheStore.getBookmarkedProjects().test().assertValue(projectList)
    }

    @Test
    fun clearProjects_completes() {
        stubClearProjects(Completable.complete())
        cacheStore.clearProjects().test().assertComplete()
    }

    @Test
    fun saveProjects_completes() {
        stubSaveProjects(Completable.complete())
        stubSetLastCacheTime(Completable.complete())
        val observer = cacheStore.saveProjects(listOf(makeProjectEntity())).test()
        observer.assertComplete()
    }

    @Test
    fun saveProjects_callsCacheStore() {
        val data = listOf(makeProjectEntity())
        stubSaveProjects(Completable.complete())
        stubSetLastCacheTime(Completable.complete())
        cacheStore.saveProjects(data).test()
        verify(mockCache).saveProjects(data)
        verify(mockCache).setLastCacheTime(anyLong())
    }

    @Test
    fun setProjectAsBookmarked_completes() {
        stubSetProjectAsBookmarked(Completable.complete())
        cacheStore.setProjectAsBookmarked("213").test().assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarked_completes() {
        stubSetProjectAsNotBookmarked(Completable.complete())
        cacheStore.setProjectAsNotBookmarked("213").test().assertComplete()
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(mockCache.getBookmarkedProjects())
            .thenReturn(observable)
    }

    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(mockCache.getProjects())
            .thenReturn(observable)
    }

    private fun stubClearProjects(completable: Completable) {
        whenever(mockCache.clearProjects())
            .thenReturn(completable)
    }

    private fun stubSaveProjects(completable: Completable) {
        whenever(mockCache.saveProjects(any()))
            .thenReturn(completable)
    }

    private fun stubSetLastCacheTime(completable: Completable) {
        whenever(mockCache.setLastCacheTime(anyLong()))
            .thenReturn(completable)
    }

    private fun stubSetProjectAsBookmarked(completable: Completable) {
        whenever(mockCache.setProjectAsBookmarked(any()))
            .thenReturn(completable)
    }

    private fun stubSetProjectAsNotBookmarked(completable: Completable) {
        whenever(mockCache.setProjectAsNotBookmarked(any()))
            .thenReturn(completable)
    }
}