package com.pedroroig.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.pedroroig.domain.interactor.bookmark.BookmarkProject
import com.pedroroig.domain.interactor.bookmark.UnBookmarkProject
import com.pedroroig.domain.interactor.browse.GetProjects
import com.pedroroig.domain.model.Project
import com.pedroroig.presentation.mapper.ViewMapper
import com.pedroroig.presentation.model.ProjectView
import com.pedroroig.presentation.state.ResourceState
import com.pedroroig.presentation.test.factory.DataFactory
import com.pedroroig.presentation.test.factory.ProjectFactory
import io.reactivex.observers.DisposableObserver
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class BrowseProjectsViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockGetProjects = mock<GetProjects>()
    private val mockBookmarkProject = mock<BookmarkProject>()
    private val mockUnBookmarkProject = mock<UnBookmarkProject>()
    private val mockMapper = mock<ViewMapper>()

    private val browseProjectsViewModel = BrowseProjectsViewModel(
        mockGetProjects,
        mockBookmarkProject,
        mockUnBookmarkProject,
        mockMapper
    )

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjects_executesUseCase() {
        browseProjectsViewModel.fetchProjects()
        verify(mockGetProjects, times(1)).execute(any(), eq(null))
    }

    @Test
    fun fetchProjects_returnsSuccess() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubMapperLists(projectViews, projects)

        browseProjectsViewModel.fetchProjects()

        verify(mockGetProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(ResourceState.Success, browseProjectsViewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjects_returnsData() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubMapperLists(projectViews, projects)

        browseProjectsViewModel.fetchProjects()

        verify(mockGetProjects).execute(captor.capture(), eq(null))

        captor.firstValue.onNext(projects)

        assertEquals(projectViews, browseProjectsViewModel.getProjects().value?.data)
    }

    @Test
    fun fetchProjects_returnsError() {
        browseProjectsViewModel.fetchProjects()

        verify(mockGetProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.Error, browseProjectsViewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjects_returnsMessageForError() {
        val errorMsg = DataFactory.randomUuid()

        browseProjectsViewModel.fetchProjects()

        verify(mockGetProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(
            RuntimeException(errorMsg))

        assertEquals(errorMsg, browseProjectsViewModel.getProjects().value?.message)
    }



    private fun stubProjectMapperMapToView(projectView: ProjectView, project: Project) {
        whenever(mockMapper.mapToView(project)).thenReturn(projectView)
    }

    private fun stubMapperLists(
        projectViews: MutableList<ProjectView>,
        projects: MutableList<Project>
    ) {
        projects.forEachIndexed { index, project ->
            stubProjectMapperMapToView(projectViews[index], project)
        }
    }

}