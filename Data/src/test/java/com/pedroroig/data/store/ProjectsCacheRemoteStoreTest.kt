package com.pedroroig.data.store

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.data.factory.makeProjectEntity
import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.data.repository.ProjectsRemote
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class ProjectsRemoteRemoteStoreTest {

    private val mockRemote = mock<ProjectsRemote>()
    private val remoteStore = ProjectsRemoteDataStore(mockRemote)

    @Before
    fun setUp() {
    }

    @Test
    fun getProjects_completes() {
        stubGetProjects(
            Observable.just(listOf(makeProjectEntity()))
        )
        remoteStore.getProjects().test().assertComplete()

    }

    @Test
    fun getProjects_returnsData() {
        val projectList = listOf(makeProjectEntity())
        stubGetProjects(
            Observable.just(projectList)
        )
        remoteStore.getProjects().test().assertValue(projectList)
    }

    @Test
    fun getProjects_callsRemoteSource() {
        val projectList = listOf(makeProjectEntity())
        stubGetProjects(
            Observable.just(projectList)
        )
        remoteStore.getProjects().test()
        verify(mockRemote).getProjects()

    }

    @Test(expected = UnsupportedOperationException::class)
    fun getBookmarkedProjects_throwsException() {
        remoteStore.getBookmarkedProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearProjects_throwsException() {
        remoteStore.clearProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveProjects_throwsException() {
        remoteStore.saveProjects(listOf(makeProjectEntity())).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsBookmarked_throwsException() {
        remoteStore.setProjectAsBookmarked("213").test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsNotBookmarked_throwsException() {
        remoteStore.setProjectAsNotBookmarked("213").test()
    }

    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(mockRemote.getProjects())
            .thenReturn(observable)
    }

}