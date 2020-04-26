package com.pedroroig.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.domain.executor.PostExecutionThread
import com.pedroroig.domain.repository.ProjectsRepository
import com.pedroroig.domain.test.randomUuId
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class BookmarkProjectTest {

    private lateinit var bookmarkProject: BookmarkProject
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete())
        val testObserver = bookmarkProject.buildUseCaseCompletable(randomUuId()).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsException() {
        stubBookmarkProject(Completable.complete())
        // pass null id, so we expect exception
        bookmarkProject.buildUseCaseCompletable(null).test()
    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(anyString()))
            .thenReturn(completable)
    }

}