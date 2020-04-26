package com.pedroroig.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.domain.executor.PostExecutionThread
import com.pedroroig.domain.model.Project
import com.pedroroig.domain.repository.ProjectsRepository
import com.pedroroig.domain.test.makeProjectList
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetBookmarkedProjectsTest {

    private lateinit var getBookmarkedProjects: GetBookmarkedProjects
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkedProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        stubGetBookmarkedProjects(Observable.just(makeProjectList(5)))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projects = makeProjectList(3)
        stubGetBookmarkedProjects(Observable.just(projects))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getBookmarkedProjects())
            .thenReturn(observable)
    }

}