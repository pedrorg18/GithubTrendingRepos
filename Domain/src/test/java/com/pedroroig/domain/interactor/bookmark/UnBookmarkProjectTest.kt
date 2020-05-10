package com.pedroroig.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.domain.executor.PostExecutionThread
import com.pedroroig.domain.repository.ProjectsRepository
import com.pedroroig.domain.test.randomUuId
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class UnUnBookmarkProjectTest {

    private lateinit var unBookmarkProject: UnBookmarkProject
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        unBookmarkProject = UnBookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun unBookmarkProjectCompletes() {
        stubUnBookmarkProject(Completable.complete())
        val testObserver = unBookmarkProject.buildUseCaseCompletable(randomUuId()).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unBookmarkProjectThrowsException() {
        stubUnBookmarkProject(Completable.complete())
        // pass null id, so we expect exception
        unBookmarkProject.buildUseCaseCompletable(null).test()
    }

    private fun stubUnBookmarkProject(completable: Completable) {
        whenever(projectsRepository.unBookmarkProject(anyString()))
            .thenReturn(completable)
    }

}